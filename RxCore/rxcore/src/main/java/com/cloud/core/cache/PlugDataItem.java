package com.cloud.core.cache;

import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "rx_cache_plug_info")
public class PlugDataItem extends BaseDataItem {

    /**
     * 插件id
     */
    @Column(name = "plugId", isId = true, autoGen = false, isIndex = true)
    private String plugId = "";

    /**
     * 插件路径
     */
    @Column(name = "plugPath")
    private String plugPath = "";

    /**
     * 是否安装(默认需要安装)
     */
    @Column(name = "isInstall")
    private boolean isInstall = true;

    /**
     * 插件版本
     */
    @Column(name = "plugVersion")
    private String plugVersion = "";

    /**
     * 获取插件id
     */
    public String getPlugId() {
        if (plugId == null) {
            plugId = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return plugId;
    }

    /**
     * 设置插件id
     *
     * @param plugName
     */
    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    /**
     * 获取插件路径
     */
    public String getPlugPath() {
        if (plugPath == null) {
            plugPath = "";
        }
        return plugPath;
    }

    /**
     * 设置插件路径
     *
     * @param plugPath
     */
    public void setPlugPath(String plugPath) {
        this.plugPath = plugPath;
    }

    /**
     * 获取是否安装(默认需要安装)
     */
    public boolean getIsInstall() {
        return isInstall;
    }

    /**
     * 设置是否安装(默认需要安装)
     *
     * @param isInstall
     */
    public void setIsInstall(boolean isInstall) {
        this.isInstall = isInstall;
    }

    /**
     * 获取插件版本
     */
    public String getPlugVersion() {
        if (plugVersion == null) {
            plugVersion = "";
        }
        return plugVersion;
    }

    /**
     * 设置插件版本
     *
     * @param plugVersion
     */
    public void setPlugVersion(String plugVersion) {
        this.plugVersion = plugVersion;
    }
}
