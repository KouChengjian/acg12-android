package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.SkinContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
@PerActivity
public class SkinPresenter implements SkinContract.Presenter{

    private SkinContract.View view;

    @Inject
    SkinPresenter() {
    }

    @Override
    public void take(SkinContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(SkinContract.View view) {

    }
}
