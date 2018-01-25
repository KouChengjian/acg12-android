package org.acg12;

import com.acg12.common.BaseApplication;
import com.acg12.common.utils.skin.SkinManager;

import org.acg12.conf.Config;
import org.acg12.net.HttpRequestImpl;
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
public class MyApplication extends BaseApplication {
	
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

	}
}
