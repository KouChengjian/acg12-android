package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.MainContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/19
 * Description: 自动生成
 */
@PerActivity
public class MainPresenter implements MainContract.Presenter{

    private MainContract.View view;

    @Inject
    MainPresenter() {
    }

    @Override
    public void take(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(MainContract.View view) {

    }
}
