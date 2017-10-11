package com.cloud.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.cloud.core.utils.BaseRedirectUtils;


/**
 * @Author lijinghuan
 * @Email: ljh0576123@163.com
 * @CreateTime:2016/4/1 18:54
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class RedirectUtils extends BaseRedirectUtils {

    public static void startActivity(Activity activity, Class<?> cls) {
        startActivity(activity, cls, null);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        startActivityForResult(activity, cls, null, requestCode);
    }

    /**
     * 打开Open设置页面
     *
     * @param context
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void startPkgActivity(Activity activity, String packageName, String classFullName) {
        startPkgActivity(activity, packageName, classFullName, null);
    }

    public static void startPkgActivity(Activity activity, String packageName, String classFullName, Bundle bundle) {
        if (TextUtils.isEmpty(classFullName)) {
            return;
        }

        Intent intent = new Intent();
        intent.setClassName(packageName, classFullName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void startPkgActivityForResult(Activity activity, String packageName, String classFullName, int requestCode) {
        startPkgActivityForResult(activity, packageName, classFullName, null, requestCode);
    }

    public static void startPkgActivityForResult(Activity activity, String packageName, String classFullName, Bundle bundle, int requestCode) {
        if (TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(packageName, classFullName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
