package org.acg12.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.acg12.bean.User;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.factory.ApiConverterFactory;
import org.acg12.net.factory.ApiErrorCode;
import org.acg12.net.factory.ApiException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by DELL on 2016/11/29.
 */
public class RetrofitClient {

    public final static int CONNECT_TIMEOUT =10;
    public final static int READ_TIMEOUT=10;
    public final static int WRITE_TIMEOUT=10;
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
    public static Request.Builder requestBuilder(User user){
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

    public static OkHttpClient initOkhttp(final User user) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("p", user.getP())
                        .addHeader("s", user.getS())
                        .addHeader("n", user.getN())
                        .addHeader("d", user.getD())
                        .addHeader("v", user.getV())
                        .addHeader("a", user.getA())
                        .addHeader("t", user.getT())
                        .addHeader("u", user.getUid() + "")
                        .addHeader("g", user.getG())
                        .addHeader("c", user.getC()).build();
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
                .baseUrl(Constant.URL)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ApiConverterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        ApiService netInterFace = retrofit.create(ApiService.class);
        return netInterFace;
    }

    public static void failure(){
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL,ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e , final HttpRequestListener httpRequestListener){
        failure("错误"+e.toString() ,httpRequestListener);
    }

    public static void failure(final String e , final HttpRequestListener httpRequestListener){
        httpRequestListener.onFailure(-1, e);
    }

    public static JSONObject success(String response , final HttpRequestListener httpRequestListener) {
        JSONObject data = null;
        String result = "";
        try {
            result = response;
            Log.e("success",result+"");
            JSONObject json = new JSONObject(result);
            int code = json.getInt("code");
            String msg = json.getString("msg");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = json.getJSONObject("data");
            }else{
                if(httpRequestListener != null)
                    httpRequestListener.onFailure(code, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if(httpRequestListener != null){
                if(Constant.debug)
                    httpRequestListener.onFailure(ApiErrorCode.EXCEPTION_JSON , e.toString());
                else
                    httpRequestListener.onFailure(ApiErrorCode.EXCEPTION_JSON , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_JSON));
            }else {
                if(Constant.debug){
                    throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
                } else {
                    throw new ApiException(ApiErrorCode.EXCEPTION_JSON , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_JSON));
                }
            }
        }
        return data;
    }

    public static JSONObject success(retrofit2.Response<ResponseBody> response , final HttpRequestListener httpRequestListener) {
        JSONObject data = null;
        String result = "";
        try {
            result = response.body().string();
            data = success(result,httpRequestListener);
        } catch (IOException e) {
            e.printStackTrace();
            if(httpRequestListener != null){
                if(Constant.debug)
                    httpRequestListener.onFailure(ApiErrorCode.EXCEPTION_IO , e.toString());
                else
                    httpRequestListener.onFailure(ApiErrorCode.EXCEPTION_IO , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_IO));
            }
        }
        return data;
    }

    public static JSONObject success(ResponseBody response){
        JSONObject data = null;
        String result = "";
        try {
            result = response.string();
            data = success(result , null);
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
}
