package org.acg12.constant;


import com.acg12.lib.constant.ConstData;

/**
 * 2016-07-26 10:23
 */
public class Constant extends ConstData {

    public static final int LIMIT_PAGER = 10;
    public static final int LIMIT_PAGER_20 = 20;
    public static final String USER_KEY = "F36238DEF57BEB2C9DA6E885492C1744";
    public static final int TOOLBAR_ID = -1;
    public static final String DB_NAME = "manyou.db";
    public static final String URL = debug ? "http://192.168.8.168:8080/acg12/" : "http://139.196.46.40:8080/api-v2/" ;

    // 申请权限
    public static  final int PERMISSION_CAMERE = 301;
    public static  final int PERMISSION_STORAGE = 302;
    public static  final int PERMISSION_LOCATION = 303;
}
