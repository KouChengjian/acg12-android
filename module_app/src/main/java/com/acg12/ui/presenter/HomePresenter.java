package com.acg12.ui.presenter;

import com.acg12.di.scope.PerFragment;
import com.acg12.ui.contract.HomeContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
@PerFragment
public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View view;

    @Inject
    HomePresenter() {
    }

    @Override
    public void take(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(HomeContract.View view) {

    }
}
