package com.acg12.net.transformer;

import android.text.TextUtils;


import com.acg12.ui.base.BaseView;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Author  SLAN
 * <br>
 * 2018/9/5 20:18
 */
public class DialogTransformer<T> implements SingleTransformer<T, T>, CompletableTransformer {

    private BaseView baseView;
    private String msg;

    public DialogTransformer(BaseView baseView) {
        this(baseView, null);
    }

    public DialogTransformer(BaseView baseView, String msg) {
        this.baseView = baseView;
        this.msg = msg;
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream
                .doOnSubscribe(disposable -> {
                    if (TextUtils.isEmpty(msg))
                        baseView.showProgressDialog(null);
                    else
                        baseView.showProgressDialog(msg);
                })
                .doFinally(() -> baseView.dismissProgressDialog());
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream
                .doOnSubscribe(disposable -> {
                    if (TextUtils.isEmpty(msg))
                        baseView.showProgressDialog(null);
                    else
                        baseView.showProgressDialog(msg);
                })
                .doFinally(() -> baseView.dismissProgressDialog());
    }
}
