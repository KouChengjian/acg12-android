package com.acg12.ui.activity;


import android.os.Bundle;

import com.acg12.R;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.MainContract;
import com.acg12.ui.presenter.MainPresenter;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/19
 * Description: 自动生成
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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