package com.acg12.lib.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import com.acg12.lib.app.BaseApp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SLAN on 2016/6/1.
 * <p>
 * 使用示例1 ToastSimple.show("简单Toast，执行时间为：" + time, time).show();
 * <p>
 * 使用示例2 ToastSimple.makeText("简单Toast，执行时间为：" + time, time, view).show();
 */
public class ToastUtil {
    private static final double LENGTH_SHORT = 3;
    private double time;
    private Scheduler.Worker showTimer;
    private Scheduler.Worker cancelTimer;
    private Toast toast;
    private static int mGravity;

    private ToastUtil() {
        showTimer = Schedulers.computation().createWorker();
        cancelTimer = Schedulers.computation().createWorker();
        mGravity = Gravity.CENTER;
    }

    private void setTime(double time) {
        this.time = time;
    }

    private void setToast(Toast toast) {
        this.toast = toast;
    }

    private void setToast(Toast toast, View view) {
        this.toast = toast;
        toast.setView(view);
        toast.setGravity(mGravity, 0, 0);
    }

    /**
     * 只显示文字内容的Toast
     *
     * @param text 文字内容
     * @param time 时间，单位为s
     * @return
     */
    public static ToastUtil makeText(CharSequence text, double time) {
        ToastUtil toast1 = new ToastUtil();
        toast1.setTime(time);
        toast1.setToast(Toast.makeText(BaseApp.app(), text, Toast.LENGTH_SHORT));
        return toast1;
    }

    public static ToastUtil makeText(int resd, double time) {
        ToastUtil toast1 = new ToastUtil();
        toast1.setTime(time);
        toast1.setToast(Toast.makeText(BaseApp.app(), resd, Toast.LENGTH_SHORT));
        return toast1;
    }

    /**
     * 可以自定义view的Toast
     *
     * @param gravity 显示的位置
     * @param time    时间，单位为s
     * @param view    要加载的自定义的view，默认显示在中间
     * @return
     */
    public static ToastUtil makeText(int gravity, double time, View view) {
        mGravity = gravity;
        ToastUtil toast1 = new ToastUtil();
        toast1.setTime(time);
        toast1.setToast(Toast.makeText(BaseApp.app(), "", Toast.LENGTH_SHORT), view);
        return toast1;
    }

    /**
     * Toast显示
     */
    public void show() {
        toast.show();
        if (time > 2) {
//            showTimer.schedulePeriodically(() -> toast.show(), 0, 1900, TimeUnit.MILLISECONDS);
            showTimer.schedulePeriodically(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            }, 0, 1900, TimeUnit.MILLISECONDS);
        }

        cancelTimer.schedule(new Runnable() {
            @Override
            public void run() {
                showTimer.dispose();
                toast.cancel();
            }
        }, (long) (time * 1000), TimeUnit.MILLISECONDS);
//        cancelTimer.schedule(() ->
//        {
//            showTimer.dispose();
//            toast.cancel();
//        }, (long) (time * 1000), TimeUnit.MILLISECONDS);
    }

    public static void show(CharSequence text, double time) {
        ToastUtil.makeText(text, time).show();
    }

    public static void showShort(String msg) {
        ToastUtil.makeText(msg, LENGTH_SHORT).show();
    }

    public static void showShort(int resId) {
        ToastUtil.makeText(resId, LENGTH_SHORT).show();
    }

    /**
     * show2 作不必要提示，即有可能后台返回数据有问题时可采用这个做提示，目的是区分正常提示 showMessage ，已达到决定不提示时可以直接直接去掉
     */
    public static void show2(CharSequence text) {
        ToastUtil.makeText(text, 3).show();
    }
}