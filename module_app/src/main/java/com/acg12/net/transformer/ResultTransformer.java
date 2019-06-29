package com.acg12.net.transformer;

import com.acg12.lib.net.exception.ApiException;
import com.acg12.lib.net.exception.ResultCode;
import com.acg12.lib.net.result.HttpResult;
import com.acg12.lib.net.result.Taker;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by AItsuki on 2018/7/14.
 */
public class ResultTransformer<T> implements SingleTransformer<HttpResult<T>, Taker<T>> {

    @Override
    public SingleSource<Taker<T>> apply(Single<HttpResult<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .flatMap((Function<HttpResult<T>, SingleSource<Taker<T>>>) result -> {
                    int code = result.code;
                    if (code == ResultCode.SUCCESS) { // 请求成功，服务器返回了数据
                        return Single.just(Taker.ofNullable(result.data));
                    } else { // 请求失败，服务器返回约定的Code --> ApiException
                        return Single.error(new ApiException(result.code, result.msg));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
