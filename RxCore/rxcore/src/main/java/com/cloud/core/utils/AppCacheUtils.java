package com.cloud.core.utils;

import android.content.Context;

import com.cloud.core.logger.Logger;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-8-14 上午10:59:25
 * @Description: 应用缓存管理工具
 * @Modifier:
 * @ModifyContent:
 */
public class AppCacheUtils {

    private File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * copy应用程序缓存目录文件至sdcar目录
     *
     * @param context
     */
    public void copyCacheFiles(Context context) {
        File dest = StorageUtils.getCachesDir();
        StorageUtils.copyFiles(getCacheDir(context), dest);
    }

    public void copyAll(Context context, String cacheDbName) {
        try {
            File dest = StorageUtils.getCachesDir();
            File destdbfile = new File(dest, cacheDbName);
            if (destdbfile.exists()) {
                destdbfile.delete();
            }
            StorageUtils.copyFiles(getCacheDir(context), dest);
        } catch (Exception e) {
            Logger.L.error("move application cache data error:", e);
        }
    }

    public void clearAllCache(Context context) {
        try {
            StorageUtils.deleteDirectory(getCacheDir(context));
            StorageUtils.deleteDirectory(StorageUtils.getImageDir());
            StorageUtils.deleteDirectory(StorageUtils.getAudioDir());
            StorageUtils.deleteDirectory(StorageUtils.getVideoDir());
            StorageUtils.deleteDirectory(StorageUtils.getErrorDir());
            StorageUtils.deleteDirectory(StorageUtils.getDebugDir());
            StorageUtils.deleteDirectory(StorageUtils.getInfoDir());
            StorageUtils.deleteDirectory(StorageUtils.getWarningDir());
            StorageUtils.deleteDirectory(StorageUtils.getPluginDir());
            StorageUtils.deleteDirectory(StorageUtils.getApksDir());
            StorageUtils.deleteDirectory(StorageUtils.getQRCodeDir());
            StorageUtils.deleteDirectory(StorageUtils.getDataCachesDir());
            StorageUtils.deleteDirectory(StorageUtils.getApatchDir());
            StorageUtils.deleteDirectory(StorageUtils.getTobeProcessed());
            StorageUtils.deleteDirectory(StorageUtils.getOssRecord());
        } catch (Exception e) {
            Logger.L.error("clear all cache error:", e);
        }
    }

    public long getCacheSize(Context context) {
        long mfsize = StorageUtils.getFileOrDirSize(getCacheDir(context));
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getApksDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getImageDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getInfoDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getQRCodeDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getDataCachesDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getPluginDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getWarningDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getDebugDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getErrorDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getAudioDir());
        mfsize += StorageUtils.getFileOrDirSize(StorageUtils.getApatchDir());
        return mfsize;
    }
}
