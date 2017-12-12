package com.acg12.cc.conf;


import com.acg12.cc.BuildConfig;

/**
 * Created by Administrator on 2017/5/8.
 */
public class Constant {

    public static final Boolean debug = BuildConfig.DEBUG;
    public static final String DB_NAME = "cc_acg12.db";
    public static final String KEY_HAS_GUIDE = "has_guide";
    public static final String KEY_USER_TOKEN = "user_token";
    public static final int TOOLBAR_ID = -1;
    public static final int LIMIT_PAGER = 10;
    public static final String USER_ID = "1001012001";
    public static final String USER_KEY = "F36238DEF57BEB2C9DA6E885492C1744";
    // url
    public static String URL = Constant.debug ? "http://open1.iejia.com" : "http://open1.iejia.com" ;
    // 申请权限
    public static  final String PERMISSION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static  final int PERMISSION_CAMERE = 301;
    public static  final int PERMISSION_STORAGE = 302;
    public static  final int PERMISSION_LOCATION = 303;
    // start activity
    public static  final int RESULT_ACTIVITY_REG_DEFAULT = 10000;
    public static  final int RESULT_ACTIVITY_REG_PHONE = 10001;
    public static  final int RESULT_ACTIVITY_REG_NICK = 10003;

}
