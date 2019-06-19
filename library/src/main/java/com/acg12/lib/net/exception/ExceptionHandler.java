package com.acg12.lib.net.exception;

import com.acg12.lib.app.BaseApp;
import com.acg12.lib.utils.HttpUtils;
import com.acg12.lib.utils.LogUtil;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.HttpException;


/**
 * Create by AItsuki on 2018/7/16.
 * 异常处理
 */
public enum ExceptionHandler {
    INSTANCE;

    public static ExceptionHandler getInstance() {
        return INSTANCE;
    }

    private static final String NETWORK_EXCEPTION = "网络连接异常";
    private static final String NETWORK_UNAVAILABLE = "网络不可用，请检查您的网络";

    private List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 添加一个拦截器
     */
    public void addIntercept(Interceptor interceptor) {
        if (interceptor == null) throw new IllegalArgumentException("interceptor == null");
        interceptors.add(interceptor);
        LogUtil.e("addIntercept: " + interceptors.size());
    }

    /**
     * 删除一个拦截器
     */
    public void removeIntercept(Interceptor interceptor) {
        interceptors.remove(interceptor);
    }

    /**
     * 拦截当前异常
     */
    public boolean intercept(Throwable throwable) {
        throwable.printStackTrace();
        for (Interceptor interceptor : interceptors) {
            if (interceptor.intercept(throwable)) {
                LogUtil.e("intercept: " + interceptor + "拦截了" + throwable);
                return true;
            }
        }
        return false;
    }

    /**
     * 将异常转换成Toast提示
     */
    public String transToReason(Throwable throwable) {
        LogUtil.e("transToReason: " + throwable);

        // 网络错误
        if (throwable instanceof retrofit2.adapter.rxjava2.HttpException) {
            return NETWORK_EXCEPTION;
        }

        if (throwable instanceof HttpException) {
            return NETWORK_EXCEPTION;
        }

        // gson解析错误
        if (throwable instanceof JsonParseException || throwable instanceof MalformedJsonException) {
            return NETWORK_EXCEPTION;

        }

        // 网络不可用
        if (!HttpUtils.isNetworkConnected(BaseApp.app())) {
            return NETWORK_UNAVAILABLE;
        }

        // 和服务器约定的异常
        if (throwable instanceof ApiException) {
            return throwable.getMessage();
        }

        // 未知错误， 先抛出去，以后一个个抓取
        return NETWORK_EXCEPTION;
    }

    public interface Interceptor {
        boolean intercept(Throwable throwable);
    }
}
