package com.acg12.lib.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.TextView;

public class ForegroundUtil implements Application.ActivityLifecycleCallbacks {

    private final static String TAG = ForegroundUtil.class.getName();
    private Activity activity;
    private TextView textView;
    //单例
    private static final ForegroundUtil instance = new ForegroundUtil();
    //用于判断是否程序在前台
    private boolean foreground = false, paused = true;

    public static void register(Application app) {
        app.registerActivityLifecycleCallbacks(instance);
    }

    public static void unregister(Application app) {
        app.unregisterActivityLifecycleCallbacks(instance);
    }

    public static ForegroundUtil get() {
        return instance;
    }

    private ForegroundUtil() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onActivityStarted(Activity activity) {
        // TODO Auto-generated method stub
    }

    // TODO :kaiser @2018/7/22:需要重构 这里会出现 sending message to a Handler on a dead thread
    @Override
    public void onActivityResumed(Activity activity) {
        this.activity = activity;
        foreground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        foreground = false;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // TODO Auto-generated method stub
    }

    public boolean isForeground() {
        return foreground;
    }

    public Activity getActivity() {
        return activity;
    }
}
