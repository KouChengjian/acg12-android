package com.acg12.lib.utils.glide;

import android.content.Context;

import com.acg12.lib.utils.CacheUtils;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by Administrator on 2017/6/23.
 */
@GlideModule
public class GlideCache extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
        File storageDirectory = CacheUtils.getCacheDirectory(context, "/cache/image");
        String downloadDirectoryPath = storageDirectory.getPath();
//        LogUtil.e(downloadDirectoryPath+"===========");
        //设置缓存的大小为100M
        int cacheSize = 100 * 1000 * 1000;
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
    }

//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        super.registerComponents(context, glide, registry);
//    }

    //    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {

//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide) {
//
//    }
}
