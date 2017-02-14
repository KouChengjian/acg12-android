package org.acg12.config;


import org.acg12.BuildConfig;

/**
 * 2016-07-26 10:23
 */
public class Constant {

	public static final Boolean debug = BuildConfig.DEBUG;
	public static final String DB_NAME = "manyou.db";
	public static final int TOOLBAR_ID = -1;
	public static final int LIMIT_PAGER = 20;

	// url常量 http://192.168.1.107:8080/acg12-api/
	public static final String URL = Constant.debug ? "http://139.196.46.40:8080/api/" : "http://139.196.46.40:8080/api-v2/" ;

	// 申请权限
	public static final int PERMISSION_CAMERE = 301;

}
