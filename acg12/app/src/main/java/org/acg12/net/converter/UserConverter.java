package org.acg12.net.converter;

import com.google.gson.Gson;

import org.acg12.bean.Album;
import org.acg12.bean.User;
import org.acg12.net.RetrofitClient;
import org.acg12.net.factory.AbstractResponseConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by DELL on 2016/12/6.
 */
public class UserConverter extends AbstractResponseConverter<User> {

    public UserConverter(Gson gson){
        super(gson);
    }

    @Override
    public User convert(ResponseBody value) throws IOException {
        User user = new User();
        JSONObject data = RetrofitClient.parseJSONObject(value);
        if(data != null){
            JSONObject json = RetrofitClient.getJSONObject(data , "user");
            user.setUid(RetrofitClient.getInt(json, "id"));
            user.setSex(RetrofitClient.getInt(json, "sex"));
            user.setNick(RetrofitClient.getString(json, "nick"));
            user.setAvatar(RetrofitClient.getString(json, "avatar"));
            user.setSignature(RetrofitClient.getString(json, "sign"));
        } else {
            RetrofitClient.failure();
        }
        return user;
    }
}
