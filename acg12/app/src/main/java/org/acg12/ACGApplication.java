package org.acg12;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.acg12.conf.Config;
import org.acg12.db.DaoBaseImpl;
import org.acg12.net.HttpRequestImpl;
import org.acg12.utlis.CrashHandler;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.Toastor;
import org.acg12.utlis.skin.SkinManager;


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

public class ACGApplication extends Application {
	
	private static ACGApplication mApplication = null;

	public static ACGApplication getInstance() {
		return mApplication;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		initInsideClass();
        initExteriorSdk();
	}

	public void initInsideClass(){
		new Config(this);
		new Toastor(this);
		new ImageLoadUtils(this);
		new CrashHandler(999999);
		new DaoBaseImpl(this);
		new HttpRequestImpl(this);
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();

	}

	public void initExteriorSdk(){
		Stetho.initializeWithDefaults(this);

	}


}
