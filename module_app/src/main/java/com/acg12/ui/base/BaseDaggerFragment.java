package com.acg12.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.acg12.lib.ui.fragment.BaseFragment;
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
 * UserEntity: KCJ
 * Date: 2019-06-19 17:06
 * Description:
 */
public abstract class BaseDaggerFragment extends BaseFragment implements BaseView, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    private final LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(this);

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    @Override
    public LifecycleProvider<Lifecycle.Event> getLifeCycleProvider() {
        return provider;
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

