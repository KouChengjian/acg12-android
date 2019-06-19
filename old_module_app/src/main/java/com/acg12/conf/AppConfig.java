package com.acg12.conf;

import android.content.Context;

import com.acg12.lib.app.BaseApp;
import com.acg12.lib.cons.Constant;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.widget.dialog.debug.ServerUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/8/3 16:09
 * Description: url配置
 */
public class AppConfig {

    public static ServerUrl SERVER = null;
    public final static List<ServerUrl> DefaultServerUrlList = new ArrayList<>();
    public final static ServerUrl URL_SERVER_PROP = new ServerUrl("http://acg12.club/acg12/"); //正式服
    public final static ServerUrl URL_SERVER_DEBUG_130 = new ServerUrl("http://192.168.8.177:8081/acg12/");

    static {
        DefaultServerUrlList.add(URL_SERVER_PROP);
        DefaultServerUrlList.add(URL_SERVER_DEBUG_130);
    }

    public static void init(Context context) {
        new AppConfig(context);
    }

    public AppConfig(Context context) {
        if (BaseApp.isDebug()) {
            int postion = getServerUrl(context);
            SERVER = DefaultServerUrlList.get(postion);
        } else {
            SERVER = URL_SERVER_DEBUG_130;
        }
    }

    public static int getServerUrl(Context context) {
        int position = PreferencesUtils.getInt(context, Constant.XML_KEY_URL_POSITION, 0);
        return position;
    }

    public static void setServerUrl(Context context, int position) {
        PreferencesUtils.putInt(context, Constant.XML_KEY_URL_POSITION, position);
    }
}
