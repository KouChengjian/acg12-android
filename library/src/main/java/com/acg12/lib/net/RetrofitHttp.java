package com.acg12.lib.net;

import android.content.Context;

import com.acg12.lib.conf.EventConfig;
import com.acg12.lib.conf.event.CommonEnum;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.net.factory.ApiConverterFactory;
import com.acg12.lib.net.factory.ApiErrorCode;
import com.acg12.lib.net.factory.ApiException;
import com.acg12.lib.net.interceptor.RequestHeaderInterceptor;
import com.acg12.lib.net.interceptor.RequestLogInterceptor;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
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

    private final int CONNECT_TIMEOUT = 40;
    private final int READ_TIMEOUT = 40;
    private final int WRITE_TIMEOUT = 40;

    private OkHttpClient mOkHttpClient;
    private retrofit2.Retrofit mRetrofit;

    public RetrofitHttp(Context context, String url) {
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
            int code = Integer.valueOf(json.getString("code")).intValue();
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                if (json.isNull("data")) {
                    data = new JSONObject();
                } else {
                    data = json.getJSONObject("data");
                }
            } else {
                throw new ApiException(code, desc);
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw new ApiException(((ApiException) e).getErrorCode(), ((ApiException) e).getMsg());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
            }
        }
        return data;
    }

    public static JSONArray parseJSONArray(ResponseBody response) {
        JSONArray data;
        try {
            String result = response.string();
            JSONObject json = new JSONObject(result);
            int code = Integer.valueOf(json.getString("code")).intValue();
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                if (json.isNull("data")) {
                    data = new JSONArray();
                } else {
                    data = json.getJSONArray("data");
                }
            } else {
                throw new ApiException(code, desc);
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw new ApiException(((ApiException) e).getErrorCode(), ((ApiException) e).getMsg());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
            }
        }
        return data;
    }

    public static JSONObject parseJSONObjectString(ResponseBody response) {
        JSONObject data;
        try {
            String result = response.string();
            JSONObject json = new JSONObject(result);
            int code = Integer.valueOf(json.getString("code")).intValue();
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                if (json.isNull("data")) {
                    data = new JSONObject();
                } else {
                    data = new JSONObject(json.getString("data"));
                }
            } else {
                throw new ApiException(code, desc);
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw new ApiException(((ApiException) e).getErrorCode(), ((ApiException) e).getMsg());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
            }
        }
        return data;
    }

    public static JSONArray parseJSONArrayString(ResponseBody response) {
        JSONArray data;
        try {
            String result = response.string();
            JSONObject json = new JSONObject(result);
            int code = Integer.valueOf(json.getString("code")).intValue();
            String desc = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                if (json.isNull("data")) {
                    data = new JSONArray();
                } else {
                    data = new JSONArray(json.getString("data"));
                }
            } else {
                throw new ApiException(code, desc);
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw new ApiException(((ApiException) e).getErrorCode(), ((ApiException) e).getMsg());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO, e.getMessage());
            }
        }
        return data;
    }

    public static <T> void success(T object, final HttpRequestListener<T> httpRequestListener) {
        httpRequestListener.onSuccess(object);
    }

    public static void failure() {
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL, ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e, final HttpRequestListener httpRequestListener) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            failure(apiException.getErrorCode(), apiException.getMsg(), httpRequestListener);
            int errorCode = apiException.getErrorCode();
            dealErrorCode(errorCode);
        } else if (e instanceof HttpException) {
            failure(-1, "网络超时", httpRequestListener);
        } else if (e instanceof ConnectException) {
            failure(-1, "网络超时", httpRequestListener);
        } else if (e instanceof SocketTimeoutException) {
            failure(-1, "网络超时", httpRequestListener);
        } else {
            failure(-1, e.toString(), httpRequestListener);
        }
    }

    public static void failure(int code, final String e, final HttpRequestListener httpRequestListener) {
        if (httpRequestListener != null) {
            httpRequestListener.onFailure(code, e);
        } else {
            failure();
        }
    }

    public static void dealErrorCode(int errorCode) {
        switch (errorCode) {
            case 5000103:
                EventConfig.get().postCommon(CommonEnum.HTTP_TOKEN_LOSE);
                break;
        }
    }


    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static RequestBody parseImageRequestBody(File file) {
        return RequestBody.create(MediaType.parse("image/png"), file);
    }
}
