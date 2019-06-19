package com.acg12.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.acg12.lib.net.exception.ExceptionHandler;
import com.acg12.lib.ui.activity.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-06-19 16:55
 * Description:
 */
public abstract class BaseDaggerActivity extends BaseActivity implements BaseView, HasSupportFragmentInjector, ExceptionHandler.Interceptor {

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    private final LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExceptionHandler.getInstance().addIntercept(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        ExceptionHandler.getInstance().removeIntercept(this);
        super.onDestroy();
    }

    @Override
    public boolean intercept(Throwable throwable) {
//        if (throwable instanceof HttpException && ((HttpException) throwable).code() == 401) {
//            ToastSimple.showShort("登陆过期，请重新登陆。");
//            logout();
//            return true;
//        }
        return false;
    }

    @Override
    public LifecycleProvider<Lifecycle.Event> getLifeCycleProvider() {
        return provider;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    /**
     * 退出登陆，会结束其他Activity，跳转到LoginActivity
     */
    public void logout() {
//        AccountManager.getInstance().logout();
//        AppManager.get().finishAllToActivity(this, LoginActivity.class);
    }

    public Observable<Object> eventClick(View view) {
        return eventClick(view, 1000);
    }

    public Observable<Object> eventClick(View view, int milliseconds) {
        return RxView.clicks(view)
                .throttleFirst(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
