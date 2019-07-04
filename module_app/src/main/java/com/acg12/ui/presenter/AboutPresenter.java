package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.AboutContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/04
 * Description: 自动生成
 */
@PerActivity
public class AboutPresenter implements AboutContract.Presenter{

    private AboutContract.View view;

    @Inject
    AboutPresenter() {
    }

    @Override
    public void take(AboutContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(AboutContract.View view) {

    }
}
