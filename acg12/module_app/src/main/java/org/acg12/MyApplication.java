package org.acg12;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.acg12.lib.conf.BaseConstant;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.utils.skin.SkinManager;
import com.facebook.stetho.Stetho;

import org.acg12.conf.Config;
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
		new Config(this);
		new Cache(this);
		new HttpRequestImpl(this);
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();


		new Toastor(this);
		new ImageLoadUtils(this);
		Stetho.initializeWithDefaults(this);
//        if(BaseConstant.debug){
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();
//        }
//        ARouter.init(this);
		if(!BaseConstant.debug){
//            Bugly.init(mContext, BaseConstant.KEY_WEIXIN_BUGLY, false);
		}
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
