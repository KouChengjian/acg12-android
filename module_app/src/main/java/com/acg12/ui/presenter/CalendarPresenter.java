package com.acg12.ui.presenter;

import com.acg12.di.scope.PerFragment;
import com.acg12.ui.contract.CalendarContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
@PerFragment
public class CalendarPresenter implements CalendarContract.Presenter{

    private CalendarContract.View view;

    @Inject
    CalendarPresenter() {
    }

    @Override
    public void take(CalendarContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(CalendarContract.View view) {

    }
}
