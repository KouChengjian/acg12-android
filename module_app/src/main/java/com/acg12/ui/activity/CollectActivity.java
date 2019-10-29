package com.acg12.ui.activity;


import android.os.Bundle;

import com.acg12.R;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.CollectContract;
import com.acg12.ui.presenter.CollectPresenter;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/05
 * Description: 自动生成
 */
public class CollectActivity extends BaseMvpActivity<CollectPresenter> implements CollectContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}