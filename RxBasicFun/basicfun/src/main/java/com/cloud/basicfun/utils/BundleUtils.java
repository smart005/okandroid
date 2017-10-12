package com.cloud.basicfun.utils;

import android.os.Bundle;

import com.cloud.core.ObjectJudge;
import com.cloud.core.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/4/18
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BundleUtils {


    public static String getJson(Bundle bundle) {
        if (bundle == null) return null;
        JSONObject jsonObject = new JSONObject();
        for (String key : bundle.keySet()) {
            Object obj = bundle.get(key);
            try {
                jsonObject.put(key, wrap(bundle.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    private static Object wrap(Object o) {
        if (o == null) {
            return JSONObject.NULL;
        }
        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        if (o.equals(JSONObject.NULL)) {
            return o;
        }
        if (o instanceof Collection) {
            return new JSONArray((Collection) o);
        } else if (o.getClass().isArray()) {
            return toJSONArray(o);
        }
        if (o instanceof Map) {
            return new JSONObject((Map) o);
        }
        if (o instanceof Boolean ||
                o instanceof Byte ||
                o instanceof Character ||
                o instanceof Double ||
                o instanceof Float ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Short ||
                o instanceof String) {
            return o;
        }
        if (o.getClass().getPackage().getName().startsWith("java.")) {
            return o.toString();
        }
        return JSONObject.NULL;
    }

    private static JSONArray toJSONArray(Object array) {
        JSONArray result = new JSONArray();
        if (!array.getClass().isArray()) {
            return result;
        }
        final int length = Array.getLength(array);
        for (int i = 0; i < length; ++i) {
            result.put(wrap(Array.get(array, i)));
        }
        return result;
    }

    public static Bundle jsonStringToBundle(String jsonString) {
        try {
            JSONObject jsonObject = toJsonObject(jsonString);
            return jsonToBundle(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Bundle();
    }

    private static JSONObject toJsonObject(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }

    private static Bundle jsonToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            Object value = jsonObject.get(key);
            if (value instanceof Boolean) {
                bundle.putBoolean(key, ObjectJudge.isTrue(value));
            } else if (value instanceof Byte) {
                bundle.putByte(key, (Byte) value);
            } else if (value instanceof Character) {
                bundle.putChar(key, (Character) value);
            } else if (value instanceof Double) {
                bundle.putDouble(key, (Double) value);
            } else if (value instanceof Float) {
                bundle.putFloat(key, (Float) value);
            } else if (value instanceof Integer) {
                bundle.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                bundle.putLong(key, (Long) value);
            } else if (value instanceof Short) {
                bundle.putShort(key, (Short) value);
            } else if (value instanceof String) {
                bundle.putString(key, (String) value);
            } else {
                bundle.putString(key, JsonUtils.toStr(value));
            }
        }
        return bundle;
    }
}
