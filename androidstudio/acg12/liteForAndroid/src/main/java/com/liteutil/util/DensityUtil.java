package com.liteutil.util;

import android.content.Context;

import com.liteutil.http.LiteHttp;



public final class DensityUtil {

    private static float density = -1F;
    private static int widthPixels = -1;
    private static int heightPixels = -1;
    private static Context mContext = LiteHttp.getContext();

    private DensityUtil() {
    }

    public static float getDensity() {
        if (density <= 0F) {
            density = mContext.getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int dip2px(float dpValue) {
        return (int) (dpValue * getDensity() + 0.5F);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / getDensity() + 0.5F);
    }

    public static int getScreenWidth() {
        if (widthPixels <= 0) {
            widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    public static int getScreenHeight() {
        if (heightPixels <= 0) {
            heightPixels = mContext.getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }
}
