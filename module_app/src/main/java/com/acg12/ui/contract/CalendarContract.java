package com.acg12.ui.contract;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.base.BaseModule;
import com.acg12.ui.base.BasePresenter;
import com.acg12.ui.base.BaseView;
import com.acg12.ui.presenter.CalendarPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
public class CalendarContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

    }

    @Module
    public abstract class CalendarModule implements BaseModule {

        @PerActivity
        @Binds
        abstract Presenter presenter(CalendarPresenter presenter);
    }
}
