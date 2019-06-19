package com.acg12.ui.contract;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.base.BaseModule;
import com.acg12.ui.base.BasePresenter;
import com.acg12.ui.base.BaseView;
import com.acg12.ui.presenter.MainPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/19
 * Description: 自动生成
 */
public class MainContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

    }

    @Module
    public abstract class MainModule implements BaseModule {

        @PerActivity
        @Binds
        abstract Presenter presenter(MainPresenter presenter);
    }
}
