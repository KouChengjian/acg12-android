package org.acg12.utlis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import org.acg12.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by DELL on 2016/11/17.
 */
public class ImageLoadUtils {

    private Context mContext;

    //Fresco
//    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
//    public static final int MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB;
//    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
//    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";
//    private static ImagePipelineConfig sImagePipelineConfig;
//    private static ImagePipelineConfig sOkHttpImagePipelineConfig;

    public ImageLoadUtils(Context mContext){
        this.mContext = mContext;
        // Universal-Image-Loader 配置
        File cacheDir = StorageUtils.getCacheDirectory(mContext);
        @SuppressWarnings("deprecation")
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
                .discCache(new UnlimitedDiskCache(cacheDir))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);
        @SuppressWarnings("unused")
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false).imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true).build();

        // Fresco
        //Fresco.initialize(mContext, getOkHttpImagePipelineConfig(mContext));
    }

    public static DisplayImageOptions getOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
                 .showImageOnLoading(R.mipmap.bg_pic_loading)
                // // 设置图片Uri为空或是错误的时候显示的图片
                 .showImageForEmptyUri(R.mipmap.bg_pic_loading)
                // // 设置图片加载/解码过程中错误时候显示的图片
                 .showImageOnFail(R.mipmap.bg_pic_loading)
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();

        return options;
    }

    public static void universalLoading(String url , ImageView imageview , SimpleImageLoadingListener simpleImageLoadingListener){
        ImageLoader.getInstance().displayImage(url, imageview, getOptions(), simpleImageLoadingListener);
    }

    public static void universalLoading(String url , ImageView imageview){
        ImageLoader.getInstance().displayImage(url, imageview, getOptions() );
    }

    public static  void clearUniversalLoading(){
        ImageLoader.getInstance().clearMemoryCache(); // 清除内存
        ImageLoader.getInstance().clearDiskCache(); // 清除sd卡
    }

    public static void glideLoading(Context mContext ,String url ,ImageView imageview){
        Glide.with(mContext).load(url)
                .placeholder(R.mipmap.bg_pic_loading)
                .animate(R.anim.glide_loading_image_alpha_in)
                .error(R.mipmap.bg_pic_loading)
                .centerCrop()
                .into(imageview);
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
    private static void clearImageMemoryCache(Context context) {
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
        String ImageExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }

    /**
     * 获取Glide造成的缓存大小
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
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

//    /**
//     * 使用Android自带的网络加载图片
//     */
//    private static ImagePipelineConfig getImagePipelineConfig(Context context) {
//        if (sImagePipelineConfig == null) {
//            ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
//            configBuilder.setProgressiveJpegConfig(mProgressiveJpegConfig);
//            configBuilder.setBitmapsConfig(Bitmap.Config.ARGB_4444);
//            configureCaches(configBuilder, context);
//            configureLoggingListeners(configBuilder);
//            configureOptions(configBuilder);
//            sImagePipelineConfig = configBuilder.build();
//        }
//        return sImagePipelineConfig;
//    }
//
//    /**
//     * 使用OkHttp网络库加载图片
//     */
//    private static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
//        if (sOkHttpImagePipelineConfig == null) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            ImagePipelineConfig.Builder configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
//            configureCaches(configBuilder, context);
//            configureLoggingListeners(configBuilder);
//            sOkHttpImagePipelineConfig = configBuilder.build();
//        }
//        return sOkHttpImagePipelineConfig;
//    }
//
//    /**
//     * 配置内存缓存和磁盘缓存
//     */
//    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {
//        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
//                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
//                Integer.MAX_VALUE,                     // Max entries in the cache
//                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
//                Integer.MAX_VALUE,                     // Max length of eviction queue
//                Integer.MAX_VALUE);                    // Max cache entry size
//        configBuilder
//                .setBitmapMemoryCacheParamsSupplier(
//                        new Supplier<MemoryCacheParams>() {
//                            public MemoryCacheParams get() {
//                                return bitmapCacheParams;
//                            }
//                        })
//                .setMainDiskCacheConfig(
//                        DiskCacheConfig.newBuilder(context)
//                                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())
//                                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
//                                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
//                                .build());
//    }
//
//    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
//        Set<RequestListener> requestListeners = new HashSet<>();
//        requestListeners.add(new RequestLoggingListener());
//        configBuilder.setRequestListeners(requestListeners);
//    }
//
//    private static void configureOptions(ImagePipelineConfig.Builder configBuilder) {
//        configBuilder.setDownsampleEnabled(true);
//    }
//
//    //渐进式图片
//    private static ProgressiveJpegConfig mProgressiveJpegConfig = new ProgressiveJpegConfig() {
//        @Override
//        public int getNextScanNumberToDecode(int scanNumber) {
//            return scanNumber + 2;
//        }
//
//        public QualityInfo getQualityInfo(int scanNumber) {
//            boolean isGoodEnough = (scanNumber >= 5);
//            return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
//        }
//    };
//
//    public static void frescoLoading(String url , SimpleDraweeView imageview){
//        Uri uri = Uri.parse(url);
//        imageview.setImageURI(uri);
//    }


    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

}
