package com.acg12.ui.base;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created with Android Studio.
 * UserEntity: KCJ
 * Date: 2019-06-19 17:09
 * Description:
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseSkinActivity {

    @Inject
    protected P mPresenter;

    @Override
    protected void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);
        AndroidInjection.inject(this);
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.create(savedInstanceState);
        //和View绑定
        if (mPresenter != null) {
            mPresenter.take(this);
        }
    }

    @Override
    protected void onDestroy() {
        //把所有的数据销毁掉
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy(this);//释放资源
            this.mPresenter = null;
    }
}
