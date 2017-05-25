package org.acg12.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.acg12.bean.User;
import org.acg12.bean.test;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.factory.ApiConverterFactory;
import org.acg12.net.factory.ApiErrorCode;
import org.acg12.net.factory.ApiException;
import org.acg12.utlis.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by DELL on 2016/11/29.
 */
public class RetrofitClient {

    public final static int CONNECT_TIMEOUT = 10;
    public final static int READ_TIMEOUT = 10;
    public final static int WRITE_TIMEOUT = 10;
    private static OkHttpClient mOkHttpClient = null;

    static {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();
    }

    {
//        FormBody formBody = OkHttpUtil.formBodyBuilder()
//                .add("username",user.getUsername())
//                .add("password",user.getPassword())
//                .build();
//        Request request = OkHttpUtil.requestBuilder(user).url(UrlConstant.URL_LOGIN).post(formBody).build();
//        Call call = OkHttpUtil.enqueue(request, new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call,final Response response) {
//            }
//        });
    }

    public static Request.Builder requestBuilder(test user){
        return new Request.Builder()
                .addHeader("p", user.getP())
                .addHeader("s", user.getS())
                .addHeader("n", user.getN())
                .addHeader("d", user.getD())
                .addHeader("v", user.getV())
                .addHeader("a", user.getA())
                .addHeader("t", user.getT())
                .addHeader("u", user.getUid()+"")
                .addHeader("g", user.getG())
                .addHeader("c", user.getC());
    }

    public static FormBody.Builder formBodyBuilder(){
        return new FormBody.Builder();
    }

    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    public static Call enqueue(Request request, Callback responseCallback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(responseCallback);
        return call;
    }

    public static OkHttpClient initOkhttp() {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
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

    public static ApiService with(){
        OkHttpClient okHttpClient = initOkhttp();
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .addConverterFactory(ApiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(Constant.URL)
                .build();
        ApiService netInterFace = retrofit.create(ApiService.class);
        return netInterFace;
    }

    public static OkHttpClient initOkhttp(final User user) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("uid" , user.getUid()+"")
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

    public static String parseString(ResponseBody response) {
        String data = null;
        try {
            String result = response.string();
            Log.e("success",result+"");
            JSONObject json = new JSONObject(result);
            String code = json.getString("result");
            String desc = json.getString("desc");
            if (code.equals(ApiErrorCode.HTTP_RESPONSE_SUCCEED+"")) {
                data = json.getString("data");
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

    public static JSONArray parseJSONArray(ResponseBody response) {
        JSONArray data = null;
        try {
            String result = response.string();
            Log.e("success",result+"");
            JSONObject json = new JSONObject(result);
            int code = json.getInt("code");
            String msg = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = json.getJSONArray("data");
            }else{
                throw new ApiException(code , msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if(Constant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_JSON));
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(Constant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_IO));
            }
        }
        return data;
    }

    public static JSONArray transformStringToJSONArray(String  response){
        JSONArray array = null;
        try {
            array = new JSONArray(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(array == null ){
            //new ApiException()
        }
        return array;
    }

    public static JSONObject transformStringToJSONObject(String  response){
        JSONObject array = null;
        try {
            array = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static JSONArray transformJSONObjectToJSONArray(JSONObject data , String key){
        JSONArray array = null;
        try {
            array = data.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void failure(){
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL,ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e , final HttpRequestListener httpRequestListener){
        failure(e.toString() ,httpRequestListener);
    }

    public static void failure(final String e , final HttpRequestListener httpRequestListener){
        if(httpRequestListener != null){
            httpRequestListener.onFailure(0, e);
        }else{
            failure();
        }
    }

}
