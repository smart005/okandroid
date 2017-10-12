package com.cloud.basicfun.oss;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.AppendObjectRequest;
import com.alibaba.sdk.android.oss.model.AppendObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.beans.ALiYunOssRole;
import com.cloud.basicfun.enums.ComRequestUrlType;
import com.cloud.core.ObjectJudge;
import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.logger.Logger;
import com.cloud.core.okgo.OkgoUtils;
import com.cloud.core.okgo.callBack.JrJsonCallback;
import com.cloud.core.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import rx.functions.Func1;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/2/24
 * @Description: Oss工具类
 * @Modifier:
 * @ModifyContent:
 */

public class OssUtils {

    private Context context;

    private OSS oss;
    private String endpoint = "";
    private String bucket = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String securityToken = "";
    private HashMap<String, List<OssResultItem>> dataobjs = new HashMap<String, List<OssResultItem>>();
    private HashMap<String, String> reqobjs = new HashMap<String, String>();
    private HashMap<String, Object> uploadMap = new HashMap<String, Object>();

    protected void onOssUploadSuccess(List<OssResultItem> ossResultItems, String objectKey, String uploadTypeFlag, String uploadType, String target) {

    }

    protected void onOssUploadProgress(PutObjectRequest request, long currentSize, long totalSize, String uploadTypeFlag, String uploadType, String target) {

    }

    protected void onOssUploadProgress(ResumableUploadRequest request, long currentSize, long totalSize, String uploadTypeFlag, String uploadType, String target) {

    }

    protected void onOssUploadProgress(AppendObjectRequest request, long currentSize, long totalSize, String uploadTypeFlag, String uploadType, String target) {

    }

    protected void onRequestALiYunAssumeRoleSuccess(ALiYunOssRole aLiYunOssRole, HashMap<String, Object> uploadMap) {

    }

    protected void onRequestALiYunAssumeRoleErrored() {

    }

    public OssUtils setContext(Context context) {
        this.context = context;
        return this;
    }

