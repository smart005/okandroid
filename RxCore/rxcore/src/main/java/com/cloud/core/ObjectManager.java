package com.cloud.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-9-6 下午2:43:54
 * @Description: 基本对象
 * @Modifier:
 * @ModifyContent:
 */
public class ObjectManager {

    /**
     * 获取WindowManager
     *
     * @param context
     * @return
     */
    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取包管理
     *
     * @param context
     * @return
     */
    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    /**
     * 获取包信息
     *
     * @param context
     * @param packageName 包名
     * @return
     * @throws NameNotFoundException 固件中未找到对应的包名
     */
    public static PackageInfo getPackageInfo(Context context, String packageName)
            throws NameNotFoundException {
        PackageManager pmg = getPackageManager(context);
        String pname = TextUtils.isEmpty(packageName) ? context.getPackageName() : packageName;
        PackageInfo pinfo = pmg.getPackageInfo(pname, 0);
        return pinfo;
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     * @throws NameNotFoundException 固件中未找到对应的包名
     */
    public static PackageInfo getPackageInfo(Context context)
            throws NameNotFoundException {
        return getPackageInfo(context, "");
    }

    /**
     * 获取应用信息
     *
     * @param context
     * @param flags
     * @return
     * @throws NameNotFoundException 固件中未找到对应的包名
     */
    public static ApplicationInfo getApplicationInfo(Context context, int flags)
            throws NameNotFoundException {
        PackageManager pm = getPackageManager(context);
        String pname = context.getPackageName();
        ApplicationInfo appinfo = pm.getApplicationInfo(pname, flags);
        return appinfo;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display disy = getWindowManager(context).getDefaultDisplay();
        disy.getMetrics(dm);
        return dm;
    }
}
