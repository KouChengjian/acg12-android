package com.acg12.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.acg12.lib.conf.BaseConstant;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.loadimage.ImageLoadUtils;
import com.facebook.stetho.Stetho;

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
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
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
