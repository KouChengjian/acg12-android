package com.acg12.ui.presenter;

import com.acg12.di.scope.PerActivity;
import com.acg12.ui.contract.FeedbackContract;

import javax.inject.Inject;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/04
 * Description: 自动生成
 */
@PerActivity
public class FeedbackPresenter implements FeedbackContract.Presenter{

    private FeedbackContract.View view;

    @Inject
    FeedbackPresenter() {
    }

    @Override
    public void take(FeedbackContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy(FeedbackContract.View view) {

    }
}
