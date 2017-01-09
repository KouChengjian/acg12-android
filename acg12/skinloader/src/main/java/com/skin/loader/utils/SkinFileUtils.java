package com.skin.loader.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.skin.loader.SkinConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by DELL on 2017/1/9.
 */
public class SkinFileUtils {

    /**
     * 复制assets/skin目录下的皮肤文件到指定目录
     *
     * @param context the context
     * @param name    皮肤名
     * @param toDir   指定目录
     * @return
     */
//    public static String copySkinAssetsToDir(Context context, String name, String toDir) {
//        String toFile = getCacheDirectory(context, true, "skin") + name;
//        try {
//            InputStream is = context.getAssets().open(SkinConfig.SKIN_DIR_NAME + File.separator + name);
//            File fileDir = new File(toDir);
//            if (!fileDir.exists()) {
//                fileDir.mkdirs();
//            }
//            OutputStream os = new FileOutputStream(toFile);
//            int byteCount;
//            byte[] bytes = new byte[1024];
//
//            while ((byteCount = is.read(bytes)) != -1) {
//                os.write(bytes, 0, byteCount);
//            }
//            os.close();
//            is.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return toFile;
//    }

    /**
     * 得到存放皮肤的目录
     *
     * @param context the context
     * @return 存放皮肤的目录
     */
//    public static String getSkinDir(Context context) {
//        File skinDir = new File(getCacheDir(context), SkinConfig.SKIN_DIR_NAME);
//        if (!skinDir.exists()) {
//            skinDir.mkdirs();
//        }
//        return skinDir.getAbsolutePath();
//    }

    /**
     * 得到手机的缓存目录
     *
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir.getAbsolutePath();
            }
        }

        File cacheDir = context.getCacheDir();

        return cacheDir.getAbsolutePath();
    }

    private static final String TAG = "CacheUtils";
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    public static File getCacheDirectory(Context context,boolean preferExternal, String dirName) {
        File appCacheDir = null;
        if (preferExternal&& MEDIA_MOUNTED.equals(Environment.getExternalStorageState())&& hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context, dirName);
            Log.e("appCacheDir", appCacheDir.toString());
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName()+ "/cache/";
            //Log.w("Can't define system cache directory! '%s' will be used.",cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context, String dirName) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir2 = new File(new File(dataDir, context.getPackageName()), "cache");
        File appCacheDir = new File(appCacheDir2, dirName);
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w(TAG, "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
