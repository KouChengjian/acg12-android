package com.acg12.ui.contract;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.base.BaseModule;
import com.acg12.ui.base.BasePresenter;
import com.acg12.ui.base.BaseView;
import com.acg12.ui.presenter.DownloadPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
public class DownloadContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

    }

    @Module
    public abstract class DownloadModule implements BaseModule {

        @PerActivity
        @Binds
        abstract Presenter presenter(DownloadPresenter presenter);
    }
}
