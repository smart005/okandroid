package com.cloud.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.cloud.core.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class ConvertUtils {

    public static byte[] toBase64Dec(String src) {
        return Base64.decode(src, Base64.DEFAULT);
    }

    public static String toBase64Enc(byte[] b) {
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap toBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    /**
     * Bitmap 转 File 保存到StorageUtils.getImageDir指定目录下
     *
     * @param btmap
     */
    public static File toFile(Bitmap btmap) {
        try {
            File dir = StorageUtils.getImageDir();
            String filename = String.format("%s.tntimg",
                    GlobalUtils.getNewGuid());
            return StorageUtils.saveBitmap(dir, filename, btmap);
        } catch (Exception e) {
            Logger.L.error("bitmap convert file error:", e);
        }
        return null;
    }

    /**
     * 数字Object对象转int型
     *
     * @param obj          要转换的对象
     * @param defaultvlaue 默认值
     * @return
     */
    public static int toInt(Object obj, int defaultvlaue) {
        int val = defaultvlaue;
        try {
            if (obj != null) {
                String objstr = obj.toString().trim();
                if (!TextUtils.isEmpty(objstr)) {
                    Number number = NumberFormat.getNumberInstance().parse(objstr);
                    val = number.intValue();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            val = defaultvlaue;
        }
        return val;
    }

    /**
     * 数字Object对象转int型(默认值0)
     *
     * @param obj 要转换的对象
     * @return
     */
    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    /**
     * 数字Object对象转long型
     *
     * @param obj          要转换的对象
     * @param defaultvlaue 默认值
     * @return
     */
    public static long toLong(Object obj, int defaultvlaue) {
        long val = defaultvlaue;
        try {
            if (obj != null) {
                String objstr = obj.toString().trim();
                if (!TextUtils.isEmpty(objstr)) {
                    Number number = NumberFormat.getNumberInstance().parse(objstr);
                    val = number.longValue();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            val = defaultvlaue;
        }
        return val;
    }

    /**
     * 数字Object对象转long型
     *
     * @param obj 要转换的对象
     * @return
     */
    public static long toLong(Object obj) {
        return toLong(obj, 0);
    }

    /**
     * 数字Object对象转double型
     *
     * @param obj          要转换的对象
     * @param defaultvlaue 默认值
     * @return
     */
    public static double toDouble(Object obj, double defaultvlaue) {
        double val = defaultvlaue;
        try {
            if (obj != null) {
                String objstr = obj.toString().trim();
                if (!TextUtils.isEmpty(objstr)) {
                    Number number = NumberFormat.getNumberInstance().parse(objstr);
                    val = number.doubleValue();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            val = defaultvlaue;
        }
        return val;
    }

    /**
     * 数字Object对象转double型(默认值1.0)
     *
     * @param obj 要转换的对象
     * @return
     */
    public static double toDouble(Object obj) {
        return toDouble(obj, 1.0);
    }

    /**
     * 数字Object对象转float型
     *
     * @param obj          要转换的对象
     * @param defaultvlaue 默认值
     * @return
     */
    public static float toFloat(Object obj, float defaultvlaue) {
        float val = defaultvlaue;
        try {
            if (obj != null) {
                String objstr = obj.toString().trim();
                if (!TextUtils.isEmpty(objstr)) {
                    Number number = NumberFormat.getNumberInstance().parse(objstr);
                    val = number.floatValue();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            val = defaultvlaue;
        }
        return val;
    }

    /**
     * 数字Object对象转float型(默认值1.0)
     *
     * @param obj 要转换的对象
     * @return
     */
    public static float toFloat(Object obj) {
        return toFloat(obj, 1);
    }

    /**
     * 资源图片转换到Bitmap图片
     *
     * @param context
     * @param resid
     * @return
     */
    public static Bitmap toBitmap(Context context, int resid) {
        return BitmapFactory.decodeResource(context.getResources(), resid);
    }

    /**
     * Bitmap转换到字节数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] toByteArray(Bitmap bitmap) {
        byte[] result = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, output);
            bitmap.recycle();
            result = output.toByteArray();
            output.close();
        } catch (Exception e) {
            Logger.L.error("bitmap convert to byte[] error:", e);
        }
        return result;
    }

    /**
     * 转字符串
     *
     * @param value
     * @return
     */
    public static String toString(Object value) {
        return value != null ? value.toString() : "";
    }

    /**
     * 转换时间至秒
     *
     * @param saveTime 时间
     * @param timeUnit 时间单位
     * @return
     */
    public static int toSeconds(long saveTime, TimeUnit timeUnit) {
        int time = 0;
        if (timeUnit == TimeUnit.NANOSECONDS) {
            time = (int) timeUnit.toSeconds(saveTime);
        } else if (timeUnit == TimeUnit.MICROSECONDS) {
            time = (int) timeUnit.toSeconds(saveTime);
        } else if (timeUnit == TimeUnit.MILLISECONDS) {
            time = (int) timeUnit.toSeconds(saveTime);
        } else if (timeUnit == TimeUnit.SECONDS) {
            time = (int) timeUnit.toSeconds(saveTime);
        } else if (timeUnit == TimeUnit.MINUTES) {
            time = (int) timeUnit.toSeconds(saveTime);
        } else if (timeUnit == TimeUnit.HOURS) {
            time = (int) timeUnit.toSeconds(saveTime);
        } else if (timeUnit == TimeUnit.DAYS) {
            time = (int) timeUnit.toSeconds(saveTime);
        }
        return time;
    }

    /**
     * 转换时间至毫秒
     *
     * @param saveTime 时间
     * @param timeUnit 时间单位
     * @return
     */
    public static int toMilliseconds(long saveTime, TimeUnit timeUnit) {
        int time = 0;
        if (timeUnit == TimeUnit.NANOSECONDS) {
            time = (int) timeUnit.toMillis(saveTime);
        } else if (timeUnit == TimeUnit.MICROSECONDS) {
            time = (int) timeUnit.toMillis(saveTime);
        } else if (timeUnit == TimeUnit.MILLISECONDS) {
            time = (int) timeUnit.toMillis(saveTime);
        } else if (timeUnit == TimeUnit.SECONDS) {
            time = (int) timeUnit.toMillis(saveTime);
        } else if (timeUnit == TimeUnit.MINUTES) {
            time = (int) timeUnit.toMillis(saveTime);
        } else if (timeUnit == TimeUnit.HOURS) {
            time = (int) timeUnit.toMillis(saveTime);
        } else if (timeUnit == TimeUnit.DAYS) {
            time = (int) timeUnit.toMillis(saveTime);
        }
        return time;
    }

    public static String toDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);
        return returnString;
    }
}
