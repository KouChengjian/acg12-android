package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.CollectContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/05
 * Description: 自动生成
 */
@PerActivity
public class CollectPresenter implements CollectContract.Presenter{

    private CollectContract.View view;

    @Inject
    CollectPresenter() {
    }

    @Override
    public void take(CollectContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(CollectContract.View view) {

    }
}
