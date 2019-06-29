package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.SettingContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
@PerActivity
public class SettingPresenter implements SettingContract.Presenter{

    private SettingContract.View view;

    @Inject
    SettingPresenter() {
    }

    @Override
    public void take(SettingContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(SettingContract.View view) {

    }
}
