package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.lib.widget.TipLayoutView;

import org.acg12.R;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.entity.Calendar;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.CalendarView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends SkinBaseFragment<CalendarView> implements View.OnClickListener ,TipLayoutView.OnReloadClick {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TOOLBARVIEW, R.color.theme_primary));
        dynamicAddView(mView.getToolBarView(), mDynamicAttr);

        requestData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            Config.navigationEventBus().post(true);
        }
    }

    @Override
    public void onReload() {
        requestData();
    }

    private void requestData(){
        mView.startProgress();
        HttpRequestImpl.getInstance().calendarList(new HttpRequestListener<List<Calendar>>() {
            @Override
            public void onSuccess(List<Calendar> result) {
                mView.bindData(getChildFragmentManager() ,result);

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
