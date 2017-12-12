package org.acg12.conf;


import org.acg12.BuildConfig;

/**
 * 2016-07-26 10:23
 */
public class Constant {

	public static final Boolean debug = BuildConfig.DEBUG;
	public static final String DB_NAME = "manyou.db";
	public static final String KEY = "manyou";
	public static final int TOOLBAR_ID = -1;
	public static final int LIMIT_PAGER = 20;
	// 下载
	public static final int NONE = 1000; //无状态
	public static final int START = 1001; //准备下载
	public static final int PROGRESS = 1002; //下载中
	public static final int PAUSE = 1003; //暂停
	public static final int RESUME = 1004; //继续下载
	public static final int CANCEL = 1005; //取消
	public static final int RESTART = 1006; //重新下载
	public static final int FINISH = 1007; //下载完成
	public static final int ERROR = 1008; //下载出错
	public static final int WAIT = 1009; //等待中
	public static final int DESTROY = 1010; //释放资源

	// url常量 http://192.168.1.107:8080/acg12-api/
	public static final String URL = Constant.debug ? "http://192.168.1.92:8080/acg12/" : "http://139.196.46.40:8080/api-v2/" ;

	// 申请权限
	public static final int USER_APPLY_PERMISSION_CAMERE = 301;

	public static final int START_ACTIVITY_RESULT = 1001;

}
