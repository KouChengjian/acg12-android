package com.acg12.net.services;

import com.acg12.entity.dto.UpdateDto;
import com.acg12.entity.po.UserEntity;
import com.acg12.lib.net.result.Taker;
import com.acg12.net.api.UserApi;
import com.acg12.net.transformer.ResultTransformer;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.RequestBody;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-06-29 18:49
 * Description:
 */
@Singleton
public class UserApiServices {

    @Inject
    UserApi userApi;

    @Inject
    public UserApiServices() {
    }

    public Single<UserEntity> login(String username, String password) {
        return userApi.login(username, password)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<UserEntity> register(String username, String password, String verify) {
        return userApi.register(username, password, verify)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<String> verify(String username, String type) {
        return userApi.verify(username, type)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<String> restPwd(String username, String password, String verify) {
        return userApi.restPwd(username, password, verify)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<UserEntity> userInfo() {
        return userApi.userInfo()
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<String> uploadAvatar(Map<String, RequestBody> map) {
        return userApi.uploadAvatar(map)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<String> userAlter(String type, String param1, String param2) {
        return userApi.userAlter(type, param1, param2)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<String> feedback(String message) {
        return userApi.feedback(message)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

    public Single<UpdateDto> updateApp(String versionCode) {
        return userApi.updateApp(versionCode)
                .compose(new ResultTransformer<>())
                .map(Taker::get);
    }

}