    public OssUtils setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public OssUtils setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }

    public OssUtils setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
        return this;
    }

    public OssUtils setEndPoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public OssUtils setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public OssUtils build() {
        this.context = context;
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken) {
            @Override
            public OSSFederationToken getFederationToken() {
                //可实现自动获取token
                return super.getFederationToken();
            }
        };
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
        return this;
    }

    public OSS getOss() {
        return this.oss;
    }

    /**
     * 上传文件
     *
     * @param uploadDir          上传目录
     * @param ossUploadFileItems 上传数据项
     */
    public void asyncUpload(String uploadDir,
                            List<OssUploadFileItem> ossUploadFileItems,
                            String target,
                            boolean isBreakpointUpload,
                            boolean isAppend) {
        if (ObjectJudge.isNullOrEmpty(ossUploadFileItems)) {
            return;
        }
        if (TextUtils.isEmpty(uploadDir)) {
            uploadDir = String.format("other/%s", DateUtils.getDateTime(DateFormatEnum.YYYYMMNC));
        }
        if (dataobjs.containsKey(target)) {
            dataobjs.remove(target);
        }
        List<OssResultItem> ossResultItems = new ArrayList<OssResultItem>();
        dataobjs.put(target, ossResultItems);
        if (!ObjectJudge.isNullOrEmpty(reqobjs)) {
            List<String> keys = new ArrayList<String>();
            for (Map.Entry<String, String> entry : reqobjs.entrySet()) {
                if (TextUtils.equals(entry.getValue(), target)) {
                    if (!keys.contains(entry.getKey())) {
                        keys.add(entry.getKey());
                    }
                }
            }
            if (!ObjectJudge.isNullOrEmpty(keys)) {
                for (String key : keys) {
                    reqobjs.remove(key);
                }
            }
        }
        for (OssUploadFileItem ossUploadFileItem : ossUploadFileItems) {
            new Thread(new ALUploadRunnable(uploadDir,
                    ossUploadFileItem.getFileName(),
                    ossUploadFileItem.getFilePath(),
                    ossUploadFileItem.getParams(),
                    false,
                    true,
                    isBreakpointUpload,
                    isAppend,
                    target)).start();
        }
    }

    private class ALUploadRunnable implements Runnable {

        private String fileName = "";
        private String filePath = "";
        private Map<String, String> params = null;
        private boolean sync = false;
        private boolean isBatch = false;
        private boolean isBreakpointUpload = false;
        private boolean isAppend = false;
        private String uploadDir = "";
        private String target = "";

        public ALUploadRunnable(String uploadDir,
                                String fileName,
                                String filePath,
                                Map<String, String> params,
                                boolean sync,
                                boolean isBatch,
                                boolean isBreakpointUpload,
                                boolean isAppend,
                                String target) {
            this.uploadDir = uploadDir;
            this.fileName = fileName;
            this.filePath = filePath;
            this.params = params;
            this.sync = sync;
            this.isBatch = isBatch;
            this.isBreakpointUpload = isBreakpointUpload;
            this.isAppend = isAppend;
            this.target = target;
        }

        private int getTaskRequestCount() {
            int count = 0;
            for (Map.Entry<String, String> entry : reqobjs.entrySet()) {
                if (TextUtils.equals(entry.getValue(), target)) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public void run() {
            try {
                FileUpload fileUpload = new FileUpload() {
                    @Override
                    protected void onPreUpload(PutObjectResult result) {
                        if (!reqobjs.containsKey(result.getRequestId())) {
                            reqobjs.put(result.getRequestId(), target);
                        }
                    }
                };

                fileUpload.setOss(oss)
                        .setBucket(bucket)
                        .setFileName(fileName)
                        .setFilePath(filePath)
                        .setOssUploadListener(new OssUploadListener() {
                            @Override
                            public void onOssUploadProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                OssUtils.this.onOssUploadProgress(request, currentSize, totalSize, params.get("UPLOAD_TYPE_FLAG"), params.get("UPLOAD_TYPE"), target);
                            }

                            @Override
                            public void onOssUploadSuccess(boolean success, PutObjectRequest request, PutObjectResult result) {
                                try {
                                    List<OssResultItem> ossResultItems = dataobjs.get(target);
                                    OssResultItem ossResultItem = new OssResultItem();
                                    ossResultItem.setRequest(request);
                                    ossResultItem.setResult(result);
                                    ossResultItem.setUrl(String.format("%s", request.getObjectKey()));
                                    ossResultItems.add(ossResultItem);
                                    if (getTaskRequestCount() == ossResultItems.size()) {
                                        OssUtils.this.onOssUploadSuccess(ossResultItems, uploadDir, params.get("UPLOAD_TYPE_FLAG"), params.get("UPLOAD_TYPE"), target);
                                    }
                                } catch (Exception e) {
                                    Logger.L.error("upload oss process error;", e);
                                }
                            }

                            @Override
                            public void onOssUploadSuccess(boolean success, ResumableUploadRequest request, ResumableUploadResult result) {
                                try {
                                    List<OssResultItem> ossResultItems = dataobjs.get(target);
                                    OssResultItem ossResultItem = new OssResultItem();
                                    ossResultItem.setResumableUploadRequest(request);
                                    ossResultItem.setResumableUploadResult(result);
                                    ossResultItem.setUrl(String.format("%s", request.getObjectKey()));
                                    ossResultItems.add(ossResultItem);
                                    OssUtils.this.onOssUploadSuccess(ossResultItems, uploadDir, params.get("UPLOAD_TYPE_FLAG"), params.get("UPLOAD_TYPE"), target);
                                } catch (Exception e) {
                                    Logger.L.error("upload oss process error;", e);
                                }
                            }

                            @Override
                            public void onOssUploadSuccess(boolean success, AppendObjectRequest request, AppendObjectResult result) {
                                try {
                                    List<OssResultItem> ossResultItems = dataobjs.get(target);
                                    OssResultItem ossResultItem = new OssResultItem();
                                    ossResultItem.setAppendObjectResult(result);
                                    ossResultItem.setUrl(String.format("%s", request.getObjectKey()));
                                    ossResultItems.add(ossResultItem);
                                    OssUtils.this.onOssUploadSuccess(ossResultItems, uploadDir, params.get("UPLOAD_TYPE_FLAG"), params.get("UPLOAD_TYPE"), target);
                                } catch (Exception e) {
                                    Logger.L.error("upload oss process error;", e);
                                }
                            }

                            @Override
                            public void onOssUploadProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                                OssUtils.this.onOssUploadProgress(request, currentSize, totalSize, params.get("UPLOAD_TYPE_FLAG"), params.get("UPLOAD_TYPE"), target);
                            }

                            @Override
                            public void onOssUploadProgress(AppendObjectRequest request, long currentSize, long totalSize) {
                                OssUtils.this.onOssUploadProgress(request, currentSize, totalSize, params.get("UPLOAD_TYPE_FLAG"), params.get("UPLOAD_TYPE"), target);
                            }
                        });
                if (isBreakpointUpload) {
                    fileUpload.asyncResumableUpload(params);
                } else if (isAppend) {
                    fileUpload.appendUpload(params);
                } else {
                    if (sync) {
                        fileUpload.syncUpload(params);
                    } else {
                        fileUpload.asyncUpload(params);
                    }
                }
            } catch (Exception e) {
                Logger.L.error("oss upload error:", e);
            }
        }
    }

    public void requestALiYunAssumeRole(Context context, String token, HashMap<String, Object> uploadMap) {
        this.uploadMap = uploadMap;
        Func1<ComRequestUrlType, String> urlAction = BaseApplication.getInstance().getUrlAction();
        if (urlAction == null) {
            OssUtils.this.onRequestALiYunAssumeRoleErrored();
        }
        String url = urlAction.call(ComRequestUrlType.ALiYunAssumeRole);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        url = "https://rest.iqiaorong.com/rest/aliyunAssumeRole";
        OkgoUtils.getInstance().postHttpByJson(context, url, params, ALiYunOssRole.class, new JrJsonCallback<ALiYunOssRole, Object>() {
            @Override
            public void onSuccess(ALiYunOssRole aLiYunOssRole, Response response, Object extras) {
                OssUtils.this.onRequestALiYunAssumeRoleSuccess(aLiYunOssRole, OssUtils.this.uploadMap);
            }

            @Override
            public void onError(Response response, Exception e, Object extras) {
                OssUtils.this.onRequestALiYunAssumeRoleErrored();
            }
        });
    }
}
