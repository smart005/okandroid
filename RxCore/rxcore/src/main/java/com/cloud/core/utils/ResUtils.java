package com.cloud.core.utils;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Xml;

import com.cloud.core.beans.Argb;
import com.cloud.core.logger.Logger;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/8/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class ResUtils {

    public static String getAssetsToString(Context context, String fileName) {
        String result = "";
        try {
            if (context != null && !TextUtils.isEmpty(fileName)) {
                InputStream is = context.getAssets().open(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString();
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return result;
    }

    public static String getAssetsInputStream(Context context, String fileName, String nodeName) {
        return getAssetsInputStream(context, fileName, nodeName, "");
    }

    public static String getAssetsInputStream(Context context, String fileName, String nodeName, String attribute) {
        String result = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            getXmlNodeAttributeValue(context, is, nodeName, attribute);
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return result;
    }

    public static String getXmlNodeValue(Context context, InputStream is, String nodeName) {
        return getXmlNodeAttributeValue(context, is, nodeName, "");
    }

    public static String getXmlNodeAttributeValue(Context context, InputStream is, String nodeName, String attribute) {
        String result = "";
        try {
            if (context != null && is != null && !TextUtils.isEmpty(nodeName)) {
                // 获得pull解析器对象
                XmlPullParser parser = Xml.newPullParser();
                // 指定解析的文件和编码格式
                parser.setInput(is, "utf-8");
                int eventType = parser.getEventType(); // 获得事件类型

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagNameString = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (TextUtils.equals(tagNameString, nodeName)) {
                                if (TextUtils.isEmpty(attribute)) {
                                    result = parser.nextText();
                                } else {
                                    result = parser.getAttributeValue(null, attribute);
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:

                            break;
                    }
                    eventType = parser.next();//重新赋值，不然会死循环
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return result;
    }

    public static List<String> getStringList(Context context, int arrayId) {
        List<String> list = null;
        try {
            if (context != null && arrayId > 0) {
                String[] stringArray = context.getResources().getStringArray(arrayId);
                if (stringArray != null && stringArray.length > 0) {
                    list = new ArrayList<>();
                    for (String s : stringArray) {
                        list.add(s);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return list;
    }

    public static Map<String, String> getStringArray(Context context, int arrayId) {
        HashMap<String, String> map = null;
        try {
            if (context != null && arrayId > 0) {
                String[] stringArray = context.getResources().getStringArray(arrayId);
                String resourceName = ResUtils.getResourceName(context, arrayId);
                int valueId = ResUtils.getResource(context, resourceName + "_value", ResFolderType.Array);
                String[] stringArrayValue = context.getResources().getStringArray(valueId);
                if (stringArray != null && stringArrayValue != null && stringArray.length == stringArrayValue.length) {
                    map = new HashMap<>();
                    for (int i = 0; i < stringArray.length; i++) {
                        String key = stringArray[0];
                        String value = stringArrayValue[0];
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return map;
    }

    public static List<Integer> getIntList(Context context, int arrayId) {
        List<Integer> list = null;
        try {
            if (context != null && arrayId > 0) {
                int[] intArray = context.getResources().getIntArray(arrayId);
                if (intArray != null && intArray.length > 0) {
                    list = new ArrayList<>();
                    for (int i : intArray) {
                        list.add(i);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return list;
    }

    public static Map<String, Integer> getIntArray(Context context, int arrayId, int arrayValueId) {
        HashMap<String, Integer> map = null;
        try {
            if (context != null && arrayId > 0) {
                String[] stringArray = context.getResources().getStringArray(arrayId);
                String resourceName = ResUtils.getResourceName(context, arrayId);
                int valueId = ResUtils.getResource(context, resourceName + "_value", ResFolderType.Array);
                int[] intArrayValue = context.getResources().getIntArray(valueId);
                if (stringArray != null && intArrayValue != null && stringArray.length == intArrayValue.length) {
                    map = new HashMap<>();
                    for (int i = 0; i < stringArray.length; i++) {
                        String key = stringArray[0];
                        int value = intArrayValue[0];
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return map;
    }

    public static int getColor(Context context, int colorId) {
        int color = 0;
        try {
            if (context != null && colorId > 0) {
                int version = Build.VERSION.SDK_INT;
                if (version >= 23) {
                    color = context.getColor(colorId);
                } else {
                    color = context.getResources().getColor(colorId);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return color;
    }

    public static Argb getColorRgba(Context context, int colorId) {
        Argb argb = new Argb();
        try {
            if (context != null && colorId > 0) {
                int color = 0;
                int version = Build.VERSION.SDK_INT;
                if (version >= 23) {
                    color = context.getColor(colorId);
                } else {
                    color = context.getResources().getColor(colorId);
                }
                if (color > 0) {
                    argb.a = Color.alpha(color);
                    argb.b = Color.blue(color);
                    argb.g = Color.green(color);
                    argb.r = Color.red(color);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return argb;
    }

    public static String getString(Context context, int stringId) {
        String result = "";
        try {
            if (context != null && stringId > 0) {
                result = context.getResources().getString(stringId);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return result;
    }

    public static float getDimen(Context context, int dimenId) {
        float result = 0F;
        try {
            if (context != null && dimenId > 0) {
                result = context.getResources().getDimension(dimenId);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return result;
    }

    public static int getDimenInt(Context context, int dimenId) {
        int result = 0;
        try {
            if (context != null && dimenId > 0) {
                result = (int) context.getResources().getDimension(dimenId);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return result;
    }

    public static Drawable getDrawable(Context context, int drawableId) {
        Drawable drawable = null;
        try {
            if (context != null && drawableId > 0) {
                drawable = ContextCompat.getDrawable(context, drawableId);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return drawable;
    }

    public static Drawable getMipmap(Context context, int mipmapId) {
        Drawable drawable = null;
        try {
            if (context != null && mipmapId > 0) {
                drawable = ContextCompat.getDrawable(context, mipmapId);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return drawable;
    }

    public static int getResource(Context context, String fileName, ResFolderType resFolderType) {
        int resId = context.getResources().getIdentifier(fileName, resFolderType.getValue(), context.getPackageName());
        return resId;
    }

    public static String getResourceName(Context context, int resId) {
        String resourceName = context.getResources().getResourceName(resId);
        return resourceName;
    }

    private enum ResFolderType {
        String("strings"),
        Array("arrays"),
        Color("colors"),
        Dimen("dimens"),
        Drawable("drawable"),
        Mipmap("mipmap");
        private String value = "";

        ResFolderType(String value) {
            this.value = value;
        }

        public java.lang.String getValue() {
            return value;
        }
    }
}
