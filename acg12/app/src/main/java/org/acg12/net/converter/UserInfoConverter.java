package org.acg12.net.converter;

import com.google.gson.Gson;

import org.acg12.bean.test;
import org.acg12.net.factory.AbstractResponseConverter;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by DELL on 2016/12/6.
 */
public class UserInfoConverter extends AbstractResponseConverter<test> {

    public UserInfoConverter(Gson gson){
        super(gson);
    }

    @Override
    public test convert(ResponseBody value) throws IOException {
        test user = new test();
//        JSONObject data = RetrofitClient.success(value);
//        if (data != null) {
//            user.setNick(RetrofitClient.getString(data,"nickname"));
//            user.setSex(RetrofitClient.getInt(data,"sex"));
//            user.setAvatar(RetrofitClient.getString(data,"avatar"));
//            user.setEmail(RetrofitClient.getString(data,"email"));
//            user.setPhone(RetrofitClient.getString(data,"phone"));
//            user.setInviteCode(RetrofitClient.getString(data,"inviteCode"));
//            user.setUserType(RetrofitClient.getInt(data,"userType"));
//            user.setCityId(RetrofitClient.getString(data,"cityId"));
//            user.setAdder(RetrofitClient.getString(data,"address"));
//            user.setWxOpenId(RetrofitClient.getInt(data,"bindWx")+"");
//        }else{
//            RetrofitClient.failure();
//        }
        return user;
    }
}
