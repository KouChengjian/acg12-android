package com.acg12.net.converter;

import com.acg12.entity.User;

import com.acg12.entity.User;
import com.acg12.lib.net.factory.AbstractResponseConverter;
import com.acg12.lib.utils.JsonParse;
import com.google.gson.Gson;

import com.acg12.lib.net.RetrofitHttp;
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
        JSONObject data = RetrofitHttp.parseJSONObject(value);
        if (data != null) {
            user.setUid(JsonParse.getInt(data, "id"));
            user.setSex(JsonParse.getInt(data, "sex"));
            user.setNick(JsonParse.getString(data, "nick"));
            user.setAvatar(JsonParse.getString(data, "avatar"));
            user.setSignature(JsonParse.getString(data, "sign"));
            user.setSessionId(JsonParse.getString(data, "sessionId"));
        } else {
            RetrofitHttp.failure();
        }
        return user;
    }
}
