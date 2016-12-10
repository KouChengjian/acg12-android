package org.acg12;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;

import org.acg12.config.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.net.HttpRequestImpl;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.Toastor;


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
		new Toastor(this);
		new ImageLoadUtils(this);
		new DaoBaseImpl(this);
		new HttpRequestImpl(this);
	}

	public void initExteriorSdk(){
		Stetho.initializeWithDefaults(this);
	}





	/*-------------------调用类--------------------*/
	public Boolean isGuide() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		return preferences.getBoolean(Constant.PREF_HASGUIDE, false);
	}

	public void setHasGuide(Boolean guide) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(Constant.PREF_HASGUIDE, guide).commit();
	}


	
}
