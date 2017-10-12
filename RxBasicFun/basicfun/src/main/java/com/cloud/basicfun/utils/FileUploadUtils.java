package com.cloud.basicfun.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.cloud.basicfun.R;
import com.cloud.basicfun.beans.ALiYunOssRole;
import com.cloud.basicfun.oss.OssResultItem;
import com.cloud.basicfun.oss.OssUploadFileItem;
import com.cloud.basicfun.oss.OssUtils;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.NetworkUtils;
import com.cloud.core.utils.PathsUtils;
import com.cloud.core.utils.ToastUtils;
import com.cloud.resources.dialog.BaseMessageBox;
import com.cloud.resources.dialog.LoadingDialog;
import com.cloud.resources.dialog.plugs.DialogPlus;
import com.cloud.resources.enums.MsgBoxClickButtonEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/4/13
 * @Description:文件上传工具类
 * @Modifier:
 * @ModifyContent:
 */
public abstract class FileUploadUtils {

    private LoadingDialog mloading = new LoadingDialog();
    private final int START_SHOW_UPLOAD = 1308754678;
    private final int DISMISS_LOADING = 1311980761;
    private final int SHOW_UPLOADING = 825700957;
    private final int UPLOADING_WITH = 476990333;
    private final String UPLOAD_FILE_TAG = "2021553808";
    private final int UPLOAD_NOT_NETWORK_FLAG = 270292957;
    private List<OssUploadFileItem> fileItems = null;
    private String uploadDirectoryFormat = "";
    private String uploadDisplayText = "";
    private Object extra = null;
    private final String UPLOAD_DIALOG_ID = "1820554346";
    private DialogPlus dialogPlus = null;

    protected abstract Activity getActivity();

    protected abstract String getEndpoint();

    protected abstract String getBucket();

    protected abstract void onUploadSuccess(int position, String relativeUrl, String updateType, Object extra);

