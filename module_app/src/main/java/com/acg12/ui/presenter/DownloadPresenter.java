package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.DownloadContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
@PerActivity
public class DownloadPresenter implements DownloadContract.Presenter{

    private DownloadContract.View view;

    @Inject
    DownloadPresenter() {
    }

    @Override
    public void take(DownloadContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(DownloadContract.View view) {

    }
}
