package com.acg12.cache;

import android.content.Context;

import com.acg12.cache.dto.UserDao;
import com.acg12.entity.po.UserEntity;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.cons.Constant;
import com.acg12.lib.utils.PreferencesUtils;

/**
 * Created with Android Studio.
 * UserEntity: KCJ
 * Date: 2019-06-24 16:56
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
    public Context getContext() {
        return BaseApp.app();
    }

    public boolean isLogin() {
        UserEntity userEntity = UserDao.INSTANCE.getCurrentUser();
        if (userEntity != null) {
            return true;
        }
        return false;
    }

    public void logout() {
//        DaoBaseImpl.getInstance(getContext()).delTabUser();
//        EventConfig.get().getUserEvent().post(new UserEntity(getContext()));
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
