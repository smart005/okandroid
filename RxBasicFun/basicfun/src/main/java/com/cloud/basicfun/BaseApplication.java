package com.cloud.basicfun;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.cloud.basicfun.enums.ComRequestUrlType;
import com.cloud.basicfun.utils.AnalyticsProcess;
import com.cloud.core.exception.CrashHandler;
import com.cloud.core.logger.Logger;

import java.util.HashMap;

import rx.functions.Func0;
import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/3
 * @Description:Application基本
 * @Modifier:
 * @ModifyContent:
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mbapp = null;
    /**
     * 是否包含新版本
     */
    private boolean hasNewVersion = false;
    private HashMap<String, Boolean> flagList = new HashMap<String, Boolean>();
    private HashMap<String, Object> objList = new HashMap<String, Object>();
    private int updateActivityNamesResId = 0;
    private int netStateActivityNamesResId = 0;

    public void setUpdateActivityNamesResId(int resId) {
        this.updateActivityNamesResId = resId;
    }

    public int getUpdateActivityNamesResId() {
        return this.updateActivityNamesResId;
    }

    public void setNetStateActivityNamesResId(int resId) {
        this.netStateActivityNamesResId = resId;
    }

    public int getNetStateActivityNamesResId() {
        return this.netStateActivityNamesResId;
    }

    /**
     * 应用图标
     */
    private int appIcon = 0;
    /**
     * 项目广播接收Action
     */
    private String projectReceiveAction = "";
    /**
     * 检测更新url(配置文件)
     */
    private String checkUpdateUrl = "";
    private AnalyticsProcess analyticsProcess = new AnalyticsProcess();
    /**
     * 返回图标
     */
    private int returnIcon = 0;
    /**
     * 主题颜色
     */
    private int themeColorResId = 0;

    /**
     * url action
     */
    private Func1<ComRequestUrlType, String> urlAction = null;

    /**
     * token action
     */
    private Func0<String> getTokenAction = null;
    private boolean isBackGround = false;

    protected void onAppSiwtchToBack() {

    }

    protected void onAppSiwtchToFront() {

    }

    /**
     * 获取主题颜色
     */
    public int getThemeColorResId() {
        return themeColorResId;
    }

    /**
     * 设置主题颜色
     */
    public void setThemeColorResId(int themeColorResId) {
        this.themeColorResId = themeColorResId;
    }

    public void setReturnIcon(int icon) {
        this.returnIcon = icon;
    }

    public int getReturnIcon() {
        return this.returnIcon;
    }

    public void setHasNewVersion(boolean hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public boolean isHasNewVersion() {
        return this.hasNewVersion;
    }

    /**
     * 是否包含状态值
     *
     * @param flagKey 根据key获取标识状态是否符合
     * @return
     */
    public boolean hasFlagStatus(String flagKey) {
        if (flagList == null) {
            return false;
        }
        if (flagList.containsKey(flagKey)) {
            return flagList.get(flagKey);
        }
        return false;
    }

    /**
     * 添加状态值
     *
     * @param flagKey
     * @param status  状态
     */
    public void addOrUpdateFlagStatus(String flagKey, boolean status) {
        if (flagList == null) {
            return;
        }
        removeFlagStatus(flagKey);
        flagList.put(flagKey, status);
    }

    /**
     * 移除状态值
     *
     * @param flagKey
     */
    public void removeFlagStatus(String flagKey) {
        if (flagList == null) {
            return;
        }
        if (flagList.containsKey(flagKey)) {
            flagList.remove(flagKey);
        }
    }

    /**
     * 是否包含对象值
     *
     * @param objKey 根据key获取标识状态是否符合
     * @return
     */
    public boolean hasObjectValue(String objKey) {
        if (objList == null) {
            return false;
        }
        if (objList.containsKey(objKey)) {
            return true;
        }
        return false;
    }

    /**
     * 添加对象值
     *
     * @param objKey
     * @param value  对象
     */
    public void addOrUpdateObjectValue(String objKey, Object value) {
        if (objList == null) {
            return;
        }
        removeObjectValue(objKey);
        objList.put(objKey, value);
    }

    public Object getObjectValue(String objKey) {
        if (objList != null && objList.containsKey(objKey)) {
            return objList.get(objKey);
        }
        return null;
    }

    /**
     * 移除对象值
     *
     * @param objKey
     */
    public void removeObjectValue(String objKey) {
        if (objList == null) {
            return;
        }
        if (objList.containsKey(objKey)) {
            objList.remove(objKey);
        }
    }

    /**
     * 获取项目广播接收Action
     */
    public String getProjectReceiveAction() {
        if (projectReceiveAction == null) {
            projectReceiveAction = "";
        }
        return projectReceiveAction;
    }

    /**
     * 设置项目广播接收Action
     *
     * @param projectReceiveAction
     */
    public void setProjectReceiveAction(String projectReceiveAction) {
        this.projectReceiveAction = projectReceiveAction;
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
     * 获取检测更新url(配置文件)
     */
    public String getCheckUpdateUrl() {
        if (checkUpdateUrl == null) {
            checkUpdateUrl = "";
        }
        return checkUpdateUrl;
    }

    /**
     * 设置检测更新url(配置文件)
     *
     * @param checkUpdateUrl
     */
    public void setCheckUpdateUrl(String checkUpdateUrl) {
        this.checkUpdateUrl = checkUpdateUrl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mbapp = this;
            crashHandler.init(this, getPackageName());
            registerActivityLifecycle();
        } catch (Exception e) {
            Logger.L.error("rx application create error:", e);
        }
    }

    private CrashHandler crashHandler = new CrashHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            super.uncaughtException(thread, ex);
        }
    };

    public static BaseApplication getInstance() {
        return mbapp;
    }


    /**
     * 获取url
     * action
     */
    public Func1<ComRequestUrlType, String> getUrlAction() {
        return urlAction;
    }

    /**
     * 设置url
     * action
     *
     * @param urlAction
     */
    public void setUrlAction(Func1<ComRequestUrlType, String> urlAction) {
        this.urlAction = urlAction;
    }

    /**
     * 获取token
     * action
     */
    public Func0<String> getGetTokenAction() {
        return getTokenAction;
    }

    /**
     * 设置token
     * action
     *
     * @param getTokenAction
     */
    public void setGetTokenAction(Func0<String> getTokenAction) {
        this.getTokenAction = getTokenAction;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackGround = true;
            onAppSiwtchToBack();
        }
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackGround) {
                    isBackGround = false;
                    onAppSiwtchToFront();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
