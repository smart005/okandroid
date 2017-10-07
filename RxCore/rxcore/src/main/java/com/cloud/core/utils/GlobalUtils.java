/**
 * @Title: GlobalUtils.java
 * @Description: 基础工具类
 * @author: lijinghuan
 * @data: 2015-1-14 上午10:53:51
 */
package com.cloud.core.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cloud.core.ObjectJudge;
import com.cloud.core.ObjectManager;
import com.cloud.core.enums.ResouceType;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GlobalUtils {

    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    private final static String[] hex = {"00", "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
            "1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
            "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
            "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
            "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
            "5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
            "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
            "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
            "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
            "8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
            "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
            "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
            "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
            "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
            "CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
            "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
            "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
            "ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
            "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"};
    private final static byte[] val = {0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
            0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F};

    public static <T> Field getPropertieField(T entity, String attrname) {
        Field field = null;
        if (entity == null) {
            return field;
        }
        try {
            field = entity.getClass().getDeclaredField(attrname);
        } catch (Exception e) {
            Logger.L.error(e, "get propertie field error:", entity.getClass()
                    .getName(), attrname);
        }
        return field;
    }

    public static Field getPropertieField(Class<?> cls, String attrname) {
        Field field = null;
        if (cls == null) {
            return field;
        }
        try {
            field = cls.getDeclaredField(attrname);
        } catch (Exception e) {
            Logger.L.error(e, "get propertie field error:", cls.getName(),
                    attrname);
        }
        return field;
    }

    @SuppressWarnings("unchecked")
    public static <T, TSuper> Object getSuperPropertiesValue(T child,
                                                             String attrname) {
        Object obj = null;
        if (child == null || TextUtils.isEmpty(attrname)) {
            return obj;
        }
        Class<?> cls = child.getClass().getSuperclass();
        Field field = getPropertieField(cls, attrname);
        if (field != null) {
            try {
                field.setAccessible(true);
                String subattrname = attrname.substring(0, 1).toUpperCase()
                        + attrname.substring(1);
                String typename = field.getType().getSimpleName().trim()
                        .toLowerCase();
                if (TextUtils.equals(typename, "boolean")) {
                    subattrname = "is" + subattrname;
                } else {
                    subattrname = "get" + subattrname;
                }
                obj = cls.getMethod(subattrname).invoke((TSuper) child);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                Logger.L.error(e, "get super propertie value error:",
                        cls.getName(), attrname);
            } catch (IllegalArgumentException e) {
                Logger.L.error(e, "get super propertie value error:",
                        cls.getName(), attrname);
            } catch (InvocationTargetException e) {
                Logger.L.error(e, "get super propertie value error:",
                        cls.getName(), attrname);
            } catch (NoSuchMethodException e) {
                Logger.L.error(e, "get super propertie value error:",
                        cls.getName(), attrname);
            }
        }
        return obj;
    }

    /**
     * @param field
     * @param child
     * @param attrvalue
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static <T> void setPrtisValue(Field field, T child, Object attrvalue)
            throws IllegalArgumentException, IllegalAccessException {
        String typename = field.getType().getSimpleName().trim().toLowerCase();
        field.setAccessible(true);
        if (TextUtils.equals(typename, "int")) {
            field.set(child, Integer.parseInt(attrvalue.toString()));
        } else if (TextUtils.equals(typename, "double")) {
            field.set(child, Double.parseDouble(attrvalue.toString()));
        } else if (TextUtils.equals(typename, "float")) {
            field.set(child, Float.parseFloat(attrvalue.toString()));
        } else if (TextUtils.equals(typename, "boolean")) {
            field.setBoolean(child, Boolean.parseBoolean(attrvalue.toString()));
        } else {
            field.set(child, attrvalue);
        }
        field.setAccessible(false);
    }

    public static <T, TSuper> void setSuperPropertiesValue(T child,
                                                           String attrname, Object attrvalue) {
        if (child == null || TextUtils.isEmpty(attrname)) {
            return;
        }
        Class<?> cls = child.getClass().getSuperclass();
        Field field = getPropertieField(cls, attrname);
        if (field != null) {
            try {
                field.setAccessible(true);
                setPrtisValue(field, child, attrvalue);
                field.setAccessible(false);
            } catch (IllegalArgumentException e) {
                Logger.L.error(e, "set super propertie value error:",
                        cls.getName(), attrname);
            } catch (IllegalAccessException e) {
                Logger.L.error(e, "set super propertie value error:",
                        cls.getName(), attrname);
            }
        }
    }

    public static <T> void setPropertiesValue(T child, String attrname,
                                              Object attrvalue) {
        if (child == null || TextUtils.isEmpty(attrname)) {
            return;
        }
        Class<?> cls = child.getClass();
        Field field = getPropertieField(cls, attrname);
        if (field != null) {
            try {
                field.setAccessible(true);
                setPrtisValue(field, child, attrvalue);
                field.setAccessible(false);
            } catch (IllegalArgumentException e) {
                Logger.L.error(e, "set propertie value error:", cls.getName(),
                        attrname, attrvalue + "");
            } catch (IllegalAccessException e) {
                Logger.L.error(e, "set propertie value error:", cls.getName(),
                        attrname, attrvalue + "");
            }
        }
    }

    public static <T> Object getPropertiesValue(T entity, String attrname) {
        Object obj = null;
        if (entity == null) {
            return obj;
        }
        Field field = getPropertieField(entity, attrname);
        if (field != null) {
            try {
                field.setAccessible(true);
                String typename = field.getType().getSimpleName().trim()
                        .toLowerCase();
                if (TextUtils.equals(typename, "boolean")) {
                    String subattrname = attrname.substring(0, 1).toUpperCase()
                            + attrname.substring(1);
                    subattrname = "is" + subattrname;
                    obj = entity.getClass().getMethod(subattrname)
                            .invoke((T) entity);
                } else {
                    obj = field.get(entity);
                }
                field.setAccessible(false);
            } catch (IllegalArgumentException e) {
                Logger.L.error(e, "get propertie value error:", entity
                        .getClass().getName(), attrname);
            } catch (IllegalAccessException e) {
                Logger.L.error(e, "get propertie value error:", entity
                        .getClass().getName(), attrname);
            } catch (InvocationTargetException e) {
                Logger.L.error(e, "get propertie value error:", entity
                        .getClass().getName(), attrname);
            } catch (NoSuchMethodException e) {
                Logger.L.error(e, "get propertie value error:", entity
                        .getClass().getName(), attrname);
            }
        }
        return obj;
    }

    public static <T> Object getPropertiesValue(T entity, Field field) {
        Object obj = null;
        if (field == null) {
            return obj;
        }
        try {
            field.setAccessible(true);
            String typename = field.getType().getSimpleName().trim()
                    .toLowerCase();
            if (TextUtils.equals(typename, "boolean")) {
                String subattrname = field.getName().substring(0, 1)
                        .toUpperCase()
                        + field.getName().substring(1);
                subattrname = "is" + subattrname;
                obj = entity.getClass().getMethod(subattrname)
                        .invoke((T) entity);
            } else {
                obj = field.get(entity);
            }
            field.setAccessible(false);
        } catch (IllegalArgumentException e) {
            Logger.L.error(e, "get propertie value error:", entity.getClass()
                    .getName(), field + "");
        } catch (IllegalAccessException e) {
            Logger.L.error(e, "get propertie value error:", entity.getClass()
                    .getName(), field + "");
        } catch (InvocationTargetException e) {
            Logger.L.error(e, "get propertie value error:", entity.getClass()
                    .getName(), field + "");
        } catch (NoSuchMethodException e) {
            Logger.L.error(e, "get propertie value error:", entity.getClass()
                    .getName(), field + "");
        }
        return obj;
    }

    public static void uninstallAPK(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        String[] args = {"pm", "uninstall", packageName};
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        try {
            processBuilder.start();
        } catch (Exception e) {
            Logger.L.error("uninstall apk error:", e);
        }
    }

    @SuppressWarnings("static-access")
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        if (context == null) {
            return apps;
        }
        PackageManager pManager = context.getPackageManager();
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                apps.add(pak);
            }
        }
        return apps;
    }

    /**
     * 获取guid
     *
     * @return
     */
    public static String getNewGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getGuidNoConnect() {
        return getNewGuid().replace("-", "");
    }

    public static int getHashCodeByUUID() {
        return Math.abs(getGuidNoConnect().hashCode());
    }

    public static void exitApp(Context context, String packgename) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion > Build.VERSION_CODES.ECLAIR_MR1) {
            System.exit(1);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        manager.killBackgroundProcesses(packgename);
    }

    public static Intent getInstallIntent(String apkuri) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkuri),
                "application/vnd.android.package-archive");
        // android4.0以前可以出现安装成功界面,
        // 但在4.0或以后版本不加FLAG_ACTIVITY_NEW_TASK则不会出现安装完成界面;
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return i;
    }

    public static String escape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') { // space : map to '+'
                sbuf.append('+');
            } else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-'
                    || ch == '_' // unreserved : as it was
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*'
                    || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch <= 0x007F) { // other ASCII : map to %XX
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else { // unicode : map to %uXXXX
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }

    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') { // + : map to ' '
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-'
                    || ch == '_' // unreserved : as it was
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*'
                    || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) { // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                } else { // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            }
            i++;
        }
        return sbuf.toString();
    }

    /**
     * 显示软键盘
     *
     * @param editText 键盘显示定位的EditText
     */
    public static void showSoftInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 字符串反转
     *
     * @param content 要处理的内容
     */
    public static String reverse(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        int count = content.length();
        StringBuffer sb = new StringBuffer();
        for (int i = count - 1; i >= 0; i--) {
            sb.append(content.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 根据资源名称获取id
     *
     * @param context      上下文
     * @param resouceType  资源类型
     * @param resourceName 资源名称
     * @param packageName  获取资源的包名
     * @return
     */
    public static int getIdByResourceName(Context context, ResouceType resouceType,
                                          String resourceName, String packageName) {
        if (context == null || TextUtils.isEmpty(resourceName) || TextUtils.isEmpty(packageName)) {
            return 0;
        }
        String resTypeName = "";
        if (resouceType.ordinal() == ResouceType.drawable.ordinal()) {
            resTypeName = "drawable";
        } else if (resouceType.ordinal() == ResouceType.layout.ordinal()) {
            resTypeName = "layout";
        }
        return context.getResources().getIdentifier(resourceName, resTypeName,
                packageName);
    }

    /**
     * 获取资源uri
     *
     * @param packageName 包名
     * @param resId       资源id
     * @return
     */
    public static Uri getResourceUri(String packageName, int resId) {
        return Uri.parse("android.resource://" + packageName + "/" + resId);
    }

    /**
     * 获取域名
     *
     * @param url url地址
     * @return
     */
    public static String getDomainName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String domainpattern = String.format(RuleParams.MatchTagBetweenContent.getValue(), "http://", "");
        String domainresult = ValidUtils.matche(domainpattern, url);
        if (!TextUtils.isEmpty(domainresult)) {
            if (domainresult.indexOf(":") >= 0) {
                domainresult = domainresult.substring(0, domainresult.indexOf(":"));
            } else if (domainresult.indexOf("/") >= 0) {
                domainresult = domainresult.substring(0, domainresult.indexOf("/"));
            }
        }
        return domainresult;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = ObjectManager.getWindowManager(context);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = ObjectManager.getWindowManager(context);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postOnAnimationJellyBean(view, runnable);
        } else {
            view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
        }
    }

    @TargetApi(16)
    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    public static int getPointerIndex(int action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            return getPointerIndexHoneyComb(action);
        else
            return getPointerIndexEclair(action);
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static int getPointerIndexEclair(int action) {
        return (action & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static int getPointerIndexHoneyComb(int action) {
        return (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
    }

    /**
     * 显示通知栏异常
     *
     * @param context
     */
    public void showNotification(Context context) {
        try {
            Object service = context.getSystemService("statusbar");
            if (service != null) {
                Method expand = service.getClass().getMethod("expandNotificationsPanel");
                expand.invoke(service);
            }
        } catch (Exception e) {
            Logger.L.error("show notification error:", e);
        }
    }

    public static void cancelTask(AsyncTask<?, ?, ?> task) {
        if (task != null) {
            if (task.getStatus() == AsyncTask.Status.RUNNING
                    || task.getStatus() == AsyncTask.Status.PENDING) {
                task.cancel(true);
            }
        }
    }

    public static HashMap<String, String> getUrlParams(String url) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(url) && url.lastIndexOf("?") >= 0) {
            String paramString = url.substring(url.lastIndexOf("?") + 1);
            if (!TextUtils.isEmpty(paramString)) {
                String[] params = paramString.split("&");
                if (!ObjectJudge.isNullOrEmpty(params)) {
                    for (String param : params) {
                        if (!TextUtils.isEmpty(param)) {
                            String[] itemlst = param.split("=");
                            if (itemlst.length > 1) {
                                hashMap.put(itemlst[0].trim(), itemlst[1].trim());
                            }
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    public static String getSuffixName(String pathOrFileName) {
        if (TextUtils.isEmpty(pathOrFileName)) {
            return "";
        } else {
            int suffixIndex = pathOrFileName.lastIndexOf(".");
            if (suffixIndex >= 0) {
                return pathOrFileName.substring(suffixIndex + 1).toLowerCase();
            } else {
                return pathOrFileName.toLowerCase();
            }
        }
    }

    public static String getHideText(int startNum, int endNum, String hideChar, String text) {
        if (TextUtils.isEmpty(hideChar)) {
            return text;
        }
        if (startNum <= 0 || endNum <= 0 || TextUtils.isEmpty(text)) {
            return "";
        }
        StringBuffer sbtxt = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            if (i > startNum && i < (text.length() - endNum)) {
                sbtxt.append(hideChar);
            } else {
                sbtxt.append(text.charAt(i));
            }
        }
        return sbtxt.toString();
    }

    public static String getHideText(int startNum, int endNum, String text) {
        return getHideText(startNum, endNum, "*", text);
    }

    public static void refreshSystemPhoto(Context context, File file, String fileName) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file + fileName))));
    }
}
