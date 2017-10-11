package com.cloud.resources;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Color;

import com.cloud.core.ObjectManager;
import com.cloud.core.appupdate.OnVersionUpdateListener;
import com.cloud.core.appupdate.UpdateManager;
import com.cloud.core.beans.ApkInfo;
import com.cloud.core.beans.InstanceUpdateServiceInfoEntity;
import com.cloud.core.beans.NoticeDownloadViewEntity;
import com.cloud.core.enums.ApkDownloadType;
import com.cloud.core.loadings.MaskLoading;
import com.cloud.core.logger.Logger;

/**
 * @Title: AppUpdate.java
 * @Description:
 * @author: lijinghuan
 * @data: 2015年4月30日 下午2:06:37
 */

public class AppUpdate {
    private UpdateManager updatemg = new UpdateManager();
    /**
     * 自动更新时是否显示更新提醒(true表示自动更新时显示,否则不显示)
     */
    private boolean isDisplayUpdateRemindForAutoUpdate = false;
    private ApkInfo apkInfo = new ApkInfo();
    private InstanceUpdateServiceInfoEntity miusinfo = new InstanceUpdateServiceInfoEntity();
    private boolean isInstanced = false;

    protected void onVersionUpdateListener(ApkInfo appinfo,
                                           boolean isCompulsoryUpdate) {

    }

    protected void onLasterVersionListener() {

    }

    protected void onComplated() {

    }

    protected void onLaterUpdate() {

    }

    protected void onNowUpdate(ApkInfo appinfo, boolean isCompulsoryUpdate) {

    }

    protected void onBeginLoadingPrompt(String text) {

    }

    protected void onEndLoadingPrompt() {

    }

    protected MaskLoading onInitLoading(InstanceUpdateServiceInfoEntity miusinfo) {
        return null;
    }

    /**
     * @param 设置自动更新时是否显示更新提醒 (true表示自动更新时显示否则不显示)
     */
    public void setDisplayUpdateRemindForAutoUpdate(
            boolean isDisplayUpdateRemindForAutoUpdate) {
        this.isDisplayUpdateRemindForAutoUpdate = isDisplayUpdateRemindForAutoUpdate;
    }

    /**
     * @param 设置apkInfo
     */
    public void setApkInfo(ApkInfo apkInfo) {
        this.apkInfo = apkInfo;
    }

    public InstanceUpdateServiceInfoEntity getInstanceUSInfo(Context context,
                                                             String checkUpdateUrl, int appicon) {
        if (!isInstanced) {
            isInstanced = true;
            miusinfo.setContext(context);
            miusinfo.setDisplayCheckUpdatePrompt(true);
            miusinfo.setMaskLoadingTheme(R.style.transparent);
            miusinfo.setMaskLoadingBackground(R.drawable.loading_bg);
            miusinfo.setMaskLoadingAnimation(R.drawable.loading_animation);
            miusinfo.setUpdateInfoUrl(checkUpdateUrl);
            miusinfo.setVersionCode(getVersionCode(context));
            miusinfo.setDialogBackground(R.drawable.dialog_background);
            miusinfo.setDialogButtonsBackground(R.drawable.dialog_button_bg);
            miusinfo.setDialogSplitLineBackground(R.color.dialog_split_line_color);
            miusinfo.setDialogCloseButtonBackground(R.drawable.dialog_close_bg);
            miusinfo.setDialogButtonTextColor(Color.WHITE);
            miusinfo.setDialogDownloadProgressLayout(R.layout.download_progress_pbar_view);
            apkInfo.setApkPackgeName(context.getPackageName());
            // 通知栏布局相关视图
            NoticeDownloadViewEntity ndventity = new NoticeDownloadViewEntity();
            ndventity.setNotificationLayout(R.layout.download_notification_layout);
            ndventity.setIcon(R.id.notification_img_icon_iv);
            ndventity.setIconResid(appicon);
            ndventity.setSubjectText(R.id.notification_subject_text_tv);
            ndventity.setProgressText(R.id.notification_progress_text_tv);
            ndventity.setProgressPBar(R.id.notification_progress_pbar);
            miusinfo.setNoticeDownloadViewEntity(ndventity);
            miusinfo.setCheckUpdatePromptText(context
                    .getString(R.string.check_update_prompt_text));
            miusinfo.setNoNetworkPromptText(context
                    .getString(R.string.no_network_prompt_text));
            miusinfo.setLastVersionPromptText(context
                    .getString(R.string.last_version_prompt_text));
        }
        return miusinfo;
    }

    public void start(Context context, ApkDownloadType downloadtype,
                      boolean isautoupdate, boolean isDisplayCheckUpdatePrompt,
                      String checkUpdateUrl, int appicon) {
        getInstanceUSInfo(context, checkUpdateUrl, appicon);
        miusinfo.setApkInfo(apkInfo);
        miusinfo.setAutoUpdate(isautoupdate);
        miusinfo.setDisplayCheckUpdatePrompt(isDisplayCheckUpdatePrompt);
        miusinfo.setDisplayUpdateRemindForAutoUpdate(isDisplayUpdateRemindForAutoUpdate);
        miusinfo.setDownloadType(downloadtype);
        updatemg.setMiusinfo(miusinfo);
        updatemg.setOnVersionUpdate(new OnVersionUpdateListener() {
            @Override
            public void versionUpdate(ApkInfo appinfo,
                                      boolean isCompulsoryUpdate) {
                onVersionUpdateListener(appinfo, isCompulsoryUpdate);
            }

            @Override
            public void lasterVersion() {
                onLasterVersionListener();
            }

            @Override
            public void updateComplated() {
                onComplated();
            }

            @Override
            public void laterUpdateListener() {
                onLaterUpdate();
            }

            @Override
            public void nowUpdateListener(ApkInfo appinfo,
                                          boolean isCompulsoryUpdate) {
                onNowUpdate(appinfo, isCompulsoryUpdate);
            }

            @Override
            public void onBeginLoadingPromptListener(String text) {
                onBeginLoadingPrompt(text);
            }

            @Override
            public void onEndLoadingPromptListener() {
                onEndLoadingPrompt();
            }

            @Override
            public MaskLoading onInitLoadingObject() {
                return onInitLoading(miusinfo);
            }
        });
        updatemg.start();
    }

    public int getVersionCode(Context context) {
        int vcode = -1;
        try {
            PackageInfo pinfo = ObjectManager.getPackageInfo(context);
            if (pinfo != null) {
                vcode = pinfo.versionCode;
            }
        } catch (Exception e) {
            Logger.L.error("get package version code error:", e);
        }
        return vcode;
    }

    public String getVersionName(Context context) {
        String vname = "";
        try {
            PackageInfo pinfo = ObjectManager.getPackageInfo(context);
            if (pinfo != null) {
                vname = pinfo.versionName;
            }
        } catch (Exception e) {
            Logger.L.error("get package version name error:", e);
        }
        return vname;
    }
}
