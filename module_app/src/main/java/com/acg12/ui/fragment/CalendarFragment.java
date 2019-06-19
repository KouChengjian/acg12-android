package com.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.acg12.entity.Calendar;
import com.acg12.lib.conf.EventConfig;
import com.acg12.lib.conf.event.CommonEnum;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseFragment;
import com.acg12.ui.views.CalendarView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends SkinBaseFragment<CalendarView> implements View.OnClickListener, TipLayoutView.OnReloadClick {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);

        requestData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == Constant.TOOLBAR_ID) {
            EventConfig.get().postCommon(CommonEnum.COMMON_TOGGLE_DRAWER);
        }
    }

    @Override
    public void onReload() {
        requestData();
    }

    private void requestData() {
        mView.startProgress();
        HttpRequestImpl.getInstance().calendarList(new HttpRequestListener<List<Calendar>>() {
            @Override
            public void onSuccess(List<Calendar> result) {
                mView.bindData(getChildFragmentManager(), result);

                mView.stopProgress();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(msg);
                mView.stopProgressOrNetError();
            }
        });
    }


}
