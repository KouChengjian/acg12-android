package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.RecordContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/04
 * Description: 自动生成
 */
@PerActivity
public class RecordPresenter implements RecordContract.Presenter{

    private RecordContract.View view;

    @Inject
    RecordPresenter() {
    }

    @Override
    public void take(RecordContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(RecordContract.View view) {

    }
}
