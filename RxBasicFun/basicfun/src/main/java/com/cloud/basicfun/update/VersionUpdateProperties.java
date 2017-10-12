package com.cloud.basicfun.update;

import android.app.Activity;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/6
 * @Description:版本更新属性
 * @Modifier:
 * @ModifyContent:
 */
public class VersionUpdateProperties {
    /**
     * activity
     */
    private Activity activity = null;
    /**
     * 是否自动更新
     */
    private boolean isAutoUpdate = false;
    /**
     * 是否检测更新提醒(即更新之前是否最新版或正在更新字样提示)
     */
    private boolean isCheckUpdatePrompt = false;
    /**
     * 应用图标
     */
    private int appIcon = 0;
    /**
     * 适用于远程存放xml配置文件
     */
    private String checkUpdateUrl = "";

    /**
     * 获取activity
     */
    public Activity getActivity() {
        if (activity == null) {
            activity = new Activity();
        }
        return activity;
    }

    /**
     * 设置activity
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取是否自动更新
     */
    public boolean getIsAutoUpdate() {
        return isAutoUpdate;
    }

    /**
     * 设置是否自动更新
     *
     * @param isAutoUpdate
     */
    public void setIsAutoUpdate(boolean isAutoUpdate) {
        this.isAutoUpdate = isAutoUpdate;
    }

    /**
     * 获取是否检测更新提醒(即更新之前是否最新版或正在更新字样提示)
     */
    public boolean getIsCheckUpdatePrompt() {
        return isCheckUpdatePrompt;
    }

    /**
     * 设置是否检测更新提醒(即更新之前是否最新版或正在更新字样提示)
     *
     * @param isCheckUpdatePrompt
     */
    public void setIsCheckUpdatePrompt(boolean isCheckUpdatePrompt) {
        this.isCheckUpdatePrompt = isCheckUpdatePrompt;
    }

    /**
     * 获取应用图标
     */
    public int getAppIcon() {
        return appIcon;
    }

    /**
     * 设置应用图标
     *
     * @param appIcon
     */
    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * 获取适用于远程存放xml配置文件
     */
    public String getCheckUpdateUrl() {
        if (checkUpdateUrl == null) {
            checkUpdateUrl = "";
        }
        return checkUpdateUrl;
    }

    /**
     * 设置适用于远程存放xml配置文件
     *
     * @param checkUpdateUrl
     */
    public void setCheckUpdateUrl(String checkUpdateUrl) {
        this.checkUpdateUrl = checkUpdateUrl;
    }
}
