package com.acg12.net.services;

import com.acg12.lib.net.result.Taker;
import com.acg12.net.api.HomeApi;
import com.acg12.net.api.UserApi;
import com.acg12.net.transformer.ResultJsonObjectTransformer;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created with Android Studio.
 * UserEntity: KCJ
 * Date: 2019-06-24 15:42
 * Description:
 */
@Singleton
public class HomeApiService {

    @Inject
    HomeApi homeApi;

    @Inject
    public HomeApiService() {
    }

    public Single<JSONObject> index() {
        return homeApi.index()
                .compose(new ResultJsonObjectTransformer<>())
                .map(Taker::get);
    }
}
