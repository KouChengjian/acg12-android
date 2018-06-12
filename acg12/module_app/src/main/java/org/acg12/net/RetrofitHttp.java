package org.acg12.net;

import android.content.Context;
import android.util.Log;

import com.acg12.lib.conf.BaseConstant;
import com.acg12.lib.entity.User;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.net.factory.ApiConverterFactory;
import com.acg12.lib.net.factory.ApiErrorCode;
import com.acg12.lib.net.factory.ApiException;
import com.acg12.lib.net.interceptor.RequestHeaderInterceptor;
import com.acg12.lib.net.interceptor.RequestLogInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.acg12.conf.Constant;
import org.acg12.net.api.HomeApi;
import org.acg12.net.api.UserApi;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHttp {

    public final static int CONNECT_TIMEOUT = 10;
    public final static int READ_TIMEOUT = 10;
    public final static int WRITE_TIMEOUT = 10;

    OkHttpClient mOkHttpClient;
    static retrofit2.Retrofit mRetrofit;

    private RetrofitHttp(Context context) {
        File httpCacheDirectory = new File(context.getApplicationContext().getCacheDir(), context
                .getApplicationContext().getPackageName());
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestHeaderInterceptor())
                .addInterceptor(new RequestLogInterceptor())
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .cache(cache)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constant.URL)
                .addConverterFactory(ApiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static <T> T createApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    /** ----------------------静态函数调用-------------------------------- */
    public static JSONObject parseJSONObject(ResponseBody response) {
        JSONObject data = null;
        try {
            String result = response.string();
            Log.e("success",result+"");
            JSONObject json = new JSONObject(result);
            int code = json.getInt("result");
            String desc = json.getString("desc");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = json.getJSONObject("data");
            }else{
                throw new ApiException(code , desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(BaseConstant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , e.getMessage());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_IO));
            }
        }
        return data;
    }

    public static void failure(){
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL,ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e , final HttpRequestListener httpRequestListener){
        if(e instanceof ApiException){
            failure(((ApiException)e).getMsg() ,httpRequestListener);
        } else if(e instanceof HttpException){
            failure(e.getMessage() ,httpRequestListener);
        } else if(e instanceof ConnectException){
            failure(e.getMessage() ,httpRequestListener);
        } else {
            failure(e.toString() ,httpRequestListener);
        }
    }

    public static void failure(final String e , final HttpRequestListener httpRequestListener){
        if(httpRequestListener != null){
            httpRequestListener.onFailure(0, e);
        }else{
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
