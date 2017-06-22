package org.acg12.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.acg12.ACGApplication;
import org.acg12.bean.User;
import org.acg12.conf.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.factory.ApiConverterFactory;
import org.acg12.net.factory.ApiErrorCode;
import org.acg12.net.factory.ApiException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by DELL on 2016/11/29.
 */
public class RetrofitClient {

    public final static int CONNECT_TIMEOUT = 10;
    public final static int READ_TIMEOUT = 10;
    public final static int WRITE_TIMEOUT = 10;

    public static OkHttpClient initOkhttp(final User u) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                User user = u;
                if(user == null ){
                    user = new User(ACGApplication.getInstance());
                }
                Request request = chain.request().newBuilder()
                        .addHeader("uid" , user.getUid()+"")
                        .addHeader("p", user.getP())
                        .addHeader("s", user.getS())
                        .addHeader("n", user.getN())
                        .addHeader("d", user.getD())
                        .addHeader("v", user.getV())
                        .addHeader("a", user.getA())
                        .addHeader("t", user.getT())
                        .addHeader("g", user.getG())
                        .build();
                return chain.proceed(request);
            }
        })
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    public static ApiService with(User user){
        OkHttpClient okHttpClient = initOkhttp(user);
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .addConverterFactory(ApiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(Constant.URL)
                .build();
        ApiService netInterFace = retrofit.create(ApiService.class);
        return netInterFace;
    }

    /* --------静态函数调用-----------*/
    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static RequestBody parseImageRequestBody(File file) {
        return RequestBody.create(MediaType.parse("image/png"), file);
    }

    public static String getString(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getString(key);
            }else
                return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getString(JSONArray json ,int position){
        try {
            return json.getString(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getInt(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getInt(key);
            }else
                return 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Double getDouble(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getDouble(key);
            }else
                return 0.00;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    public static JSONArray getJSONArray(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getJSONArray(key);
            }else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONArray json ,int position){
        try {
            return json.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONObject json ,String key){
        JSONObject array = null;
        try {
            array = json.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

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
                throw new ApiException(Integer.valueOf(code).intValue() , desc);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(Constant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_IO));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if(Constant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_JSON));
            }
        }
        return data;
    }

    public static void failure(){
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL,ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e , final HttpRequestListener httpRequestListener){
        if(e instanceof ApiException){
            failure(e.getMessage() ,httpRequestListener);
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

}
