package com.acg12.lib.net.converter;

import com.acg12.lib.entity.User;
import com.acg12.lib.net.UserRetrofitClient;
import com.acg12.lib.net.factory.AbstractResponseConverter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by DELL on 2016/12/6.
 */
public class UserConverter extends AbstractResponseConverter<User> {

    public UserConverter(Gson gson) {
        super(gson);
    }

    @Override
    public User convert(ResponseBody value) throws IOException {
        User user = new User();
        JSONObject data = UserRetrofitClient.parseJSONObject(value);
        if (data != null) {
            JSONObject json = UserRetrofitClient.getJSONObject(data, "user");
            user.setUid(UserRetrofitClient.getInt(json, "id"));
            user.setSex(UserRetrofitClient.getInt(json, "sex"));
            user.setNick(UserRetrofitClient.getString(json, "nick"));
            user.setAvatar(UserRetrofitClient.getString(json, "avatar"));
            user.setSignature(UserRetrofitClient.getString(json, "sign"));
        } else {
            UserRetrofitClient.failure();
        }
        return user;
    }
}
