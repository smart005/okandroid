package com.cloud.core.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast = null;
    private static Toast cusToast = null;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     * @param yOffset
     */
    public static void showShort(Context context, CharSequence message,
                                 int yOffset) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, yOffset);
        } else {
            if (toast.getDuration() == Toast.LENGTH_SHORT) {
                toast.setView(toast.getView());
                toast.setText(message);
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, yOffset);
            }
        }
        toast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        showShort(context, message, -16);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param resId
     * @param yOffset
     */
    public static void showShort(Context context, int resId, int yOffset) {
        if (toast == null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, yOffset);
        } else {
            if (toast.getDuration() == Toast.LENGTH_SHORT) {
                toast.setView(toast.getView());
                toast.setText(resId);
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, yOffset);
            }
        }
        toast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param resId
     */
    public static void showShort(Context context, int resId) {
        showShort(context, resId, -16);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     * @param yOffset
     */
    public static void showLong(Context context, CharSequence message,
                                int yOffset) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, yOffset);
        } else {
            if (toast.getDuration() == Toast.LENGTH_LONG) {
                toast.setView(toast.getView());
                toast.setText(message);
                toast.setDuration(Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, yOffset);
            }
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        showLong(context, message, -16);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param resId
     * @param yOffset
     */
    public static void showLong(Context context, int resId, int yOffset) {
        if (toast == null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, yOffset);
        } else {
            if (toast.getDuration() == Toast.LENGTH_LONG) {
                toast.setView(toast.getView());
                toast.setText(resId);
                toast.setDuration(Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, yOffset);
            }
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param resId
     */
    public static void showLong(Context context, int resId) {
        showLong(context, resId, -16);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     * @param yOffset
     */
    public static void show(Context context, CharSequence message,
                            int duration, int yOffset) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.CENTER, 0, yOffset);
        } else {
            toast.setView(toast.getView());
            toast.setText(message);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        show(context, message, duration, -16);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param resId
     * @param duration
     * @param yOffset
     */
    public static void show(Context context, int resId, int duration,
                            int yOffset) {
        if (null == toast) {
            toast = Toast.makeText(context, resId, duration);
            toast.setGravity(Gravity.CENTER, 0, yOffset);
        } else {
            toast.setView(toast.getView());
            toast.setText(resId);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param resId
     * @param duration
     */
    public static void show(Context context, int resId, int duration) {
        show(context, resId, duration, -16);
    }

    public static void hide() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
