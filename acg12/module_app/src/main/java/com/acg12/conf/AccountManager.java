package com.acg12.conf;

import android.content.Context;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.User;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.conf.EventConfig;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.utils.PreferencesUtils;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/22 18:14
 * Description:
 */
public class AccountManager {

    private static final String USER_USERNAME = "login_username"; // 手机

    private static final AccountManager manager = new AccountManager();

    public static AccountManager getInstance() {
        return manager;
    }

    /**
     * -----------------------------------------------------公共保存------------------------------------------------------------
     */
    public Context getContext(){
        return BaseApp.app();
    }

    public void logout() {
        DaoBaseImpl.getInstance(getContext()).delTabUser();
        EventConfig.get().getUserEvent().post(new User(getContext()));
    }

    /**
     * -----------------------------------------------------USER------------------------------------------------------------
     */
    public void setSession(String session) {
        PreferencesUtils.putString(getContext(), Constant.SESSION, session);
    }

    public String getSession() {
        return PreferencesUtils.getString(getContext(), Constant.SESSION, "");
    }

    public void setUserName(String phone) {
        PreferencesUtils.putString(getContext(), USER_USERNAME, phone);
    }

    public String getUserName() {
        return PreferencesUtils.getString(getContext(), USER_USERNAME, "");
    }
}
