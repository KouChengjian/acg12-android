package org.acg12.app;


import android.app.Application;
import android.content.Context;

import com.acg12.lib.utils.CrashHandler;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.skin.SkinManager;

import org.acg12.conf.AppConfig;
import org.acg12.conf.Config;
import org.acg12.constant.Constant;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.utlis.cache.Cache;


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
		InitializeService.start(this);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
//		MultiDex.install(this);
	}
}
