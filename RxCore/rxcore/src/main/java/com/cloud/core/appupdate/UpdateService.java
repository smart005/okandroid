package com.cloud.core.appupdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.cloud.core.SAXSerializable;
import com.cloud.core.beans.ApkInfo;
import com.cloud.core.beans.BaseDialogRes;
import com.cloud.core.beans.DeviceInfo;
import com.cloud.core.beans.InstanceUpdateServiceInfoEntity;
import com.cloud.core.beans.NoticeDownloadViewEntity;
import com.cloud.core.beans.VersionUpdateInfo;
import com.cloud.core.enums.ApkDownloadType;
import com.cloud.core.enums.ResponseType;
import com.cloud.core.loadings.MaskLoading;
import com.cloud.core.logger.Logger;
import com.cloud.core.okgo.OkgoUtils;
import com.cloud.core.okgo.callBack.JrFileCallback;
import com.cloud.core.utils.AppInfoUtils;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.NetworkUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.core.utils.ToastUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class UpdateService {

    private DownloadUtil ddutil = new DownloadUtil();

    private final int INIT_LOAD_VIEW = 1;

    private final int NO_NETWORK_TIP = 2;

    private final int DISMISS_LOADING_VIEW = 3;

    private final int DOWNLOAD_FINISH = 5;

    private final int DOWNLOAD_APK_ADDRESS_ERROR_TIP_WHAT = 1840102850;

    private final int DOWNLOAD_APK_ERROR_TIP_WHAT = 1992169680;
    private final int UPLOAD_PROGRESS_WHAT = 1605407770;

    // %s更新
    private String SUBJECT_TEXT = "%s-\u66F4\u65B0";
    // 下载APK地址异常,更新失败.
    private String DOWNLOAD_APK_ADRESS_ERROR = "\u4E0B\u8F7D\u0041\u0050\u004B\u5730\u5740\u5F02\u5E38\u002C\u66F4\u65B0\u5931\u8D25\u002E";
    // 下载apk异常,请稍候再试.
    private String DOWNLOAD_APK_ERROR_PLEASE_LATER_UPDATE = "\u4E0B\u8F7D\u0061\u0070\u006B\u5F02\u5E38\u002C\u8BF7\u7A0D\u5019\u518D\u8BD5\u002E";

    private Message msgh;

    private MaskLoading mloading;

    private ProgressBar updatepbar;

    private int ddapktextresid;

    /**
     * 是否取消apk下载
     */
    private boolean cancelUpdate;

    /**
     * 版本信息请求键
     */
    private String VERSION_INFO_REQUEST_KEY = "15882";

    private InstanceUpdateServiceInfoEntity miusinfo = new InstanceUpdateServiceInfoEntity();

    private int NOTIFICATION_ID = 1385659997;

    private NotificationManager noticeManager = null;
    private RemoteViews mrview = null;
    private Notification notification = new Notification();
    private int currprogress = 0;
    //    private AsyncHttpClientOverride httpclient = null;
    private OnVersionUpdateListener mvulistener = null;

    /**
     * @param mvulistener 设置mvulistener
     */
    public void setMvulistener(OnVersionUpdateListener mvulistener) {
        this.mvulistener = mvulistener;
    }

    private Handler _handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case INIT_LOAD_VIEW:
                        if (mvulistener != null) {
                            mvulistener.onBeginLoadingPromptListener(miusinfo
                                    .getCheckUpdatePromptText());
                        }
                        break;
                    case NO_NETWORK_TIP:
                        ToastUtils.showLong(miusinfo.getContext(),
                                miusinfo.getNoNetworkPromptText());
                        break;
                    case DISMISS_LOADING_VIEW:
                        if (mvulistener != null) {
                            mvulistener.onEndLoadingPromptListener();
                        }
                        break;
                    case DOWNLOAD_FINISH:
                        if (miusinfo.getDownloadType() == ApkDownloadType.WINDOW) {
                            if (ddapkdg.isShowing()) {
                                ddapkdg.dismiss();
                            }
                            installApk();
                        } else if (miusinfo.getDownloadType() == ApkDownloadType.NOTIFICATION) {
                            NoticeDownloadViewEntity ndventity = miusinfo
                                    .getNoticeDownloadViewEntity();
                            setDownloadProgress(100, ndventity);
                        }
                        didUpdaetComplate();
                        break;
                    case DOWNLOAD_APK_ERROR_TIP_WHAT:
                        ToastUtils.showLong(miusinfo.getContext(), DOWNLOAD_APK_ERROR_PLEASE_LATER_UPDATE);
                        break;
                    case DOWNLOAD_APK_ADDRESS_ERROR_TIP_WHAT:
                        ToastUtils.showLong(miusinfo.getContext(), DOWNLOAD_APK_ADRESS_ERROR);
                        break;
                    case UPLOAD_PROGRESS_WHAT:
                        if (updatepbar != null) {
                            updatepbar.setProgress(msg.arg1);
                        }
                        TextView tv = (TextView) ddapkdg.findViewById(ddapktextresid);
                        String distxt = String.format("%s(%s", miusinfo.getApkInfo().getApkName(), msg.arg1) + "%)";
                        if (tv != null) {
                            tv.setText(distxt);
                        }
                        break;
                }
            } catch (Exception e) {
                didUpdaetComplate();
                Logger.L.error("update application service error:", e);
            }
        }
    };

    public void onCheckAppUpdate(InstanceUpdateServiceInfoEntity miusinfo) {
        try {
            if (mvulistener != null) {
                mloading = mvulistener.onInitLoadingObject();
            }
            this.miusinfo = miusinfo;
            if (miusinfo.isAutoUpdate()) {
                if (NetworkUtils.isConnected(miusinfo.getContext())) {
                    locaUpdateCheck();
                } else {
                    msgh = _handler.obtainMessage();
                    msgh.what = NO_NETWORK_TIP;
                    _handler.sendMessage(msgh);
                }
            } else {
                if (NetworkUtils.isConnected(miusinfo.getContext())) {
                    if (miusinfo.isDisplayCheckUpdatePrompt()) {
                        msgh = _handler.obtainMessage();
                        msgh.what = INIT_LOAD_VIEW;
                        msgh.obj = miusinfo.getUpdateInfoUrl();
                        _handler.sendMessage(msgh);
                    }
                    msgh = _handler.obtainMessage();
                    msgh.what = DISMISS_LOADING_VIEW;
                    _handler.sendMessage(msgh);
                    locaUpdateCheck();
                } else {
                    msgh = _handler.obtainMessage();
                    msgh.what = NO_NETWORK_TIP;
                    _handler.sendMessage(msgh);
                }
            }
        } catch (Exception e) {
            msgh = _handler.obtainMessage();
            msgh.what = DISMISS_LOADING_VIEW;
            _handler.sendMessage(msgh);
            didUpdaetComplate();
            Logger.L.error("update exception:", e);
        }
    }

    private void locaUpdateCheck() {
        if (miusinfo.getApkInfo().getVersionCode() > miusinfo.getVersionCode()) {
            vinfodg.setMiusinfo(miusinfo);
            vinfodg.setDialogRes(getDialogRes());
            vinfodg.setTitle(String.format(SUBJECT_TEXT, miusinfo.getApkInfo().getApkName()));
            vinfodg.setAppInfo(miusinfo.getApkInfo());
            double minvcode = miusinfo.getApkInfo().getMinVersionCode();
            if (miusinfo.getApkInfo().isEnablePart()) {
                if (TextUtils.isEmpty(miusinfo.getApkInfo().getUpdateUsers())) {
                    didUpdaetComplate();
                    return;
                }
                DeviceInfo mdinfo = AppInfoUtils.getDeviceInfo(miusinfo
                        .getContext());
                String[] uuas = miusinfo.getApkInfo().getUpdateUsers().split(",");
                List<String> uulst = Arrays.asList(uuas);
                boolean flag = uulst.contains(mdinfo.getImei());
                if (flag) {
                    if (miusinfo.getApkInfo().getUpdateType() == 1 || miusinfo.getVersionCode() < minvcode) {
                        vinfodg.setCompulsoryUpdate(true);
                    } else {
                        vinfodg.setCompulsoryUpdate(false);
                    }
                } else {
                    didUpdaetComplate();
                    Logger.L.info("don't need to update");
                    return;
                }
            } else {
                if (miusinfo.getApkInfo().getUpdateType() == 1 || miusinfo.getVersionCode() < minvcode) {
                    vinfodg.setCompulsoryUpdate(true);
                } else {
                    vinfodg.setCompulsoryUpdate(false);
                }
            }
            // 检测到版本更新发送广播
            if (mvulistener != null) {
                mvulistener.versionUpdate(miusinfo.getApkInfo(),
                        vinfodg.getIsCompulsoryUpdate());
            }
            if (vinfodg.getIsCompulsoryUpdate()) {
                vinfodg.show(miusinfo.getContext());
            } else {
                if (miusinfo.isDisplayUpdateRemindForAutoUpdate()) {
                    vinfodg.show(miusinfo.getContext());
                }
            }
        } else {
            if (mvulistener != null) {
                mvulistener.lasterVersion();
            }
            if (!miusinfo.isAutoUpdate()) {
                ToastUtils.showLong(miusinfo.getContext(),
                        miusinfo.getLastVersionPromptText());
            }
        }
    }

    private void remoteUpdateCheck() {
        if (miusinfo.getApkInfo().getVersionCode() == 0) {
            if (mvulistener != null) {
                mvulistener.lasterVersion();
            }
            if (!miusinfo.isAutoUpdate()) {
                ToastUtils.showLong(miusinfo.getContext(),
                        miusinfo.getLastVersionPromptText());
            }
        } else {
            vinfodg.setMiusinfo(miusinfo);
            vinfodg.setDialogRes(getDialogRes());
            vinfodg.setTitle(String.format(SUBJECT_TEXT, miusinfo.getApkInfo().getApkName()));
            vinfodg.setAppInfo(miusinfo.getApkInfo());
            if (miusinfo.getApkInfo().getUpdateType() == 0) {
                vinfodg.setCompulsoryUpdate(false);
            } else if (miusinfo.getApkInfo().getUpdateType() == 1 ||
                    miusinfo.getVersionCode() < miusinfo.getApkInfo().getMinVersionCode()) {
                vinfodg.setCompulsoryUpdate(true);
            }
            if (mvulistener != null) {
                mvulistener.versionUpdate(miusinfo.getApkInfo(),
                        vinfodg.getIsCompulsoryUpdate());
            }
            if (miusinfo.isAutoUpdate()) {
                if (miusinfo.isDisplayUpdateRemindForAutoUpdate()) {
                    vinfodg.show(miusinfo.getContext());
                }
            } else {
                vinfodg.show(miusinfo.getContext());
            }
        }
    }

    private void completeVersionInfoRequest(String result) {
        if (!miusinfo.isAutoUpdate()) {
            msgh = _handler.obtainMessage();
            msgh.what = DISMISS_LOADING_VIEW;
            _handler.sendMessage(msgh);
        }
        if (!TextUtils.isEmpty(result)) {
            String[] notnodes = {"Root"};
            SAXSerializable serialy = new SAXSerializable(notnodes);
            HashMap<String, String> datalist = serialy.getListFromXmlStr(
                    miusinfo.getContext(), result);
            ApkInfo appinfo = serialy.parseT(datalist, ApkInfo.class);
            if (appinfo != null) {
                checkUpdateApp(appinfo);
            }
        }
    }

    private void didUpdaetComplate() {
        VersionUpdateInfo vuinfo = new VersionUpdateInfo();
        vuinfo.setNoticeMode(NoticeUpdateEnum.UpdateComplete.getValue());
        if (mvulistener != null) {
            mvulistener.updateComplated();
        }
    }

    private void checkUpdateApp(ApkInfo appinfo) {
        if (appinfo.getVersionCode() > miusinfo.getVersionCode()) {
            vinfodg.setMiusinfo(miusinfo);
            vinfodg.setDialogRes(getDialogRes());
            vinfodg.setTitle(String.format(SUBJECT_TEXT, miusinfo.getApkInfo().getApkName()));
            vinfodg.setAppInfo(appinfo);
            double minvcode = appinfo.getMinVersionCode();
            if (appinfo.isEnablePart()) {
                if (TextUtils.isEmpty(appinfo.getUpdateUsers())) {
                    didUpdaetComplate();
                    return;
                }
                DeviceInfo mdinfo = AppInfoUtils.getDeviceInfo(miusinfo
                        .getContext());
                String[] uuas = appinfo.getUpdateUsers().split(",");
                List<String> uulst = Arrays.asList(uuas);
                boolean flag = uulst.contains(mdinfo.getImei());
                if (flag) {
                    if (miusinfo.getApkInfo().getUpdateType() == 1 || miusinfo.getVersionCode() < minvcode) {
                        vinfodg.setCompulsoryUpdate(true);
                    } else {
                        miusinfo.setAutoUpdate(false);
                        vinfodg.setCompulsoryUpdate(false);
                    }
                } else {
                    didUpdaetComplate();
                    Logger.L.info("don't need to update");
                    return;
                }
            } else {
                if (miusinfo.getApkInfo().getUpdateType() == 1 || miusinfo.getVersionCode() < minvcode) {
                    vinfodg.setCompulsoryUpdate(true);
                } else {
                    vinfodg.setCompulsoryUpdate(false);
                }
            }
            // 检测到版本更新发送广播
            if (mvulistener != null) {
                mvulistener.versionUpdate(appinfo,
                        vinfodg.getIsCompulsoryUpdate());
            }
            if (vinfodg.getIsCompulsoryUpdate()) {
                vinfodg.show(miusinfo.getContext());
            } else {
                if (!miusinfo.isAutoUpdate()) {
                    vinfodg.show(miusinfo.getContext());
                }
            }
        } else {
            if (mvulistener != null) {
                mvulistener.lasterVersion();
            }
            if (!miusinfo.isAutoUpdate()) {
                ToastUtils.showLong(miusinfo.getContext(),
                        miusinfo.getLastVersionPromptText());
            }
        }
    }

    private BaseDialogRes getDialogRes() {
        BaseDialogRes dgres = new BaseDialogRes();
        dgres.animation = miusinfo.getMaskLoadingAnimation();
        dgres.dialogbackground = miusinfo.getDialogBackground();
        dgres.buttonbackground = miusinfo.getDialogButtonsBackground();
        dgres.splitlinebackground = miusinfo.getDialogSplitLineBackground();
        dgres.closebuttonbackground = miusinfo.getDialogCloseButtonBackground();
        dgres.buttonTextColor = miusinfo.getDialogButtonTextColor();
        return dgres;
    }

    private DownloadUtil.VersionInfoDialog vinfodg = ddutil.new VersionInfoDialog() {

        @Override
        protected void updateSubmitListener(String actionid, ApkInfo appinfo) {
            if (mvulistener != null) {
                mvulistener.nowUpdateListener(appinfo,
                        vinfodg.getIsCompulsoryUpdate());
            }
            if (NetworkUtils.getConnectedType(miusinfo.getContext()) == ConnectivityManager.TYPE_WIFI) {
                if (checkApkUpdate(appinfo.getApkUrl())) {
                    appDownloadProgress(appinfo);
                }
            } else {
                BaseDialogRes bdr = getDialogRes();
                nowifiupdatetipdg.setMiusinfo(miusinfo);
                nowifiupdatetipdg.setDialogRes(bdr);
                nowifiupdatetipdg.setTitle(String.format(SUBJECT_TEXT, miusinfo.getApkInfo().getApkName()));
                nowifiupdatetipdg.setAppInfo(appinfo);
                nowifiupdatetipdg.show(miusinfo.getContext());
            }
        }

        @Override
        protected void laterListener() {
            if (mvulistener != null) {
                mvulistener.laterUpdateListener();
            }
        }
    };

    // private void sendUpdateReceiver(VersionUpdateInfo vuinfo) {
    // String vuinfojson = JsonUtils.toStr(vuinfo);
    // Intent _intent = new Intent();
    // _intent.setAction(VersionUpdateAction.UPDATE_RECEIVER_ACTION);
    // _intent.putExtra(VersionUpdateAction.RECEIVER_DATA_KEY, vuinfojson);
    // sendBroadcast(_intent);
    // }

    private DownloadUtil.NoWifiUpdateTipDialog nowifiupdatetipdg = ddutil.new NoWifiUpdateTipDialog() {
        @Override
        protected void yesSubmitListener(ApkInfo appinfo) {
            if (checkApkUpdate(appinfo.getApkUrl())) {
                appDownloadProgress(appinfo);
            }
        }

        @Override
        protected void noSubmitListener(ApkInfo appinfo) {
            if (vinfodg.getIsCompulsoryUpdate()) {
                System.exit(0);
            }
        }
    };

    private boolean checkApkUpdate(String url) {
        if (TextUtils.isEmpty(url)) {
            _handler.obtainMessage(DOWNLOAD_APK_ADDRESS_ERROR_TIP_WHAT).sendToTarget();
            return false;
        } else {
            return true;
        }
    }

    private void appDownloadProgress(ApkInfo appinfo) {
        ApkDownloadType updatetype = miusinfo.getDownloadType();
        if (updatetype == ApkDownloadType.WINDOW) {
            ddapkdg.setTitle(String.format(SUBJECT_TEXT, miusinfo.getApkInfo().getApkName()));
            ddapkdg.setMiusinfo(miusinfo);
            ddapkdg.setDialogRes(getDialogRes());
            ddapkdg.setAppInfo(appinfo);
            ddapkdg.setPBarLayoutId(miusinfo.getDialogDownloadProgressLayout());
            ddapkdg.show(miusinfo.getContext());
        } else if (updatetype == ApkDownloadType.NOTIFICATION) {
            showNotification();
            downApkFile(miusinfo.getContext(), miusinfo.getApkInfo());
        }
    }

    private void showNotification() {
        try {
            NoticeDownloadViewEntity ndventity = miusinfo
                    .getNoticeDownloadViewEntity();
            noticeManager = (NotificationManager) miusinfo.getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            noticeManager.cancel(NOTIFICATION_ID);
            notification.icon = ndventity.getIconResid();
            String subject = String.format("\u6b63\u5728\u4e0b\u8f7d\u003a%s",
                    miusinfo.getApkInfo().getApkName());
            notification.tickerText = subject;
            notification.when = System.currentTimeMillis();
            File dir = StorageUtils.getApksDir();
            File apkFile = new File(dir, getApkFileName(true));
            Intent installIntent = GlobalUtils.getInstallIntent(apkFile.getAbsolutePath());
            PendingIntent contentIntent = PendingIntent.getActivity(
                    miusinfo.getContext(), NOTIFICATION_ID, installIntent, 0);
            notification.contentIntent = contentIntent;
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            mrview = new RemoteViews(miusinfo.getApkInfo().getApkPackgeName(),
                    ndventity.getNotificationLayout());
            mrview.setImageViewResource(ndventity.getIcon(),
                    ndventity.getIconResid());
            mrview.setTextViewText(ndventity.getSubjectText(), subject);
            setDownloadProgress(0, ndventity);
        } catch (Exception e) {
            didUpdaetComplate();
            Logger.L.error("display download notification error:", e);
        }
    }

    private void setDownloadProgress(int progress,
                                     NoticeDownloadViewEntity ndventity) {
        mrview.setProgressBar(ndventity.getProgressPBar(), 100, progress, false);
        mrview.setTextViewText(ndventity.getProgressText(), progress + "%");
        notification.contentView = mrview;
        if (progress == 100) {
            String subject = String
                    .format("%s\u0020\u4E0B\u8F7D\u5B8C\u6210\u002C\u8BF7\u70B9\u51FB\u5B89\u88C5",
                            miusinfo.getApkInfo().getApkName());
            notification.tickerText = subject;
            mrview.setTextViewText(ndventity.getSubjectText(), subject);
        }
        noticeManager.notify(NOTIFICATION_ID, notification);
    }

    private DownloadUtil.DownloadAPKDialog ddapkdg = ddutil.new DownloadAPKDialog() {
        @Override
        protected void initListener(ProgressBar pbar, int textresid) {
            updatepbar = pbar;
            ddapktextresid = textresid;
        }

        @Override
        protected void downloadStartListener(ApkInfo appinfo) {
            cancelUpdate = false;
            downApkFile(miusinfo.getContext(), appinfo);
        }

        @Override
        protected void cancelDownloadListener(ApkInfo appinfo) {
            cancelUpdate = true;
            checkUpdateApp(appinfo);
        }
    };

    private void downApkFile(Context context, ApkInfo apkInfo) {
        try {
            ;
            OkgoUtils.getInstance().downloadFile(context,
                    apkInfo.getApkUrl(),
                    getApkFileName(false),
                    StorageUtils.getApksDir().getAbsolutePath(),
                    new JrFileCallback<Object>() {
                        @Override
                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            try {
                                int progressTemp = (int) progress;
                                if (miusinfo.getDownloadType() == ApkDownloadType.WINDOW) {
                                    _handler.obtainMessage(UPLOAD_PROGRESS_WHAT, progressTemp, 0).sendToTarget();
                                } else if (miusinfo.getDownloadType() == ApkDownloadType.NOTIFICATION) {
                                    if (progress > (currprogress + 2)) {
                                        currprogress = progressTemp;
                                        NoticeDownloadViewEntity ndventity = miusinfo
                                                .getNoticeDownloadViewEntity();
                                        setDownloadProgress(progressTemp, ndventity);
                                    }
                                }
                            } catch (Exception e) {
                                didUpdaetComplate();
                                Logger.L.error("UPDATE PROGRESS error:", e);
                            }
                        }

                        @Override
                        public void onSuccess(File file, Response response, Object extras) {
                            cancelUpdate = true;
                            msgh = _handler.obtainMessage();
                            msgh.what = DOWNLOAD_FINISH;
                            _handler.sendMessage(msgh);
                        }

                        @Override
                        public void onError(Response response, Exception e, Object extras) {
                            Logger.L.error("download apk error:", e);
                            if (miusinfo.getDownloadType() == ApkDownloadType.WINDOW) {
                                if (ddapkdg.isShowing()) {
                                    ddapkdg.dismiss();
                                }
                            }
                            _handler.obtainMessage(DOWNLOAD_APK_ERROR_TIP_WHAT).sendToTarget();
                            didUpdaetComplate();
                        }

                        @Override
                        public void onAfter(Object extras) {
                            didUpdaetComplate();
                        }
                    }, ResponseType.Apk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getApkFileName(boolean isInstall) {
        String packagename = miusinfo.getApkInfo().getApkPackgeName();
        if (TextUtils.isEmpty(packagename)) {
            packagename = GlobalUtils.getGuidNoConnect();
        } else {
            packagename = packagename.replace(".", "_");
        }
        String apkname = String.format("%s.apk", packagename);
        File dir = StorageUtils.getApksDir();
        File apkFile = new File(dir, apkname);
        if (!isInstall) {
            if (apkFile.exists()) {
                apkFile.delete();
            }
        }
        return apkname;
    }

    private void installApk() {
        try {
            File dir = StorageUtils.getApksDir();
            File apkFile = new File(dir, getApkFileName(true));
            Intent installIntent = GlobalUtils.getInstallIntent(apkFile.getAbsolutePath());
            miusinfo.getContext().startActivity(installIntent);
        } catch (Exception e) {
            didUpdaetComplate();
            Logger.L.error("install apk error:", e);
        }
    }
}
