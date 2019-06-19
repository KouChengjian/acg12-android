package com.acg12.lib.utils;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/11/8 14:17
 * Description: 防止双击跳转双页面
 */
public class DoubleClickUtil {

    private static long lastClickTime;
    private final static long TIME = 800;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
