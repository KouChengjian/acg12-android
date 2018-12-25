package org.acg12.constant;


import com.acg12.lib.BuildConfig;

/**
 * 2016-07-26 10:23
 */
public class Constant   {

    public static final Boolean debug = BuildConfig.DEBUG;


    public static final String USER_KEY = "F36238DEF57BEB2C9DA6E885492C1744";
    public static final int TOOLBAR_ID = -1;
    public static final String DB_NAME = "manyou.db";
    public static final String URL = "http://192.168.8.130:8081/acg12/" ;

    // 申请权限
    public static  final int PERMISSION_CAMERE = 301;
    public static  final int PERMISSION_STORAGE = 302;
    public static  final int PERMISSION_LOCATION = 303;
    // 请求页数
    public static final int LIMIT_PAGER = 10;
    public static final int LIMIT_PAGER_20 = 20;
    // 申请权限
    public static final int USER_PERMISSION_CAMERE = 301;
    public static final int USER_PERMISSION_STORAGE = 302;
    public static final int USER_PERMISSION_LOCATION = 303;
    public static final int USER_PERMISSION_VOICE = 304;
    public static final int USER_PERMISSION_CALL_PHONE = 305;
    // xml保存字段
    public static final String XML_KEY_URL_POSITION = "xml_key_url_position";
}
