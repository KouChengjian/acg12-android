package com.acg12.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.ui.base.BaseMvpFragment;
import com.acg12.ui.contract.HomeContract;
import com.acg12.ui.presenter.HomePresenter;


/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
