package org.acg12;


import android.app.Application;
import android.content.Context;

import com.acg12.lib.utils.skin.SkinManager;

import org.acg12.conf.Config;
import org.acg12.constant.Constant;
import org.acg12.utlis.CrashHandler;


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
		Config.init(this);
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();

		if(!Constant.debug){
//            Bugly.init(mContext, ConstData.KEY_WEIXIN_BUGLY, false);
		}
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
//		MultiDex.install(this);
	}
}
