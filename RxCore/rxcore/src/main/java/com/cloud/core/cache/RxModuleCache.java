package com.cloud.core.cache;

import android.text.TextUtils;

import com.cloud.core.db.DbUtils;
import com.cloud.core.db.Selector;
import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/11
 * @Description:模块缓存
 * @Modifier:
 * @ModifyContent:
 */
public class RxModuleCache {
    /**
     * 设置缓存数据
     *
     * @param cacheKey 缓存键
     * @param value    缓存数据
     */
    private static <T> void setBaseCacheData(String moduleName, T value) {
        try {
            if (TextUtils.isEmpty(moduleName) || value == null) {
                return;
            }
            ModuleFlagItem dataItem = new ModuleFlagItem();
            dataItem.setModuleName(moduleName);
            if (value instanceof Boolean) {
                dataItem.setFlag((Boolean) value);
            }
            DbUtils.getInstance().getDbManager().addOrUpdate(dataItem);
        } catch (Exception e) {
            Logger.L.error("save cache error:", e);
        }
    }

    /**
     * 获取缓存数据
     *
     * @param cacheKey 缓存键
     * @return
     */
    private static ModuleFlagItem getBaseCacheData(String moduleName) {
        try {
            if (TextUtils.isEmpty(moduleName)) {
                return null;
            }
            Selector<ModuleFlagItem> selector = DbUtils.getInstance().getDbManager().selector(ModuleFlagItem.class);
            ModuleFlagItem dataItem = selector.getEntity();
            ModuleFlagItem first = selector.where(dataItem.getForKey(dataItem.getModuleName()), "=", moduleName).findFirst();
            if (first == null) {
                return dataItem;
            }
            return first;
        } catch (Exception e) {
            Logger.L.error("get cache data error:", e);
        }
        return null;
    }

    public static void setCacheModuleFlag(String moduleName, boolean flag) {
        setBaseCacheData(moduleName, flag);
    }

    public static boolean getCacheModuleFlag(String moduleName) {
        ModuleFlagItem moduleFlagItem = getBaseCacheData(moduleName);
        return moduleFlagItem == null ? false : moduleFlagItem.getFlag();
    }
}
