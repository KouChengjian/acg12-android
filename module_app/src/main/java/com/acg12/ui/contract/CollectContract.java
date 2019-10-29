package com.acg12.ui.contract;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.base.BaseModule;
import com.acg12.ui.base.BasePresenter;
import com.acg12.ui.base.BaseView;
import com.acg12.ui.presenter.CollectPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/05
 * Description: 自动生成
 */
public class CollectContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

    }

    @Module
    public abstract class CollectModule implements BaseModule {

        @PerActivity
        @Binds
        abstract Presenter presenter(CollectPresenter presenter);
    }
}
