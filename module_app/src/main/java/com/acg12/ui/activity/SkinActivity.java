package com.acg12.ui.activity;


import android.os.Bundle;

import com.acg12.R;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.SkinContract;
import com.acg12.ui.presenter.SkinPresenter;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
public class SkinActivity extends BaseMvpActivity<SkinPresenter> implements SkinContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_skin;
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