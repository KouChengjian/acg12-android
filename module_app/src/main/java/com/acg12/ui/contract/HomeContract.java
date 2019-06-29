package com.acg12.ui.contract;


import com.acg12.di.scope.PerActivity;
import com.acg12.entity.po.HomeEntity;
import com.acg12.ui.base.BaseModule;
import com.acg12.ui.base.BasePresenter;
import com.acg12.ui.base.BaseView;
import com.acg12.ui.presenter.HomePresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
public class HomeContract {

    public interface View extends BaseView {
        void requestIndexSuccess(HomeEntity home);
    }

    public interface Presenter extends BasePresenter<View> {
        void requestIndex();
    }

    @Module
    public abstract class HomeModule implements BaseModule {

        @PerActivity
        @Binds
        abstract Presenter presenter(HomePresenter presenter);
    }
}
