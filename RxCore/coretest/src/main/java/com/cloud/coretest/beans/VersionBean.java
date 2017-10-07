package com.cloud.coretest.beans;

import com.cloud.core.beans.BaseBean;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/20
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class VersionBean extends BaseBean {

    private String versionName = "";

    private int versionCode = 0;

    private int minVersionCode = 0;

    private int updateType = 0;

    private boolean enablePart = false;

    private String updateUsers = "";

    private String apkName = "";

    private String apkUrl = "";

    private String updateDescription = "";

    private int downloadType = 0;

    private String plugParams = "";

    private String hotUpdateParams = "";

    /**
     *
     */
    public String getVersionName() {
        if (versionName == null) {
            versionName = "";
        }
        return versionName;
    }

    /**
     * @param versionName
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     *
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode
     */
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    /**
     *
     */
    public int getMinVersionCode() {
        return minVersionCode;
    }

    /**
     * @param minVersionCode
     */
    public void setMinVersionCode(int minVersionCode) {
        this.minVersionCode = minVersionCode;
    }

    /**
     *
     */
    public int getUpdateType() {
        return updateType;
    }

    /**
     * @param updateType
     */
    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    /**
     *
     */
    public boolean getEnablePart() {
        return enablePart;
    }

    /**
     * @param enablePart
     */
    public void setEnablePart(boolean enablePart) {
        this.enablePart = enablePart;
    }

    /**
     *
     */
    public String getUpdateUsers() {
        if (updateUsers == null) {
            updateUsers = "";
        }
        return updateUsers;
    }

    /**
     * @param updateUsers
     */
    public void setUpdateUsers(String updateUsers) {
        this.updateUsers = updateUsers;
    }

    /**
     *
     */
    public String getApkName() {
        if (apkName == null) {
            apkName = "";
        }
        return apkName;
    }

    /**
     * @param apkName
     */
    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    /**
     *
     */
    public String getApkUrl() {
        if (apkUrl == null) {
            apkUrl = "";
        }
        return apkUrl;
    }

    /**
     * @param apkUrl
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    /**
     *
     */
    public String getUpdateDescription() {
        if (updateDescription == null) {
            updateDescription = "";
        }
        return updateDescription;
    }

    /**
     * @param updateDescription
     */
    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    /**
     *
     */
    public int getDownloadType() {
        return downloadType;
    }

    /**
     * @param downloadType
     */
    public void setDownloadType(int downloadType) {
        this.downloadType = downloadType;
    }

    /**
     *
     */
    public String getPlugParams() {
        if (plugParams == null) {
            plugParams = "";
        }
        return plugParams;
    }

    /**
     * @param plugParams
     */
    public void setPlugParams(String plugParams) {
        this.plugParams = plugParams;
    }

    /**
     *
     */
    public String getHotUpdateParams() {
        if (hotUpdateParams == null) {
            hotUpdateParams = "";
        }
        return hotUpdateParams;
    }

    /**
     * @param hotUpdateParams
     */
    public void setHotUpdateParams(String hotUpdateParams) {
        this.hotUpdateParams = hotUpdateParams;
    }
}
