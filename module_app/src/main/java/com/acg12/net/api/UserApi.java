package com.acg12.net.api;


import com.acg12.entity.dto.UpdateDto;
import com.acg12.entity.po.UserEntity;
import com.acg12.lib.net.result.HttpResult;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UserApi {

    @FormUrlEncoded
    @POST("api/app/common/login.json")
    Single<HttpResult<UserEntity>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Single<HttpResult<UserEntity>> register(@Field("username") String username, @Field("password") String password, @Field("verify") String verify);

    @FormUrlEncoded
    @POST("api/verify")
    Single<HttpResult<String>> verify(@Field("username") String username, @Field("type") String type);

    @FormUrlEncoded
    @POST("api/restPwd")
    Single<HttpResult<String>> restPwd(@Field("username") String username, @Field("password") String password, @Field("verify") String verify);

    @POST("api/userInfo")
    Single<HttpResult<UserEntity>> userInfo();

    @Multipart
    @POST("api/alteruser")
    Single<HttpResult<String>> uploadAvatar(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("api/alteruser")
    Single<HttpResult<String>> userAlter(@Field("alterType") String type, @Field("param1") String param1, @Field("param2") String param2);

    @FormUrlEncoded
    @POST("api/feedback")
    Single<HttpResult<String>> feedback(@Field("message") String message);

    @FormUrlEncoded
    @POST("api/update")
    Single<HttpResult<UpdateDto>> updateApp(@Field("versionCode") String versionCode);
}
