package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;

import org.acg12.R;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.CalendarView;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends SkinBaseFragment<CalendarView> {


    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        requestData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestData(){
        mView.startProgress();
        HttpRequestImpl.getInstance().calendarList(new HttpRequestListener<List<org.acg12.entity.Calendar>>() {
            @Override
            public void onSuccess(List<org.acg12.entity.Calendar> result) {
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
