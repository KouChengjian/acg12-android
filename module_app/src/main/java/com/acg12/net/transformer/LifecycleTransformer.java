package com.acg12.net.transformer;

import android.arch.lifecycle.Lifecycle;


import com.acg12.ui.base.BaseView;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Create by AItsuki on 2018/7/16.
 */
public class LifecycleTransformer<T> implements SingleTransformer<T, T>, CompletableTransformer {

    private BaseView view;
    private Lifecycle.Event event;

    public LifecycleTransformer(BaseView view) {
        this(view, Lifecycle.Event.ON_DESTROY);
    }

    public LifecycleTransformer(BaseView view, Lifecycle.Event event) {
        this.view = view;
        this.event = event;
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.compose(view.getLifeCycleProvider().bindUntilEvent(event));
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream.compose(view.getLifeCycleProvider().bindUntilEvent(event));
    }
}
