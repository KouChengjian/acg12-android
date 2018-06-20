package org.acg12.net.converter;

import com.acg12.lib.entity.User;
import com.acg12.lib.net.factory.AbstractResponseConverter;
import com.acg12.lib.utils.JsonParse;
import com.google.gson.Gson;

import org.acg12.net.RetrofitHttp;
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
            JSONObject json = JsonParse.getJSONObject(data, "user");
            user.setUid(JsonParse.getInt(json, "id"));
            user.setSex(JsonParse.getInt(json, "sex"));
            user.setNick(JsonParse.getString(json, "nick"));
            user.setAvatar(JsonParse.getString(json, "avatar"));
            user.setSignature(JsonParse.getString(json, "sign"));
        } else {
            RetrofitHttp.failure();
        }
        return user;
    }
}
