package com.acg12.lib.conf;


import com.acg12.lib.BuildConfig;

/**
 * Created by Administrator on 2017/5/8.
 */
public class BaseConstant {

    public static final Boolean debug = BuildConfig.DEBUG;
    public static final String DB_NAME = "manyou.db";
    public static final int TOOLBAR_ID = -1;
    public static final int LIMIT_PAGER = 10;
    public static final int LIMIT_PAGER_20 = 20;
    public static final String USER_KEY = "F36238DEF57BEB2C9DA6E885492C1744";
    // url
    public static final String URL = debug ? "http://192.168.8.168:8080/acg12/" : "http://139.196.46.40:8080/api-v2/" ;
    // 申请权限
    public static  final int PERMISSION_CAMERE = 301;
    public static  final int PERMISSION_STORAGE = 302;
    public static  final int PERMISSION_LOCATION = 303;
    // start activity
    public static  final int RESULT_ACTIVITY_REG_DEFAULT = 10000;
    public static  final int RESULT_ACTIVITY_REG_PHONE = 10001;
    public static  final int RESULT_ACTIVITY_REG_NICK = 10003;

}
