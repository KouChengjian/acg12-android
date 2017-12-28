package org.acg12.utlis;

import android.content.pm.PackageInfo;
import android.os.Looper;
import android.util.Log;

import com.acg12.kk.utils.AppUtil;
import com.acg12.kk.utils.CacheUtils;
import com.acg12.kk.utils.Toastor;

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
        PackageInfo pi = AppUtil.getPackageInfo(MyApplication.getInstance());
        if (pi != null) {
            String versionName = pi.versionName == null ? "null"
                    : pi.versionName;
            String versionCode = pi.versionCode + "";
            sb.append("userid=" + userId + "\n");
            sb.append("versionName=" + versionName + "\n");
            sb.append("versionCode=" + versionCode + "\n");
        }
        sb.append("Throwable = " + ex.toString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日- HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String toFile = CacheUtils.getCacheDirectory(MyApplication.getInstance(), true, "log") + File.separator + str;
        File f = new File(toFile);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f,true);
            out.write(sb.toString().getBytes("UTF-8"));
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
