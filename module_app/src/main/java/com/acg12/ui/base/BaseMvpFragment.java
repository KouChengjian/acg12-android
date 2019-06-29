package com.acg12.ui.base;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created with Android Studio.
 * UserEntity: kcj
 * Date: 2019/1/10 18:40
 * Description:
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseSkinFragment {

    @Inject
    protected P mPresenter;

    @Override
    protected void create(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        //和View绑定
        if (mPresenter != null) {
            mPresenter.take(this);
        }
    }

    @Override
    public void onDestroy() {
        //把所有的数据销毁掉
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy(this);//释放资源
        this.mPresenter = null;
    }
}
