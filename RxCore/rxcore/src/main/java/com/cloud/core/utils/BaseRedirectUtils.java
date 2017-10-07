/**
 * @Title: BaseRedirectUtils.java
 * @Description:
 * @author: lijinghuan
 * @data: 2015年5月4日 下午8:31:13
 */
package com.cloud.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.core.ObjectJudge;


public class BaseRedirectUtils {

    public static void startService(Context context, Class<?> cls, String action, Bundle bundle) {
        Intent _intent = new Intent();
        if (!TextUtils.isEmpty(action)) {
            _intent.setAction(action);
        }
        _intent.setClass(context, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        context.startService(_intent);
    }

    public static void startService(Context context, Class<?> cls, String action) {
        startService(context, cls, action, null);
    }

    public static void startService(Context context, Class<?> cls, Bundle bundle) {
        startService(context, cls, "", bundle);
    }

    public static void startService(Context context, Class<?> cls) {
        startService(context, cls, "", null);
    }

    public static void stopService(Context context, Class<?> cls) {
        Intent _intent = new Intent();
        _intent.setClass(context, cls);
        context.stopService(_intent);
    }

    public static void bindService(Context context, ServiceConnection conn,
                                   String action) {
        Intent _intent = new Intent(action);
        context.bindService(_intent, conn, Context.BIND_AUTO_CREATE);
    }

    public static void unbindService(Context context, ServiceConnection conn) {
        context.unbindService(conn);
    }

    public static void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent _intent = new Intent();
        _intent.setClass(activity, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivity(_intent);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, Bundle bundle, int requestCode) {
        Intent _intent = new Intent();
        _intent.setClass(activity, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivityForResult(_intent, requestCode);
    }

    /**
     * 启动Activity
     *
     * @param activity         当前Activity
     * @param classPackageName 需要启动类对应的包名
     * @param classFullName    需要启动类全称
     * @param bundle
     * @param enterAnim        进入动画
     * @param exitAnim         退出动画
     */
    public static void startActivity(Activity activity, String classFullName, Bundle bundle) {
        if (activity == null || TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClassName(activity, classFullName);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivity(_intent);
    }

    /**
     * 启动Activity
     *
     * @param activity         当前Activity
     * @param classPackageName 需要启动类对应的包名
     * @param classFullName    需要启动类全称
     * @param bundle
     */
    public static void startActivity(Context context, String classFullName, Bundle bundle) {
        if (context == null || TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClassName(context, classFullName);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        context.startActivity(_intent);
    }

    /**
     * 启动Activity
     *
     * @param activity         当前Activity
     * @param classPackageName 需要启动类对应的包名
     * @param classFullName    需要启动类全称
     * @param bundle
     * @param requestCode
     * @param enterAnim        进入动画
     * @param exitAnim         退出动画
     */
    public static void startActivityForResult(Activity activity, String classFullName, Bundle bundle, int requestCode) {
        if (activity == null || TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClassName(activity, classFullName);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivityForResult(_intent, requestCode);
    }

    public static void finishActivity(Activity activity) {
        activity.finish();
    }

    public static void sendBroadcast(Context context, String action,
                                     String permission, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (TextUtils.isEmpty(permission)) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, permission);
        }
    }

    public static void sendBroadcast(Context context, String action,
                                     Bundle bundle) {
        sendBroadcast(context, action, "", bundle);
    }

    public static void sendBroadcast(Context context, Bundle bundle) {
        // ReceiverActions.JRJR_RECEIVE_ACTION("208148512")
        sendBroadcast(context, "208148512", bundle);
    }

    public static <T> void sendBroadcast(Context context, String action,
                                         T... params) {
        if (ObjectJudge.isNullOrEmpty(params)) {
            return;
        }
        Bundle mbundle = new Bundle();
        String jsondata = JsonUtils.toStr(params);
        mbundle.putString("RECEIVE_DATA", jsondata);
        sendBroadcast(context, action, "", mbundle);
    }

    public static void callTel(Context context, String phonenumber) {
        Intent intent = null;
        if (TextUtils.isEmpty(phonenumber)) {
            return;
        }
        if (phonenumber.contains("tel")) {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse(phonenumber));
        } else {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse(String.format(
                    "tel:%s", phonenumber)));
        }
        context.startActivity(intent);
    }

    /**
     * 启动桌面
     *
     * @param context
     */
    public static void startHome(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }

    /**
     * 判断某个Activity是否存在
     *
     * @param context
     * @param packageName
     * @param className
     * @return
     */
    public boolean isActivityExist(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
            return false;
        } else {
            return true;
        }
    }
}
