package com.acg12.app;


import android.app.Application;
import android.content.Context;

import com.acg12.BuildConfig;
import com.acg12.conf.AppConfig;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.utils.CrashHandler;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.utlis.cache.Cache;

import com.acg12.lib.cons.Constant;


//                          _oo0oo_
//                         o8888888o
//                         88" . "88
//                         (| -_- |)
//                         0\  =  /0
//                       ___/'---'\___
//                    .' \\\|     |// '.
//                   / \\\|||  :  |||// \\
//                  / _ ||||| -:- |||||- \\
//                  | |  \\\\  -  /// |   |
//                  | \_|  ''\---/''  |_/ |
//                  \  .-\__  '-'  __/-.  /
//                ___'. .'  /--.--\  '. .'___
//             ."" '<  '.___\_<|>_/___.' >'  "".
//            | | : '-  \'.;'\ _ /';.'/ - ' : | |
//            \  \ '_.   \_ __\ /__ _/   .-' /  /
//        ====='-.____'.___ \_____/___.-'____.-'=====
//                          '=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//       佛祖保佑           永无BUG         镇类之宝
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
public class MyApplication extends Application {
	
	private static MyApplication mApplication = null;

	public static MyApplication getInstance() {
		return mApplication;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		BaseApp.Ext.init(this);
		BaseApp.Ext.setDebug(BuildConfig.DEBUG);
		// 初始化
		Cache.init(this);
		Toastor.init(this);
		AppConfig.init(this);
		CrashHandler.init(this);
		PreferencesUtils.init(this); // xml存储
        HttpRequestImpl.init(this); // http请求
		// 初始化皮肤
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
//		MultiDex.install(this);
	}
}