    protected void onCompleted() {

    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    //检查上传环境
    private void upload(String fileName,
                        File targetFile,
                        String uploadDisplayText,
                        String token,
                        String uploadTypeFlag,
                        String updateType,
                        int position,
                        Object extra) {
        if (TextUtils.isEmpty(fileName) || targetFile == null) {
            return;
        }
        this.uploadDisplayText = uploadDisplayText;
        FileUploadParam fileUploadParam = new FileUploadParam();
        fileUploadParam.fileName = fileName;
        fileUploadParam.targetFile = targetFile;
        fileUploadParam.token = token;
        fileUploadParam.uploadTypeFlag = uploadTypeFlag;
        fileUploadParam.updateType = updateType;
        fileUploadParam.position = position;
        fileUploadParam.originalFileName = targetFile.getName();
        //非wifi状态提醒
        if (NetworkUtils.getConnectedType(getActivity()) == ConnectivityManager.TYPE_WIFI) {
            uploadFilePrepare(fileUploadParam);
        } else {
            msgbox.setShowTitle(true);
            msgbox.setShowClose(false);
            msgbox.setTitle("网络提醒");
            msgbox.setContentGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            msgbox.setContent("当前非wifi状态;确定要继续上传吗?");
            msgbox.setTarget("WIFI_REMIND_TAG", fileUploadParam);
            msgbox.show(getActivity(), DialogButtonsEnum.ConfirmCancel);
        }
    }

    public void upload(String fileName,
                       File targetFile,
                       String uploadDisplayText,
                       String token,
                       String updateType, int position) {
        upload(fileName, targetFile, uploadDisplayText, token, "NORMAL", updateType, position, extra);
    }

    public void upload(String fileName,
                       File targetFile,
                       String uploadDisplayText,
                       String token,
                       String updateType) {
        upload(fileName, targetFile, uploadDisplayText, token, "NORMAL", updateType, 0, extra);
    }

    //一般的上传
    public void upload(String fileName,
                       File targetFile,
                       String uploadDisplayText,
                       String token) {
        upload(fileName, targetFile, uploadDisplayText, token, "NORMAL", "", 0,extra);
    }

    //断点续传
    public void breakPointUpload(String fileName, File targetFile, String uploadDisplayText, String token, String updateType) {
        upload(fileName, targetFile, uploadDisplayText, token, "BREAKPOINT", updateType, 0, extra);
    }

    public void breakPointUpload(String fileName, File targetFile, String uploadDisplayText, String token) {
        upload(fileName, targetFile, uploadDisplayText, token, "BREAKPOINT", "", 0, extra);
    }

    //追加续传
    public void appendUpload(String fileName, File targetFile, String uploadDisplayText, String token, String updateType) {
        upload(fileName, targetFile, uploadDisplayText, token, "APPEND", updateType, 0, extra);
    }

    public void appendUpload(String fileName, File targetFile, String uploadDisplayText, String token) {
        upload(fileName, targetFile, uploadDisplayText, token, "APPEND", "", 0, extra);
    }

    private class FileUploadParam {
        public String fileName = "";
        public String originalFileName = "";
        public File targetFile = null;
        public String token = "";
        /**
         * 上传方式:NORMAL;BREAKPOINT;APPEND
         */
        public String uploadTypeFlag = "";
        public String updateType = "";
        public int position = 0;

    }

    public void setUploadDirectoryFormat(String uploadDirectory) {
        this.uploadDirectoryFormat = uploadDirectory;
    }

    private BaseMessageBox msgbox = new BaseMessageBox() {
        @Override
        public void onItemClickListener(View v, MsgBoxClickButtonEnum mcbenum, String target, Object extraData) {
            if (TextUtils.equals(target, "WIFI_REMIND_TAG")) {
                if (mcbenum == MsgBoxClickButtonEnum.Confirm) {
                    uploadFilePrepare((FileUploadParam) extraData);
                }
            }
        }
    };

    private void uploadFilePrepare(FileUploadParam fileUploadParam) {
        //设置上传显示文本
        mhandler.obtainMessage(START_SHOW_UPLOAD, uploadDisplayText).sendToTarget();
        HashMap<String, Object> uploadMap = new HashMap<String, Object>();
        uploadMap.put("TARGET", UPLOAD_FILE_TAG);
        uploadMap.put("FILE_NAME", fileUploadParam.fileName);
        uploadMap.put("FILE_PATH", fileUploadParam.targetFile.getAbsoluteFile());
        uploadMap.put("UPLOAD_TYPE_FLAG", fileUploadParam.uploadTypeFlag);
        uploadMap.put("UPLOAD_TYPE", fileUploadParam.updateType);
        uploadMap.put("POSITION", fileUploadParam.position);
        uploadMap.put("ORIGINAL_FILE_NAME",fileUploadParam.originalFileName);
        ossUtils.requestALiYunAssumeRole(getActivity(), fileUploadParam.token, uploadMap);
    }

    private OssUtils ossUtils = new OssUtils() {
        @Override
        protected void onOssUploadProgress(PutObjectRequest request, long currentSize, long totalSize, String uploadTypeFlag, String uploadType, String target) {
            if (TextUtils.equals(target, UPLOAD_FILE_TAG)) {
                try {
                    if (NetworkUtils.isConnected(getActivity())) {
                        Thread.sleep(30);
                        float progress = (currentSize * 100 / totalSize);
                        Bundle bundle = new Bundle();
                        bundle.putFloat("PROGRESS_KEY", progress);
                        Message message = mhandler.obtainMessage(UPLOADING_WITH);
                        message.setData(bundle);
                        mhandler.sendMessage(message);
                    } else {
                        mhandler.obtainMessage(UPLOAD_NOT_NETWORK_FLAG).sendToTarget();
                    }
                } catch (InterruptedException e) {
                    Logger.L.info("progress thread 200 error:", e);
                }
            }
        }

        @Override
        protected void onOssUploadProgress(ResumableUploadRequest request, long currentSize, long totalSize, String uploadTypeFlag, String uploadType, String target) {
            if (TextUtils.equals(target, UPLOAD_FILE_TAG)) {
                try {
                    if (NetworkUtils.isConnected(getActivity())) {
                        Thread.sleep(30);
                        float progress = (currentSize * 100 / totalSize);
                        Bundle bundle = new Bundle();
                        bundle.putFloat("PROGRESS_KEY", progress);
                        Message message = mhandler.obtainMessage(UPLOADING_WITH);
                        message.setData(bundle);
                        mhandler.sendMessage(message);
                    } else {
                        mhandler.obtainMessage(UPLOAD_NOT_NETWORK_FLAG).sendToTarget();
                    }
                } catch (InterruptedException e) {
                    Logger.L.info("progress thread 200 error:", e);
                }
            }
        }

        @Override
        protected void onRequestALiYunAssumeRoleSuccess(ALiYunOssRole aLiYunOssRole, HashMap<String, Object> uploadMap) {
            ossUtils.setContext(getActivity())
                    .setAccessKeyId(aLiYunOssRole.getAccessKeyId())
                    .setAccessKeySecret(aLiYunOssRole.getAccessKeySecret())
                    .setSecurityToken(aLiYunOssRole.getSecurityToken())
                    .setEndPoint(FileUploadUtils.this.getEndpoint())
                    .setBucket(FileUploadUtils.this.getBucket())
                    .build();
            if (uploadMap != null) {
                if (uploadMap.containsKey("TARGET") &&
                        TextUtils.equals(String.valueOf(uploadMap.get("TARGET")), UPLOAD_FILE_TAG)) {
                    if (uploadMap.containsKey("UPLOAD_TYPE_FLAG")) {
                        String typeFlag = String.valueOf(uploadMap.get("UPLOAD_TYPE_FLAG"));
                        if (TextUtils.equals(typeFlag, "NORMAL")) {
                            uploadFilePrepare(uploadMap);
                            //上传文件
                            ossUtils.asyncUpload(uploadDirectoryFormat, fileItems, UPLOAD_FILE_TAG, false, false);
                        } else if (TextUtils.equals(typeFlag, "BREAKPOINT")) {
                            uploadFilePrepare(uploadMap);
                            ossUtils.asyncUpload(uploadDirectoryFormat, fileItems, UPLOAD_FILE_TAG, true, false);
                        } else if (TextUtils.equals(typeFlag, "APPEND")) {
                            uploadFilePrepare(uploadMap);
                            ossUtils.asyncUpload(uploadDirectoryFormat, fileItems, UPLOAD_FILE_TAG, false, true);

                        } else {
                            onCompleted();
                            mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
                        }
                    } else {
                        onCompleted();
                        mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
                    }
                } else {
                    onCompleted();
                    mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
                }
            } else {
                onCompleted();
                mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
            }
        }

        @Override
        protected void onOssUploadSuccess(List<OssResultItem> ossResultItems, String objectKey, String uploadTypeFlag, String uploadType, String target) {
            try {
                if (TextUtils.equals(target, UPLOAD_FILE_TAG)) {
                    if (TextUtils.equals(uploadTypeFlag, "NORMAL")) {
                        for (OssResultItem ossResultItem : ossResultItems) {
                            PutObjectRequest putObjectRequest = ossResultItem.getRequest();
                            int position = 0;
                            Map<String, String> params = putObjectRequest.getCallbackParam();
                            if (params != null && params.containsKey("POSITION")) {
                                position = ConvertUtils.toInt(params.get("POSITION"));
                            }
                            onUploadSuccess(position, ossResultItem.getUrl(), uploadType, extra);
                        }
                    } else if (TextUtils.equals(uploadTypeFlag, "BREAKPOINT")) {
                        for (OssResultItem ossResultItem : ossResultItems) {
                            ResumableUploadRequest resumableUploadRequest = ossResultItem.getResumableUploadRequest();
                            int position = 0;
                            Map<String, String> params = resumableUploadRequest.getCallbackParam();
                            if (params != null && params.containsKey("POSITION")) {
                                position = ConvertUtils.toInt(params.get("POSITION"));
                            }
                            onUploadSuccess(position, ossResultItem.getUrl(), uploadType, extra);
                        }
                    } else if (TextUtils.equals(uploadTypeFlag, "APPEND")) {
                        for (OssResultItem ossResultItem : ossResultItems) {
                            int position = 0;
                            Map<String, String> params = ossResultItem.getRequest().getCallbackParam();
                            if (params != null && params.containsKey("POSITION")) {
                                position = ConvertUtils.toInt(params.get("POSITION"));
                            }
                            onUploadSuccess(position, ossResultItem.getUrl(), uploadType, extra);
                        }
                    }
                }
            } catch (Exception e) {
                Logger.L.error("upload file error:", e);
            } finally {
                onCompleted();
                mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
            }
        }

        @Override
        protected void onRequestALiYunAssumeRoleErrored() {
            onCompleted();
            mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
        }
    };

    private void uploadFilePrepare(HashMap<String, Object> params) {
        if (TextUtils.isEmpty(uploadDirectoryFormat)) {
            return;
        }
        fileItems = new ArrayList<OssUploadFileItem>();
        mhandler.obtainMessage(SHOW_UPLOADING, uploadDisplayText).sendToTarget();
        OssUploadFileItem ossUploadFileItem = new OssUploadFileItem();
        ossUploadFileItem.setFileName(PathsUtils.combine(uploadDirectoryFormat, String.valueOf(params.get("FILE_NAME"))));
        ossUploadFileItem.setFilePath(String.valueOf(params.get("FILE_PATH")));
        Map<String, String> ossparams = new HashMap<String, String>();
        ossparams.put("POSITION", String.valueOf(params.get("POSITION")));
        ossparams.put("UPLOAD_TYPE_FLAG", String.valueOf(params.get("UPLOAD_TYPE_FLAG")));
        ossparams.put("UPLOAD_TYPE", String.valueOf(params.get("UPLOAD_TYPE")));
        ossparams.put("ORIGINAL_FILE_NAME", String.valueOf(params.get("ORIGINAL_FILE_NAME")));
        ossUploadFileItem.setParams(ossparams);
        fileItems.add(ossUploadFileItem);
    }

    private Handler mhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == START_SHOW_UPLOAD) {
                if (dialogPlus == null) {
                    dialogPlus = mloading.buildDialog(getActivity(), UPLOAD_DIALOG_ID, String.valueOf(msg.obj));
                }
                mloading.setRotate(dialogPlus, true);
                mloading.setCurrentProgress(dialogPlus, 0);
                mloading.setMaxProgress(dialogPlus, 100);
                mloading.setContent(dialogPlus, String.valueOf(msg.obj));
                dialogPlus.show();
            } else if (msg.what == SHOW_UPLOADING) {
                if (dialogPlus == null) {
                    dialogPlus = mloading.buildDialog(getActivity(), UPLOAD_DIALOG_ID, String.valueOf(msg.obj));
                }
                mloading.setRotate(dialogPlus, false);
                mloading.setCurrentProgress(dialogPlus, 0);
                mloading.setMaxProgress(dialogPlus, 100);
                mloading.setContent(dialogPlus, String.valueOf(msg.obj));
                dialogPlus.show();
            } else if (msg.what == UPLOADING_WITH) {
                if (dialogPlus != null) {
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        float progress = bundle.getFloat("PROGRESS_KEY", 0);
                        if (progress > 0) {
                            if (dialogPlus.getHolderView() != null) {
                                DonutProgress dpProgress = (DonutProgress) dialogPlus.getHolderView().findViewById(R.id.dp_progress);
                                if (dpProgress != null) {
                                    dpProgress.setProgress(progress);
                                }
                            }
                        }
                    }
                }
            } else if (msg.what == DISMISS_LOADING) {
                if (dialogPlus != null && dialogPlus.isShowing()) {
                    mloading.dismiss(dialogPlus);
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogPlus = null;
                        }
                    }, 500);
                }
            } else if (msg.what == UPLOAD_NOT_NETWORK_FLAG) {
                ToastUtils.showLong(getActivity(), R.string.network_faild_please_check);
            }
        }
    };

    public void showLoading(String text) {
        mhandler.obtainMessage(START_SHOW_UPLOAD, text).sendToTarget();
    }

    public void hideLoading() {
        mhandler.obtainMessage(DISMISS_LOADING).sendToTarget();
    }
}
