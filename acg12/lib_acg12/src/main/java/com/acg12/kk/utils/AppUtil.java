package com.acg12.kk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

public class AppUtil {

	
	public static boolean isOverMarshmallow() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
	}
	
	public static boolean isOverLollipop() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}
	
	public static String getTelephonyMgr(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		String imei = tm.getDeviceId(); 
		return imei.toString(); 
	} 
	
	/**
     * 获取App包 信息版本号
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }
}
