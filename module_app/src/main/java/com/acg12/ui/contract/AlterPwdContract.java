package com.acg12.ui.contract;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.base.BaseModule;
import com.acg12.ui.base.BasePresenter;
import com.acg12.ui.base.BaseView;
import com.acg12.ui.presenter.AlterPwdPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/29
 * Description: 自动生成
 */
public class AlterPwdContract {


    public interface View extends BaseView {
        void alterPwdSucceed();
    }

    public interface Presenter extends BasePresenter<View> {
        void alterPwd(String password, String newPassword);
    }

    @Module
    public abstract class AlterPwdModule implements BaseModule {

        @PerActivity
        @Binds
        abstract Presenter presenter(AlterPwdPresenter presenter);
    }
}
