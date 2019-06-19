package com.acg12.lib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.Iterator;
import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public synchronized static AppManager get() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity, Class<?> cls) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (null == getActivity(activity.getClass().getSimpleName())) {
            activityStack.add(activity);
        }
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);

        LogUtil.d("存在activity个数： " + activityStack.size());

        for (Activity at : activityStack) {
            LogUtil.d("当前存在的activity为： " + at.getClass().getSimpleName());
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            finishActivity(activity);
        }

        LogUtil.d("存在activity个数： " + activityStack.size());

        for (Activity at : activityStack) {
            LogUtil.d("当前存在的activity为： " + at.getLocalClassName());
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }

        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

        LogUtil.d("存在activity个数： " + activityStack.size());

        for (Activity at : activityStack) {
            LogUtil.d("当前存在的activity为： " + at.getLocalClassName());
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }

        LogUtil.d("存在activity个数： " + activityStack.size());

        for (Activity at : activityStack) {
            LogUtil.d("当前存在的activity为： " + at.getLocalClassName());
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity,返回到指定页面
     */
    public void finishAllToActivity(Activity activity, Class<?> clazz) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && activity != activityStack.get(i) && !activityStack.get(i).getClass().equals(clazz)) {
                activityStack.get(i).finish();
            }
        }

        Intent intent = new Intent(activity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 结束指定页面上面的页面 并返回到指定页面
     */
    public void finishTopToActivity(Class<?> clazz) {
        int size = activityStack.size();
        for (int i = size - 1; i > 0; i--) {
            if (null != activityStack.get(i) && !activityStack.get(i).getClass().equals(clazz)) {
                activityStack.get(i).finish();
            } else {
                break;
            }
        }
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception ignored) {
        }
    }

    /**
     * 根据ActivityName获取堆中Activity实例
     *
     * @param activityName
     * @return
     */
    public Activity getActivity(String activityName) {
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().getSimpleName().equals(activityName)) {
                return activity;
            }
        }
        return null;
    }
}
