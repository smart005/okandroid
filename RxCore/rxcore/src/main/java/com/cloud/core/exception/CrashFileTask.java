package com.cloud.core.exception;

import android.os.Build;
import android.text.TextUtils;

import com.cloud.core.beans.CrashFileParam;
import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.enums.LogLevel;
import com.cloud.core.utils.DateUtils;
import com.cloud.core.utils.StorageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-11 上午10:49:22
 * @Description: 缓存异常信息至文件
 * @Modifier:
 * @ModifyContent:
 */
public class CrashFileTask {

    private static final String STACK_TRACE = "STACK_TRACE";
    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = "txt";

    public static void writeLog(CrashFileParam mcfp) {
        try {
            if (mcfp.getCrashValue() == null) {
                return;
            }
            String crashInfo = "";
            if (mcfp.getCrashValue() instanceof Throwable) {
                crashInfo = getCrashInfo((Throwable) mcfp.getCrashValue());
            } else if (mcfp.getCrashValue() instanceof String) {
                crashInfo = mcfp.getCrashValue().toString();
            }
            if (!TextUtils.isEmpty(crashInfo)) {
                Properties mDeviceCrashInfo = collectCrashDeviceInfo();
                if (!TextUtils.isEmpty(mcfp.getMessage())) {
                    mDeviceCrashInfo.put("TAGMESSAGE:", mcfp.getMessage());
                }
                if (!TextUtils.isEmpty(mcfp.getPackageInfo().packageName)) {
                    mDeviceCrashInfo.put("PACKAGENAME:", mcfp.getPackageInfo().packageName);
                }
                if (!TextUtils.isEmpty(mcfp.getPackageInfo().versionName)) {
                    mDeviceCrashInfo.put("VERSIONNAME:", mcfp.getPackageInfo().versionName);
                }
                if (mcfp.getPackageInfo().versionCode > 0) {
                    mDeviceCrashInfo.put("VERSIONCODE:", String.valueOf(mcfp.getPackageInfo().versionCode));
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getImei())) {
                    mDeviceCrashInfo.put("IMEI:", mcfp.getDeviceInfo().getImei());
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getImsi())) {
                    mDeviceCrashInfo.put("IMSI:", mcfp.getDeviceInfo().getImsi());
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getMac())) {
                    mDeviceCrashInfo.put("MAC:", mcfp.getDeviceInfo().getMac());
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getModel())) {
                    mDeviceCrashInfo.put("MODEL:", mcfp.getDeviceInfo().getModel());
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getRelease())) {
                    mDeviceCrashInfo.put("RELEASE:", mcfp.getDeviceInfo().getRelease());
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getSerialNumber())) {
                    mDeviceCrashInfo.put("SERIALNUMBER:", mcfp.getDeviceInfo().getSerialNumber());
                }
                if (!TextUtils.isEmpty(mcfp.getDeviceInfo().getSimPprovidersName())) {
                    mDeviceCrashInfo.put("SIMPPROVIDESNAME:", mcfp.getDeviceInfo().getSimPprovidersName());
                }
                if (mcfp.getDeviceInfo().getSdkVersion() > 0) {
                    mDeviceCrashInfo.put("SDKVERSION:", String.valueOf(mcfp.getDeviceInfo().getSdkVersion()));
                }
                saveToFile(crashInfo, mDeviceCrashInfo, mcfp.getLevel());
            }
        } catch (Exception e) {
            // write log error
        }
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    private static Properties collectCrashDeviceInfo() {
        Properties mDeviceCrashInfo = new Properties();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                // setAccessible(boolean flag)
                // 将此对象的 accessible 标志设置为指示的布尔值。
                // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null) + "");
            } catch (Exception e) {
                // collectCrashDeviceInfo
            }
        }
        return mDeviceCrashInfo;
    }

    /**
     * 获取错误信息
     *
     * @param ex
     * @return
     */
    private static String getCrashInfo(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        // printStackTrace(PrintWriter s)
        // 将此 throwable 及其追踪输出到指定的 PrintWriter
        ex.printStackTrace(printWriter);
        // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        return result;
    }

    /**
     * 保存日志
     *
     * @param crashInfo        错误信息
     * @param mDeviceCrashInfo 设置相关信息
     * @param level            错误等级
     */
    private static void saveToFile(String crashInfo,
                                   Properties mDeviceCrashInfo, LogLevel level) {
        try {
            if (TextUtils.isEmpty(crashInfo)) {
                return;
            }
            File dir = null;
            final String contentperx = crashInfo.length() > 20 ? crashInfo.substring(0, 20)
                    : crashInfo.toString();
            String fileName = String.format("%s_%s.%s",
                    DateUtils.getDateTime(DateFormatEnum.YYYYMMDDHHMMSS),
                    contentperx, CRASH_REPORTER_EXTENSION);
            if (level == LogLevel.DEBUG) {
                dir = StorageUtils.getDebugDir();
            } else if (level == LogLevel.INFO) {
                dir = StorageUtils.getInfoDir();
            } else if (level == LogLevel.WARNING) {
                dir = StorageUtils.getWarningDir();
            } else {
                dir = StorageUtils.getErrorDir();
            }
            if (dir == null) {
                return;
            }
            File[] filelst = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (TextUtils.isEmpty(filename)) {
                        return false;
                    } else if (filename.contains(contentperx)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            if (filelst != null && filelst.length > 0) {
                for (File file : filelst) {
                    file.delete();
                }
            }
            mDeviceCrashInfo.put(STACK_TRACE, crashInfo);
            File mfile = new File(dir, fileName);
            if (!mfile.exists()) {
                mfile.createNewFile();
            }
            // 保存文件
            FileOutputStream trace = new FileOutputStream(mfile, true);
            mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
        } catch (Exception e) {
            // collectCrashDeviceInfo save to file error
        }
    }
}
