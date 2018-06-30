package com.acg12.lib.net;

import android.content.Context;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.net.factory.ApiConverterFactory;
import com.acg12.lib.net.factory.ApiErrorCode;
import com.acg12.lib.net.factory.ApiException;
import com.acg12.lib.net.interceptor.RequestHeaderInterceptor;
import com.acg12.lib.net.interceptor.RequestLogInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RetrofitHttp {

    private final int CONNECT_TIMEOUT = 10;
    private final int READ_TIMEOUT = 10;
    private final int WRITE_TIMEOUT = 10;

    private OkHttpClient mOkHttpClient;
    private retrofit2.Retrofit mRetrofit;

    public RetrofitHttp(Context context , String url) {
        File httpCacheDirectory = new File(context.getApplicationContext().getCacheDir(), context
                .getApplicationContext().getPackageName());
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestHeaderInterceptor())
                .addInterceptor(new RequestLogInterceptor())
//                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
//                .cache(cache)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(url)
                .addConverterFactory(ApiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> T createApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    /**
     * ----------------------静态函数调用--------------------------------
     */
    public static JSONObject parseJSONObject(ResponseBody response) {
        JSONObject data;
        try {
            String result = response.string();
            JSONObject json = new JSONObject(result);
            int code = json.getInt("code");
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = json.getJSONObject("data");
            }else{
                throw new ApiException(code , desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
        }
        return data;
    }

    public static JSONObject parseJSONObject2(ResponseBody response) {
        JSONObject data;
        try {
            String result = response.string();
            JSONObject json = new JSONObject(result);
            int code = json.getInt("code");
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = new JSONObject(json.getString("data"));
            }else{
                throw new ApiException(code , desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
        }
        return data;
    }

    public static JSONArray parseJSONObject3(ResponseBody response) {
        JSONArray data;
        try {
            String result = response.string();
            JSONObject json = new JSONObject(result);
            int code = json.getInt("code");
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = new JSONArray(json.getString("data"));
            }else{
                throw new ApiException(code , desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
        }
        return data;
    }

    public static void failure() {
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL, ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e, final HttpRequestListener httpRequestListener) {
        if (e instanceof ApiException) {
            failure(((ApiException) e).getMsg(), httpRequestListener);
        } else if (e instanceof HttpException) {
            failure(e.getMessage(), httpRequestListener);
        } else if (e instanceof ConnectException) {
            failure(e.getMessage(), httpRequestListener);
        } else {
            failure(e.toString(), httpRequestListener);
        }
    }

    public static void failure(final String e, final HttpRequestListener httpRequestListener) {
        if (httpRequestListener != null) {
            httpRequestListener.onFailure(0, e);
        } else {
            failure();
        }
    }

    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static RequestBody parseImageRequestBody(File file) {
        return RequestBody.create(MediaType.parse("image/png"), file);
    }
}
