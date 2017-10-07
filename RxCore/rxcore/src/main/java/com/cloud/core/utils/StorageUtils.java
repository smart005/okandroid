package com.cloud.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.cloud.core.beans.StorageInitParam;
import com.cloud.core.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StorageUtils {

    private static StorageInitParam storageInitParam = null;
    public static StorageUtils.OnStorageInitListener onStorageInitListener = null;

    public static void setOnStorageInitListener(OnStorageInitListener listener) {
        onStorageInitListener = listener;
    }

    public interface OnStorageInitListener {
        public StorageInitParam getStorageInit();
    }

    /**
     * 是否存在sd卡
     *
     * @return
     */
    public static boolean hasStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static boolean initParam() {
        if (storageInitParam == null) {
            if (onStorageInitListener == null) {
                return false;
            }
            storageInitParam = onStorageInitListener.getStorageInit();
            if (storageInitParam == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * sd卡目录下，创建应用主目录
     *
     * @return
     */
    private static File getApp() {
        if (hasStorage()) {
            return createDirectory(Environment.getExternalStorageDirectory(), storageInitParam.getAppDir());
        } else {
            return createDirectory(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), storageInitParam.getAppDir());
        }
    }

    public static File getRootDir() {
        if (!initParam()) {
            return null;
        }
        return getApp();
    }

    /**
     * 获取{@link StorageInitParam}目录下的子目录 如果不存在，则自动创建
     *
     * @param dirname
     * @return
     */
    private static File getDir(String dirname) {
        return createDirectory(getApp(), dirname);
    }

    /**
     * 获取图片目录
     *
     * @return
     */
    public static File getImageDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getImagesDir());
    }

    /**
     * 获取下载目录
     *
     * @return
     */
    public static File getDownloadDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getDownload());
    }

    /**
     * 获取音频文件目录
     *
     * @return
     */
    public static File getAudioDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getAudiosDir());
    }

    /**
     * 获取视频文件目录
     *
     * @return
     */
    public static File getVideoDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getVideoDir());
    }

    /**
     * 获取错误日志目录
     *
     * @return
     */
    public static File getErrorDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getErrorsDir());
    }

    /**
     * 获取Debug日志目录
     *
     * @return
     */
    public static File getDebugDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getDebugsDir());
    }

    /**
     * 获取Info日志目录
     *
     * @return
     */
    public static File getInfoDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getInfosDir());
    }

    /**
     * 获取警告类日志目录
     *
     * @return
     */
    public static File getWarningDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getWarningsDir());
    }

    /**
     * 获取插件目录
     *
     * @return
     */
    public static File getPluginDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getPluginsDir());
    }

    /**
     * 获取程序更新目录
     *
     * @return
     */
    public static File getApksDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getApksDir());
    }

    /**
     * 获取程序缓存目录
     *
     * @return
     */
    public static File getCachesDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getCachesDir());
    }

    /**
     * 获取程序数据缓存目录
     *
     * @return
     */
    public static File getDataCachesDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getDataCachesDir());
    }

    /**
     * 获取二维码目录
     *
     * @return
     */
    public static File getQRCodeDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getQrcodesDir());
    }

    /**
     * 获取插件目录
     *
     * @return
     */
    public static File getApatchDir() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getApatchsDir());
    }

    /**
     * 获取待处理目录
     *
     * @return
     */
    public static File getTobeProcessed() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getTodosDir());
    }

    /**
     *
     * @return
     */
    public static File getOssRecord() {
        if (!initParam()) {
            return null;
        }
        return getDir(storageInitParam.getOssRecord());
    }

    /**
     * 创建目录
     *
     * @param dir  主目录
     * @param dest 需要创建的子目录
     * @return
     */
    public static File createDirectory(File dir, String dest) {
        File result = new File(dir, File.separator + dest + File.separator);
        if (!result.exists()) {
            result.mkdirs();
        }
        return result;
    }

    /**
     * 创建文件
     *
     * @param dir  目录 绝对路径。如:/sdcard/childdir
     * @param file
     * @return
     * @throws IOException 创建失败,抛出异常 文件
     */
    public static File createFile(File dir, String name, boolean delete)
            throws IOException {
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        File result = new File(dir, name);
        if (result.exists()) {
            if (delete) {// 删除原来文件重新创建
                result.delete();
                result.createNewFile();
            } else {
                // do nothing;
            }
        } else {// 直接创建文件
            result.createNewFile();
        }

        return result;
    }

    /**
     * 删除目录或者文件
     *
     * @param filepath
     * @return
     */
    public static boolean deleteQuietly(String filepath) {
        File file = new File(filepath);
        return deleteQuietly(file);
    }

    /**
     * @param dir      父目录
     * @param filepath 文件名称
     * @return
     */
    public static boolean deleteQuietly(String dir, String filepath) {
        File file = new File(dir, filepath);
        return deleteQuietly(file);
    }

    /**
     * 删除目录或者文件。 目录则递归删除， 文件直接删除 不抛出异常
     *
     * @param file
     * @return
     */
    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception ignored) {
        }

        try {
            return file.delete();
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 删除目录中的文件及目录 支持递归删除 删除目录本身 抛出异常
     *
     * @param directory
     * @throws IOException
     */
    public static void deleteDirectory(File directory) {
        if (!directory.exists()) {
            return;
        }
        if (!isSymlink(directory)) {// 不是符号链接的，递归删除
            cleanDirectory(directory);
        }
        directory.delete();
    }

    /**
     * 递归删除目录中的目录或者文件 不删除目录本身 抛出异常
     *
     * @param directory
     * @throws IOException
     */
    public static void cleanDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        IOException exception = null;
        for (File file : files) {
            forceDelete(file);
        }
    }

    /**
     * 删除目录或者文件 支持递归删除 删除目录本身
     *
     * @param file
     * @throws IOException
     */
    public static void forceDelete(File file) {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (filePresent) {
                file.delete();
            }
        }
    }

    /**
     * 是否符号链接判断
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isSymlink(File file) {
        try {
            if (file == null) {
                throw new NullPointerException("File must not be null");
            }
            if (FilenameUtils.isSystemWindows()) {
                return false;
            }
            File fileInCanonicalDir = null;
            if (file.getParent() == null) {
                fileInCanonicalDir = file;
            } else {
                File canonicalDir = file.getParentFile().getCanonicalFile();
                fileInCanonicalDir = new File(canonicalDir, file.getName());
            }

            if (fileInCanonicalDir.getCanonicalFile().equals(
                    fileInCanonicalDir.getAbsoluteFile())) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static void copyFiles(String fromFile, String toFile) {
        // 要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        // 如同判断SD卡是否存在或者文件是否存在
        // 如果不存在则 return出去
        if (!root.exists()) {
            return;
        }
        // 如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
        // 目标目录
        File targetDir = new File(toFile);
        // 创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        // 遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory()) {
                // 如果当前项为子目录 进行递归
                File childdir = new File(toFile, currentFiles[i].getName());
                copyFiles(currentFiles[i].getPath() + "/",
                        childdir.getAbsolutePath());
            } else {
                // 如果当前项为文件则进行文件拷贝
                File mfile = new File(toFile, currentFiles[i].getName());
                copyFile(currentFiles[i].getPath(), mfile.getAbsolutePath());
            }
        }
    }

    public static void copyFiles(File fromFile, File toFile) {
        String frompath = fromFile.getAbsolutePath();
        String topath = toFile.getAbsolutePath();
        copyFiles(frompath, topath);
    }

    public static void copyFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[4096];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
        } catch (Exception e) {
            Logger.L.error("copy file error:", e);
        }
    }

    public static void copyFile(File fromFile, File toFile) {
        String frompath = fromFile.getAbsolutePath();
        String topath = toFile.getAbsolutePath();
        copyFile(frompath, topath);
    }

    public static void save(String content, File tofile) {
        try {
            if (TextUtils.isEmpty(content) || tofile == null) {
                return;
            }
            content = content.trim();
            if (tofile.exists()) {
                tofile.delete();
            }
            tofile.createNewFile();
            byte[] bs = content.getBytes();
            OutputStream fosto = new FileOutputStream(tofile);
            fosto.write(bs, 0, content.length());
            fosto.close();
        } catch (Exception e) {
            Logger.L.error("save content to sdcard error:", e);
        }
    }

    public static void appendContent(String content, File tofile) {
        try {
            if (TextUtils.isEmpty(content) || tofile == null) {
                return;
            }
            if(tofile.getParentFile()==null){
                return;
            }
            content = content.trim();
            if (!tofile.exists()) {
                tofile.createNewFile();
            }
            byte[] bs = content.getBytes();
            OutputStream fosto = new FileOutputStream(tofile, true);
            fosto.write(bs, 0, content.length());
            fosto.close();
        } catch (Exception e) {
            Logger.L.error("append content to sdcard error:", e);
        }
    }

    /**
     * 获取文件(一般指文本文件)内容
     *
     * @param targetfile 目标文件
     * @return 内容
     */
    public static String readContent(File targetfile) {
        try {
            if (targetfile == null || !targetfile.exists()) {
                return "";
            }
            String result = "";
            FileInputStream fis = new FileInputStream(targetfile);
            int lenght = fis.available();
            byte[] buffer = new byte[lenght];
            fis.read(buffer);
            result = new String(buffer, "UTF-8");
            fis.close();
            return result;
        } catch (Exception e) {
            Logger.L.warn("read file content error:", e);
        }
        return "";
    }

    public static String readContent(InputStream is) {
        try {
            if (is == null || is.available() <= 0) {
                return "";
            }
            String result = "";
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
            return result;
        } catch (Exception e) {
            Logger.L.warn("read InputStream content error:", e);
        }
        return "";
    }

    /**
     * 从assets取文本文件内容
     *
     * @param context
     * @param fileName 文件名称
     * @return
     */
    public static String readAssetsFileContent(Context context, String fileName) {
        try {
            if (context == null || TextUtils.isEmpty(fileName)) {
                return "";
            }
            String result = "";
            InputStream is = context.getAssets().open(fileName);
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
            return result;
        } catch (Exception e) {
            Logger.L.error("read file content error:", e);
        }
        return "";
    }

    /**
     * 保存Bitmap
     *
     * @param dir      目录
     * @param filename 文件名
     * @param bt       图片
     * @return
     */
    public static File saveBitmap(File dir, String filename, Bitmap bt) {
        try {
            File mfile = new File(dir, filename);
            if (mfile.exists()) {
                mfile.delete();
            }
            mfile.createNewFile();
            FileOutputStream out = new FileOutputStream(mfile);
            bt.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return mfile;
        } catch (Exception e) {
            Logger.L.error("save bitmap error:", e);
        }
        return null;
    }

    /**
     * 获取文件或目录大小(单位为字节)
     *
     * @param fileOrDirPath 文件或目录
     * @return
     */
    public static long getFileOrDirSize(File fileOrDirPath) {
        return getFolderSize(fileOrDirPath);
    }

    /**
     * 获取文件或目录大小(单位为字节)
     *
     * @param fileOrDirPath 文件或目录
     * @return
     */
    public static long getFileOrDirSize(String fileOrDirPath) {
        File file = new File(fileOrDirPath);
        return getFolderSize(file);
    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }
}
