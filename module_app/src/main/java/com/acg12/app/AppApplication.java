package com.acg12.app;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;

import com.acg12.BuildConfig;
import com.acg12.conf.AppConfig;
import com.acg12.di.component.DaggerAppComponent;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.utils.ToastUtil;
import com.acg12.lib.utils.skin.SkinManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;
import okhttp3.Cache;

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
public class AppApplication extends Application implements HasActivityInjector, HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .app(this)
                .build()
                .inject(this);
        BaseApp.Ext.init(this);
        BaseApp.Ext.setDebug(BuildConfig.DEBUG);
        // 初始化
//        Cache.init(this);
//        ToastUtil.init(this);
        AppConfig.init(this);
//        CrashHandler.init(this);
//        PreferencesUtils.init(this); // xml存储
//        HttpRequestImpl.init(this); // http请求
//         初始化皮肤
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//		MultiDex.install(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }
}
