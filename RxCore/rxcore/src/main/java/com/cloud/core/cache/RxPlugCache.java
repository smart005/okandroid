package com.cloud.core.cache;

import android.text.TextUtils;

import com.cloud.core.db.DbManager;
import com.cloud.core.db.DbUtils;
import com.cloud.core.db.Selector;
import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class RxPlugCache {

    private static void setBaseCacheData(String plugId, String plugPath, String plugVersion, boolean isInstall) {
        try {
            if (TextUtils.isEmpty(plugId) || TextUtils.isEmpty(plugPath)) {
                return;
            }
            PlugDataItem dataItem = new PlugDataItem();
            dataItem.setPlugId(plugId);
            dataItem.setPlugPath(plugPath);
            dataItem.setPlugVersion(plugVersion);
            dataItem.setIsInstall(isInstall);
            DbUtils.getInstance().getDbManager().addOrUpdate(dataItem);
        } catch (Exception e) {
            Logger.L.error("save cache error:", e);
        }
    }

    private static void setBaseCacheData(String plugId, boolean isInstall) {
        try {
            if (TextUtils.isEmpty(plugId)) {
                return;
            }
            PlugDataItem dataItem = new PlugDataItem();
            DbManager dbManager = DbUtils.getInstance().getDbManager();
            PlugDataItem first = dbManager.selector(PlugDataItem.class).
                    where(dataItem.getForKey(dataItem.getPlugId()), "=", plugId).
                    findFirst();
            if (first == null) {
                return;
            }
            first.setIsInstall(isInstall);
            dbManager.addOrUpdate(first);
        } catch (Exception e) {
            Logger.L.error("save cache error:", e);
        }
    }

    private static PlugDataItem getBaseCacheData(String plugId) {
        try {
            if (TextUtils.isEmpty(plugId)) {
                return null;
            }
            Selector<PlugDataItem> selector = DbUtils.getInstance().getDbManager().selector(PlugDataItem.class);
            PlugDataItem dataItem = selector.getEntity();
            PlugDataItem first = selector.where(dataItem.getForKey(dataItem.getPlugId()), "=", plugId).findFirst();
            if (first == null) {
                return dataItem;
            }
            return first;
        } catch (Exception e) {
            Logger.L.error("get cache data error:", e);
        }
        return null;
    }

    public static void setCachePlugInstallState(String plugId, boolean isInstall) {
        setBaseCacheData(plugId, isInstall);
    }

    public static void setCachePlugPath(String plugId, String plugPath, String plugVersion) {
        setBaseCacheData(plugId, plugPath, plugVersion, true);
    }

    public static PlugDataItem getCachePlugInfo(String plugId) {
        PlugDataItem dataItem = getBaseCacheData(plugId);
        if (dataItem == null ||
                TextUtils.isEmpty(dataItem.getPlugId()) ||
                TextUtils.isEmpty(dataItem.getPlugPath()) ||
                !dataItem.getIsInstall()) {
            return null;
        }
        return dataItem;
    }
}
