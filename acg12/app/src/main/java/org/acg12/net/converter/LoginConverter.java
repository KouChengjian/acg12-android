package org.acg12.net.converter;

import com.google.gson.Gson;

import org.acg12.bean.test;
import org.acg12.net.factory.AbstractResponseConverter;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by DELL on 2016/12/6.
 */
public class LoginConverter extends AbstractResponseConverter<test> {

    public LoginConverter(Gson gson){
        super(gson);
    }

    @Override
    public test convert(ResponseBody value) throws IOException {
        test user = new test();
//        JSONObject data = RetrofitClient.success(value);
//        if (data != null) {
//            user.setUid(RetrofitClient.getInt(data, "userId"));
//            user.setC(RetrofitClient.getString(data, "accessToken"));
//            user.setTokenKey(RetrofitClient.getString(data, "tokenKey"));
//            user.setExpired(RetrofitClient.getInt(data, "expired"));
//            user.setUpdateTime(System.currentTimeMillis() / 1000);
//        }else{
//            RetrofitClient.failure();
//        }
        return user;
    }
}
