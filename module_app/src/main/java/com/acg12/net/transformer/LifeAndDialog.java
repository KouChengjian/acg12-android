package com.acg12.net.transformer;



import com.acg12.ui.base.BaseView;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Created by AItsuki on 2018/7/28.
 */
public class LifeAndDialog<T> implements SingleTransformer<T, T> {

    private BaseView baseView;

    public LifeAndDialog(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream
                .compose(new DialogTransformer<>(baseView))
                .compose(new LifecycleTransformer<>(baseView));
    }
}
