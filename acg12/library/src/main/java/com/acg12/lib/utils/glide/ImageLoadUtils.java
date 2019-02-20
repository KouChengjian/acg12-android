package com.acg12.lib.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.acg12.lib.R;
import com.acg12.lib.utils.CacheUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2016/11/17.
 */
public class ImageLoadUtils {

    private static RequestOptions mRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.bg_loading_pic).error(R.mipmap.bg_loading_pic);

    private static RequestBuilder<Drawable> loadWithContext(Context mContext, String url) {
        return Glide.with(mContext).load(getModel(url));
    }

    /**
     * --------------------    Glide    ------------------------
     */
    public static void glideLoading(Context mContext, String url, ImageView imageview) {
        if (mContext == null) return;
        glideLoading(mContext, url, imageview, R.mipmap.bg_loading_pic, R.mipmap.bg_loading_pic);
    }

    public static void glideLoading(Context mContext, String url, ImageView imageview, int placeholderId) {
        glideLoading(mContext, url, imageview, placeholderId, R.mipmap.bg_loading_pic);
    }

    public static void glideLoading(Context mContext, String url, ImageView imageview, int placeholderId, int errorId) {
        if (mContext == null) return;
        loadWithContext(mContext, url)
                .apply(mRequestOptions)
//                .animate(R.anim.glide_loading_image_alpha_in)
//                .centerCrop()
                .into(imageview);
    }

    /**
     * @param mContext
     * @param header    http://images.dmzj.com/
     * @param url
     * @param imageview
     */
    public static void glideLoading(Context mContext, String header, String url, ImageView imageview) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Referer", header);
        glideLoading(mContext, headers, url, imageview);
    }

    public static void glideLoading(Context mContext, final HashMap<String, String> header, String url, ImageView imageview) {
        Headers headers = new Headers() {
            @Override
            public Map<String, String> getHeaders() {
                return header;
            }
        };
        GlideUrl gliderUrl = new GlideUrl(url, headers);
        Glide.with(mContext).load(gliderUrl).apply(mRequestOptions).into(imageview);
    }

    public static void glideLoading(Context mContext, String url, DrawableImageViewTarget drawableImageViewTarget) {
        loadWithContext(mContext, url).apply(mRequestOptions).into(drawableImageViewTarget);
    }

    public static void glideLoading(Context mContext, String url, SimpleTarget target) {
        RequestOptions requestOptions = mRequestOptions
                .clone()
                .format(DecodeFormat.PREFER_ARGB_8888);
        GlideApp.with(mContext)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
//                .transition(withCrossFade())
                .into(target);
    }

    public static void glideCircleLoading(Context mContext, String url, ImageView imageview) {
        RequestOptions requestOptions;
//        if (placeholder != R.mipmap.bg_loading_pic || errorResId != R.drawable.bg_default_image) {
//            requestOptions = new RequestOptions()
//                    .placeholder(R.mipmap.bg_loading_pic)
//                    .error(R.mipmap.bg_loading_pic);
//        } else {
//            requestOptions = mRequestOptions.clone();
//        }
        requestOptions = new RequestOptions().placeholder(R.mipmap.bg_loading_pic).error(R.mipmap.bg_loading_pic);
        requestOptions.transform(new GlideCircleTransform());
        loadWithContext(mContext, url).apply(mRequestOptions).into(imageview);
    }

    /**
     * 清除图片磁盘缓存
     */
    private static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
//                        BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        File storageDirectory = CacheUtils.getCacheDirectory(context, "/cache/image");
//        deleteFolderFile(storageDirectory.getPath(), true);
    }

    /**
     * 获取Glide造成的缓存大小
     */
    public static String getCacheSize(Context context) {
        try {
            File storageDirectory = CacheUtils.getCacheDirectory(context, "/cache/image");
            return getFormatSize(getFolderSize(storageDirectory));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     */
    private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "MB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
//                imageView.setImageResource(R.mipmap.bg_loading_pic);
                bitmap.recycle();
            }
        }
    }

    public static Bitmap ImageViewResouceBitmap(ImageView imageView) {
        if (imageView == null) return null;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            return bitmap;
        }
        return null;
    }

    private static Object getModel(String url) {
        if (url == null || url.isEmpty()) return url;
        if (url.startsWith("/")) return url;
        return new GlideUrlNoParams(url);
    }
}
