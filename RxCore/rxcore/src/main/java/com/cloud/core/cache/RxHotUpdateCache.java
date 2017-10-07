package com.cloud.core.cache;

import android.text.TextUtils;

import com.cloud.core.db.DbUtils;
import com.cloud.core.db.Selector;
import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/14
 * @Description:热更新缓存
 * @Modifier:
 * @ModifyContent:
 */
public class RxHotUpdateCache {

    private static void setBaseCacheData(String packageName,
                                         String channelName,
                                         String updateVersion) {
        try {
            if (TextUtils.isEmpty(packageName) ||
                    TextUtils.isEmpty(channelName) ||
                    TextUtils.isEmpty(updateVersion)) {
                return;
            }
            HotUpdateItem dataItem = new HotUpdateItem();
            dataItem.setPackageName(packageName);
            dataItem.setChannelName(channelName);
            dataItem.setUpdateVersion(updateVersion);
            dataItem.setLastUpdateTime(System.currentTimeMillis());
            DbUtils.getInstance().getDbManager().addOrUpdate(dataItem);
        } catch (Exception e) {
            Logger.L.error("save cache error:", e);
        }
    }

    private static HotUpdateItem getBaseCacheData(String packageName) {
        try {
            if (TextUtils.isEmpty(packageName)) {
                return null;
            }
            Selector<HotUpdateItem> selector = DbUtils.getInstance().getDbManager().selector(HotUpdateItem.class);
            HotUpdateItem dataItem = selector.getEntity();
            HotUpdateItem first = selector.where(dataItem.getForKey(dataItem.getPackageName()), "=", packageName).findFirst();
            if (first == null) {
                return dataItem;
            }
            return first;
        } catch (Exception e) {
            Logger.L.error("get cache data error:", e);
        }
        return null;
    }

    public static void setHotUpdateCache(String packageName,
                                         String channelName,
                                         String updateVersion) {
        setBaseCacheData(packageName, channelName, updateVersion);
    }

    public static HotUpdateItem getHotUpdateInfo(String packageName) {
        HotUpdateItem updateItem = getBaseCacheData(packageName);
        if (updateItem == null ||
                TextUtils.isEmpty(updateItem.getPackageName()) ||
                TextUtils.isEmpty(updateItem.getChannelName()) ||
                TextUtils.isEmpty(updateItem.getUpdateVersion())) {
            return null;
        } else {
            return updateItem;
        }
    }
}
