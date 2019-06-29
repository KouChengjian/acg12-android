package com.acg12.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.ui.base.BaseMvpFragment;
import com.acg12.ui.contract.CalendarContract;
import com.acg12.ui.presenter.CalendarPresenter;


/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
public class CalendarFragment extends BaseMvpFragment<CalendarPresenter> implements CalendarContract.View {

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
