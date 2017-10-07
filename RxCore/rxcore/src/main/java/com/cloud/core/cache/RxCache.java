package com.cloud.core.cache;

import android.text.TextUtils;

import com.cloud.core.db.DbUtils;
import com.cloud.core.db.Selector;
import com.cloud.core.db.sqlite.WhereBuilder;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/10/14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class RxCache {

    private static HashMap<String, Object> redisCacheList = new HashMap<String, Object>();

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     * @param saveTime 缓存时间
     * @param timeUnit 时间单位
     */
    private static <T> void setBaseCacheData(String cacheKey, T value, long saveTime, TimeUnit timeUnit) {
        try {
            if (TextUtils.isEmpty(cacheKey) || value == null) {
                return;
            }
            CacheDataItem dataItem = new CacheDataItem();
            if (saveTime > 0 && timeUnit != null) {
                int time = ConvertUtils.toMilliseconds(saveTime, timeUnit);
                if (time > 0) {
                    long mtime = System.currentTimeMillis() + time;
                    dataItem.setKey(cacheKey);
                    setCacheValue(value, dataItem);
                    dataItem.setEffective(mtime);
                } else {
                    dataItem.setKey(cacheKey);
                    setCacheValue(value, dataItem);
                }
            } else {
                dataItem.setKey(cacheKey);
                setCacheValue(value, dataItem);
            }
            DbUtils.getInstance().getDbManager().addOrUpdate(dataItem);
        } catch (Exception e) {
            Logger.L.error("save cache error:", e);
        }
    }

    private static <T> void setCacheValue(T value, CacheDataItem dataItem) {
        if (value instanceof String) {
            dataItem.setValue(valueOf(value));
        } else if (value instanceof Boolean) {
            dataItem.setFlag((Boolean) value);
        } else if (value instanceof Integer) {
            dataItem.setIniValue(ConvertUtils.toInt(value));
        } else if (value instanceof Long) {
            dataItem.setLongValue(ConvertUtils.toLong(value));
        }
    }

    /**
     * 获取缓存数据
     *
     * @param cacheKey 缓存键
     * @return
     */
    private static CacheDataItem getBaseCacheData(String cacheKey) {
        try {
            if (TextUtils.isEmpty(cacheKey)) {
                return null;
            }
            Selector<CacheDataItem> selector = DbUtils.getInstance().getDbManager().selector(CacheDataItem.class);
            CacheDataItem dataItem = selector.getEntity();
            CacheDataItem first = selector.where(dataItem.getForKey(dataItem.getKey()), "=", cacheKey).findFirst();
            if (first == null) {
                return dataItem;
            }
            if (first.getEffective() > 0) {
                if (first.getEffective() > System.currentTimeMillis()) {
                    return first;
                } else {
                    clear(cacheKey);
                    return dataItem;
                }
            } else {
                return first;
            }
        } catch (Exception e) {
            Logger.L.error("get cache data error:", e);
        }
        return null;
    }

    /**
     * 获取缓存数据
     *
     * @param cacheKey 缓存键
     * @return
     */
    public static String getCacheData(String cacheKey) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(cacheKey);
                if (dataItem == null) {
                    return "";
                } else {
                    if (dataItem.getEffective() != 0) {
                        redisCacheList.put(cacheKey, dataItem.getValue());
                    }
                    return dataItem.getValue();
                }
            } else {
                String result = String.valueOf(o);
                if (TextUtils.isEmpty(result)) {
                    CacheDataItem dataItem = getBaseCacheData(cacheKey);
                    if (dataItem == null) {
                        return "";
                    } else {
                        if (dataItem.getEffective() != 0) {
                            redisCacheList.put(cacheKey, dataItem.getValue());
                        }
                        return dataItem.getValue();
                    }
                } else {
                    return result;
                }
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(cacheKey);
            if (dataItem == null) {
                return "";
            } else {
                if (dataItem.getEffective() != 0) {
                    redisCacheList.put(cacheKey, dataItem.getValue());
                }
                return dataItem.getValue();
            }
        }
    }

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     * @param saveTime 缓存时间
     * @param timeUnit 时间单位
     */
    public static void setCacheData(String cacheKey, String value, long saveTime, TimeUnit timeUnit) {
        setBaseCacheData(cacheKey, value, saveTime, timeUnit);
    }

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     */
    public static void setCacheData(String cacheKey, String value) {
        setBaseCacheData(cacheKey, value, 0, null);
    }

    /**
     * 清空所有缓存
     */
    public static void clear() {
        try {
            DbUtils.getInstance().getDbManager().delete(CacheDataItem.class);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 清空包含指定键的缓存
     */
    public static void clear(String containsKey) {
        try {
            CacheDataItem dataItem = new CacheDataItem();
            DbUtils.getInstance().getDbManager()
                    .delete(CacheDataItem.class, WhereBuilder.b()
                            .expr(MessageFormat.format("[{0}] like '%{1}%'",
                                    dataItem.getForKey(dataItem.getKey()),
                                    containsKey)));
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 移除指定缓存
     *
     * @param cacheKey 缓存key
     */
    public static void remove(String cacheKey) {
        try {
            CacheDataItem dataItem = new CacheDataItem();
            DbUtils.getInstance().getDbManager()
                    .delete(CacheDataItem.class, WhereBuilder.
                            b(dataItem.getForKey(dataItem.getKey()), "=", cacheKey));
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     * @param saveTime 缓存时间
     * @param timeUnit 时间单位
     */
    public static void setJsonObject(String cacheKey, JSONObject value, long saveTime, TimeUnit timeUnit) {
        if (value == null) {
            return;
        }
        setBaseCacheData(cacheKey, value.toString(), saveTime, timeUnit);
    }

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     */
    public static void setJsonObject(String cacheKey, JSONObject value) {
        if (value == null) {
            return;
        }
        setBaseCacheData(cacheKey, value.toString(), 0, null);
    }

    /**
     * 获取缓存数据
     *
     * @param cacheKey 缓存键
     * @return
     */
    public static JSONObject getJsonObject(String cacheKey) {
        try {
            CacheDataItem dataItem = getBaseCacheData(cacheKey);
            return dataItem == null ? new JSONObject() : new JSONObject(dataItem.getValue());
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     * @param saveTime 缓存时间
     * @param timeUnit 时间单位
     */
    public static void setJsonArray(String cacheKey, JSONArray value, long saveTime, TimeUnit timeUnit) {
        if (value == null) {
            return;
        }
        setBaseCacheData(cacheKey, value.toString(), saveTime, timeUnit);
    }

    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     */
    public static void setJsonArray(String cacheKey, JSONArray value) {
        setJsonArray(cacheKey, value, 0, null);
    }

    /**
     * 获取缓存数据
     *
     * @param cacheKey 缓存键
     * @return
     */
    public static JSONArray getJsonArray(String cacheKey) {
        try {
            CacheDataItem dataItem = getBaseCacheData(cacheKey);
            return dataItem == null ? new JSONArray() : new JSONArray(dataItem.getValue());
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static <T> void setCacheObject(String cacheKey, T data, long saveTime, TimeUnit timeUnit) {
        if (data == null) {
            return;
        }
        String value = JsonUtils.toStr(data);
        setBaseCacheData(cacheKey, value, saveTime, timeUnit);
    }

    public static <T> T getCacheObject(String cacheKey, Class<T> dataClass) {
        CacheDataItem dataItem = getBaseCacheData(cacheKey);
        return dataItem == null ? JsonUtils.newNull(dataClass) : JsonUtils.parseT(dataItem.getValue(), dataClass);
    }

    public static <T> List<T> getCacheList(String cacheKey, Class<T> dataClass) {
        CacheDataItem dataItem = getBaseCacheData(cacheKey);
        return dataItem == null ? new ArrayList<T>() : JsonUtils.parseArray(dataItem.getValue(), dataClass);
    }

    public static void setCacheFlag(String cacheKey, boolean flag) {
        setBaseCacheData(cacheKey, flag, 0, null);
    }

    public static boolean getCacheFlag(String cacheKey, boolean defaultValue) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(cacheKey);
                boolean value = (dataItem == null ? defaultValue : dataItem.getFlag());
                redisCacheList.put(cacheKey, value);
                return value;
            } else {
                return (boolean) o;
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(cacheKey);
            return dataItem == null ? defaultValue : dataItem.getFlag();
        }
    }

    public static boolean getCacheFlag(String cacheKey) {
        return getCacheFlag(cacheKey, false);
    }

    public static void setCacheInt(String cacheKey, int value) {
        setBaseCacheData(cacheKey, value, 0, null);
    }

    public static int getCacheInt(String cacheKey) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(cacheKey);
                int value = dataItem == null ? 0 : dataItem.getIniValue();
                redisCacheList.put(cacheKey, value);
                return value;
            } else {
                return ConvertUtils.toInt(o);
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(cacheKey);
            int value = dataItem == null ? 0 : dataItem.getIniValue();
            redisCacheList.put(cacheKey, value);
            return value;
        }
    }

    public static void setCacheLong(String cacheKey, long value) {
        setBaseCacheData(cacheKey, value, 0, null);
    }

    public static long getCacheLong(String cacheKey) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(cacheKey);
                long value = dataItem == null ? 0 : dataItem.getLongValue();
                redisCacheList.put(cacheKey, value);
                return value;
            } else {
                return ConvertUtils.toLong(o);
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(cacheKey);
            long value = dataItem == null ? 0 : dataItem.getLongValue();
            redisCacheList.put(cacheKey, value);
            return value;
        }
    }
}
