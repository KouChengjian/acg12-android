package org.acg12.config;


import com.kcj.animationfriend.BuildConfig;

/**
 * @ClassName: Constant
 * @Description: 常量
 * @author: KCJ
 * @date: 2016-07-26 10:23
 */
public class Constant {

	public static final Boolean debug = BuildConfig.DEBUG;
	public static final String PREF_HASGUIDE = "hasGuide";   // 是否启动引导
	public static final String KEY = "wcphp";
	public static final String DB_NAME = "";
	public static final int TOOLBAR_ID = -1;

	// url常量
	public static final String URL = Constant.debug ? "http://api.nx87.cn" : "" ; // http://api.51yueka.com

	// 申请权限
	public static final int PERMISSION_CAMERE = 301;

}
