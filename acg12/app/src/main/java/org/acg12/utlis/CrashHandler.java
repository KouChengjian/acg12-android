package org.acg12.utlis;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.acg12.ACGApplication;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private int userId;

    /**
     * 初始化
     */
    public CrashHandler(int userId) {
        this.userId = userId;
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
        if (ex == null && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            Log.e("CrashHandler", "error", ex);
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toastor.ShowToast("少侠！很抱歉,程序出现异常,即将退出");
                    Looper.loop();
                }
            }.start();
            uploadCrash(ex);
        }
    }
    /**
     * 保存错误信息到文件中
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    public void uploadCrash(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        PackageInfo pi = AppUtil.getPackageInfo(ACGApplication.getInstance());
        if (pi != null) {
            String versionName = pi.versionName == null ? "null"
                    : pi.versionName;
            String versionCode = pi.versionCode + "";
            sb.append("userid=" + userId + "\n");
            sb.append("versionName=" + versionName + "\n");
            sb.append("versionCode=" + versionCode + "\n");
        }
        sb.append("Throwable = " + ex.toString());

//        Log.e("tag",sb.toString());
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                Looper.loop();
            }
        }.start();
    }


}
