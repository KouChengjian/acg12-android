package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.net.services.UserApiServices;
import com.acg12.ui.contract.AlterPwdContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/29
 * Description: 自动生成
 */
@PerActivity
public class AlterPwdPresenter implements AlterPwdContract.Presenter{

    @Inject
    UserApiServices userApiServices;

    private AlterPwdContract.View view;

    @Inject
    AlterPwdPresenter() {
    }

    @Override
    public void take(AlterPwdContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(AlterPwdContract.View view) {

    }

    @Override
    public void alterPwd(String password, String newPassword) {
//        userApiServices.
    }
}
