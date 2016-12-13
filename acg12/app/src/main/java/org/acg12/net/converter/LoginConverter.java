package org.acg12.net.converter;

import com.google.gson.Gson;

import org.acg12.bean.User;
import org.acg12.net.RetrofitClient;
import org.acg12.net.factory.AbstractResponseConverter;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by DELL on 2016/12/6.
 */
public class LoginConverter extends AbstractResponseConverter<User> {

    public LoginConverter(Gson gson){
        super(gson);
    }

    @Override
    public User convert(ResponseBody value) throws IOException {
        User user = new User();
        JSONObject data = RetrofitClient.success(value);
        if (data != null) {
            user.setUid(RetrofitClient.getInt(data, "userId"));
            user.setC(RetrofitClient.getString(data, "accessToken"));
            user.setTokenKey(RetrofitClient.getString(data, "tokenKey"));
            user.setExpired(RetrofitClient.getInt(data, "expired"));
            user.setUpdateTime(System.currentTimeMillis() / 1000);
        }else{
            RetrofitClient.failure();
        }
        return user;
    }
}
