package com.cloud.core.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-9-6 下午2:29:45
 * @Description: SDCard工具类
 * @Modifier:
 * @ModifyContent:
 */
public class SDCardUtils {

    /**
     * 获取SDCard状态
     *
     * @return
     */
    public static String getSDCardsStatus() {
        return Environment.getExternalStorageState();
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean hasSdcard() {
        return getSDCardsStatus().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard目录
     *
     * @return
     */
    public static File getSDCardFile() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return getSDCardFile().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (hasSdcard()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {
            // 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = getSDCardFile().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return getSDCardFile().getAbsolutePath();
    }
}
