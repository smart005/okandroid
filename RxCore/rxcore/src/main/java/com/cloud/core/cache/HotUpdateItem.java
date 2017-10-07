package com.cloud.core.cache;

import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/14
 * @Description:热更新数据项
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "rx_cache_hot_update")
public class HotUpdateItem extends BaseDataItem {

    /**
     * 包名
     */
    @Column(name = "packageName", isId = true, autoGen = false, isIndex = true)
    private String packageName = "";

    /**
     * 渠道名称
     */
    @Column(name = "channelName")
    private String channelName = "";

    /**
     * 更新版本
     */
    @Column(name = "updateVersion")
    private String updateVersion = "";

    /**
     * 上次更新时间
     */
    @Column(name = "lastUpdateTime")
    private long lastUpdateTime = 0;


    /**
     * 获取包名
     */
    public String getPackageName() {
        if (packageName == null) {
            packageName = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return packageName;
    }

    /**
     * 设置包名
     *
     * @param packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 获取渠道名称
     */
    public String getChannelName() {
        if (channelName == null) {
            channelName = "";
        }
        return channelName;
    }

    /**
     * 设置渠道名称
     *
     * @param channelName
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取更新版本
     */
    public String getUpdateVersion() {
        if (updateVersion == null) {
            updateVersion = "";
        }
        return updateVersion;
    }

    /**
     * 设置更新版本
     *
     * @param updateVersion
     */
    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    /**
     * 获取上次更新时间
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 设置上次更新时间
     *
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
