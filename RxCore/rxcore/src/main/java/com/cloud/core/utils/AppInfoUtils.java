package com.cloud.core.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cloud.core.ObjectManager;
import com.cloud.core.beans.DeviceInfo;
import com.cloud.core.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author LiJingHuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-3 上午11:01:01
 * @Description:app相关信息工具类
 * @Modifier:
 * @ModifyContent:
 */
public class AppInfoUtils {

    public static String getAppName(Context context, int pid) {
        String processName = null;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : am
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        return processName;
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位) 读取失败为""
     */
    public static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException ex) {
            // get cpu serial error
        }
        return cpuAddress;
    }

    /**
     * @param context
     * @return
     */
    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo dvinfo = new DeviceInfo();
        try {
            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            dvinfo.setMac(wifi.getConnectionInfo().getMacAddress());
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            dvinfo.setImei(getCPUSerial());
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(tm.getDeviceId());
            }
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(Build.SERIAL);
            }
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(dvinfo.getMac());
            }
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID));
            }
            dvinfo.setImsi(tm.getSubscriberId());
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(dvinfo.getImsi());
            }
            dvinfo.setSimSerialNumber(tm.getSimSerialNumber());
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(dvinfo.getSimSerialNumber());
            }
            dvinfo.setLocalTel(tm.getLine1Number());
            dvinfo.setSimPprovidersName(tm.getSimOperatorName());
            dvinfo.setModel(Build.MODEL);
            dvinfo.setRelease(Build.VERSION.RELEASE);
            dvinfo.setSerialNumber(Build.SERIAL);
            if (TextUtils.isEmpty(dvinfo.getImei())) {
                dvinfo.setImei(dvinfo.getSerialNumber());
            }
            dvinfo.setSdkVersion(Build.VERSION.SDK_INT);
        } catch (Exception e) {
            Logger.L.error("get device info error:", e);
        }
        return dvinfo;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.L.error("WifiPreference IpAddress error:", e);
        }
        return "127.0.0.1";
    }

    /**
     * 获取可执行文件(apk或jar)包信息
     *
     * @param context
     * @param file
     * @return
     */
    public static PackageInfo getPackageInfoByFile(Context context, File file) {
        PackageManager pm = ObjectManager.getPackageManager(context);
        PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(),
                PackageManager.GET_ACTIVITIES);
        return info;
    }

    public static <T extends Application> PackageInfo getPackageInfo(T application) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = application.getPackageManager();
        String packageName = application.getPackageName();
        PackageInfo info = packageManager.getPackageInfo(packageName, 0);
        return info;
    }

    public static <T extends Application> Bundle getApplicationMetaData(T application) {
        try {
            PackageManager packageManager = application.getPackageManager();
            String packageName = application.getPackageName();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Bundle mbundle = appInfo.metaData;
            return mbundle;
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return new Bundle();
    }

    public static <T extends Application> String getMetaName(T application, String metaKey) {
        try {
            String metaName = "";
            Bundle mbundle = AppInfoUtils.getApplicationMetaData(application);
            if (mbundle.containsKey(metaKey)) {
                metaName = mbundle.getString(metaKey);
            }
            return metaName;
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }

    /**
     * 获得app进程名
     *
     * @param context 宿主上下文
     */
    public static String getAppProcessName(Context context) {
        int pid = android.os.Process.myPid();
        return getAppName(context, pid);
    }

    /**
     * 获取cup架构
     *
     * @return
     */
    public static List<String> getCPUAbis() {
        List<String> lst = new ArrayList<String>();
        try {
            String[] abis = new String[]{};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                abis = Build.SUPPORTED_ABIS;
            } else {
                abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            for (String abi : abis) {
                lst.add(abi);
            }
        } catch (Exception e) {
            Logger.L.error("get cup abis error:", e);
        }
        return lst;
    }
}
