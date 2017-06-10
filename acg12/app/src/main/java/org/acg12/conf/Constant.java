package org.acg12.conf;


import org.acg12.BuildConfig;

/**
 * 2016-07-26 10:23
 */
public class Constant {

	public static final Boolean debug = BuildConfig.DEBUG;
	public static final String DB_NAME = "manyou.db";
	public static final int TOOLBAR_ID = -1;
	public static final int LIMIT_PAGER = 20;
	public static final String BMOB_APP_ID = "92b8ccc3c129b72251e817155d955485";
	public static final String SEX_MALE = "male";
	public static final String SEX_FEMALE = "female";

	// url常量 http://192.168.1.107:8080/acg12-api/
	public static final String URL = Constant.debug ? "http://192.168.1.101:8080/acg12/" : "http://139.196.46.40:8080/api-v2/" ;

	// 申请权限
	public static final int USER_APPLY_PERMISSION_CAMERE = 301;

	public static final int START_ACTIVITY_RESULT = 1001;

}
