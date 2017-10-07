package com.cloud.core.glides;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/13
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //内存缓存
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = defaultMemoryCacheSize / 4;
        int customBitmapPoolSize = defaultBitmapPoolSize;
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        //磁盘缓存(单位字节)
        int cacheSizeBytes = 1024 * 1024 * 256 * 2;
        //builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeBytes));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSizeBytes));
//        String downloadDirectoryPath = StorageUtils.getImageDir().getPath();
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSizeBytes));
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeBytes));
        //设置图片解码格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
//        glide.register(IDataModel.class, InputStream.class, new GlideImageLoader.Factory());
    }
}
