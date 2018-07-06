package org.acg12.utlis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.acg12.lib.utils.ActivityTack;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.CacheUtils;
import com.acg12.lib.utils.Toastor;

import org.acg12.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    public static void init(Context context) {
        new CrashHandler(context);
    }

    /**
     * 初始化
     */
    public CrashHandler(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        showToast("很抱歉,程序出现异常,即将退出");
        try {
            thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActivityTack.getInstanse().finishAllActivity();
        System.exit(1);
    }

    //线程中展示Toast
    private void showToast(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }
}
