/**
 * @Title: ObjectJudge.java
 * @Description:
 * @author: lijinghuan
 * @data: 2015年5月4日 上午7:16:34
 */
package com.cloud.core;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

public class ObjectJudge {
    /**
     * 判断列表是否为空
     *
     * @param list 需要检测的列表集合
     * @return
     */
    public static <T> Boolean isNullOrEmpty(T[] list) {
        if (list != null && list.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param list 需要检测的列表集合
     * @return
     */
    public static <K, V> Boolean isNullOrEmpty(TreeMap<K, V> list) {
        if (list != null && !list.isEmpty() && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param haslist 需要检测的列表集合
     * @return
     */
    public static Boolean isNullOrEmpty(HashSet<?> haslist) {
        if (haslist != null && !haslist.isEmpty() && haslist.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param haslist 需要检测的列表集合
     * @return
     */
    public static Boolean isNullOrEmpty(HashMap<?, ?> haslist) {
        if (haslist != null && !haslist.isEmpty() && haslist.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param haslist 需要检测的列表集合
     * @return
     */
    public static Boolean isNullOrEmpty(Hashtable<?, ?> haslist) {
        if (haslist != null && !haslist.isEmpty() && haslist.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param list 整型数据集合
     * @return
     */
    public static Boolean isNullOrEmpty(int[] list) {
        if (list != null && list.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param longlist 长整型集合
     * @return
     */
    public static Boolean isNullOrEmpty(long[] longlist) {
        if (longlist != null && longlist.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param list 列表集合
     * @return
     */
    public static Boolean isNullOrEmpty(List<?> list) {
        if (list != null && !list.isEmpty() && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param list 数据集合
     * @return
     */
    public static Boolean isNullOrEmpty(Collection<?> list) {
        if (list != null && !list.isEmpty() && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断列表是否为空
     *
     * @param list 数据列表
     * @return
     */
    public static Boolean isNullOrEmpty(String[][] list) {
        if (list != null && list.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断服务是否正在运行
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(100000);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().contains(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackgroundRunning(Context context) {
        try {
            String processName = context.getPackageName();
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null)
                return false;
            List<ActivityManager.RunningAppProcessInfo> processList = activityManager
                    .getRunningAppProcesses();
            if (ObjectJudge.isNullOrEmpty(processList)) {
                return false;
            }
            Boolean flag = false;
            for (ActivityManager.RunningAppProcessInfo process : processList) {
                if (process.processName.startsWith(processName)) {
                    boolean isBackground = process.importance != IMPORTANCE_FOREGROUND
                            && process.importance != IMPORTANCE_VISIBLE;
                    flag = isBackground;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 是否中文字符
     *
     * @param chineseStr
     * @return
     */
    public static boolean isChineseCharacter(String chineseStr) {
        char[] charArray = chineseStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            // 是否是Unicode编码,除了"�"这个字符.这个字符要另外处理
            if ((charArray[i] >= '\u0000' && charArray[i] < '\uFFFD')
                    || ((charArray[i] > '\uFFFD' && charArray[i] < '\uFFFF'))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为真
     *
     * @param object true或>0都为真,否则为假
     * @return
     */
    public static boolean isTrue(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (boolean) object;
        } else {
            String flag = object.toString().trim().toLowerCase();
            if (TextUtils.equals(flag, "true")) {
                return true;
            } else if (ConvertUtils.toInt(flag) > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isBankCard(String bankCard) {
        if (bankCard == null) {
            return false;
        }
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    private static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0 || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static boolean isEmptyString(String content) {
        if (TextUtils.isEmpty(content)) {
            return true;
        } else {
            content = content.toLowerCase();
            if (TextUtils.equals(content, "null") ||
                    TextUtils.equals(content, "(null)")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isRunningActivity(Context context, String packgeName, String activityName) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100000);
            boolean isRunning = false;
            for (ActivityManager.RunningTaskInfo info : list) {
                if (TextUtils.equals(info.topActivity.getShortClassName(), activityName) ||
                        TextUtils.equals(info.baseActivity.getShortClassName(), activityName)) {
                    if (info.topActivity.getPackageName().equals(packgeName) || info.baseActivity.getPackageName().equals(packgeName)) {
                        isRunning = true;
                        break;
                    }
                }
            }
            return isRunning;
        } catch (SecurityException e) {
            Logger.L.error("check activity is running error:", e);
        }
        return false;
    }
}
