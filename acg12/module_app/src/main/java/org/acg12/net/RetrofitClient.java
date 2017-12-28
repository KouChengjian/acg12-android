package org.acg12.net;

import com.acg12.common.entity.User;
import com.acg12.kk.net.BaseRetrofitClient;
import com.acg12.kk.net.factory.ApiConverterFactory;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.acg12.conf.Constant;
import org.acg12.net.base.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by DELL on 2016/11/29.
 */
public class RetrofitClient  extends BaseRetrofitClient{


    public static OkHttpClient initOkhttp(final User user) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

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
}
