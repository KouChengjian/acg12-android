package com.acg12.di.module;




import com.acg12.conf.AppConfig;
import com.acg12.net.api.UserApi;
import com.acg12.net.intercept.LogInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit+Rxjava工具类，用于配置、封装等
 * 提供Service实例
 */
@Module
public class ApiModule {

    @Provides
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
//                                                   CommonInterceptor commonInterceptor,
//                                                   AuthInterceptor authInterceptor,
                                                   LogInterceptor logInterceptor
//                                                   BranchInterceptor branchInterceptor
    ) {
        return new OkHttpClient.Builder()
//                .addInterceptor(commonInterceptor) // 公共参数
                .addInterceptor(logInterceptor)    // 请求日志
//                .addInterceptor(branchInterceptor) // branchId TODO 暂时屏蔽
//                .addInterceptor(authInterceptor) // token
                .addInterceptor(loggingInterceptor) // log
                .build();
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

//    @Provides
//    @Singleton
//    public static AuthService provideOauthService(Retrofit retrofit) {
//        return retrofit.create(AuthService.class);
//    }

//    @Provides
//    @Singleton
//    public static ApiService provideDishService(Retrofit retrofit) {
//        return retrofit.create(ApiService.class);
//    }

    @Provides
    @Singleton
    public static UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

}
