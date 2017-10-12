package com.cloud.basicfun.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.AppendObjectRequest;
import com.alibaba.sdk.android.oss.model.AppendObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.cloud.basicfun.beans.AppendPositionBean;
import com.cloud.basicfun.beans.BreakPointBean;
import com.cloud.core.db.DbUtils;
import com.cloud.core.db.Selector;
import com.cloud.core.utils.StorageUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/2/24
 * @Description: 文件上传类
 * @Modifier:
 * @ModifyContent:
 */

public class FileUpload {

    private OSS oss;
    private String bucket;
    private String fileName;
    private String filePath;
    private OssUploadListener ossUploadListener = null;

    public FileUpload setOss(OSS oss) {
        this.oss = oss;
        return this;
    }

    public FileUpload setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public FileUpload setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileUpload setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public FileUpload setOssUploadListener(OssUploadListener listener) {
        this.ossUploadListener = listener;
        return this;
    }

    protected void onPreUpload(PutObjectResult result) {

    }

    // 从本地文件上传，采用阻塞的同步接口
    public void syncUpload(Map<String, String> params) {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, fileName, filePath);
        put.setCallbackParam(params);
        try {
            PutObjectResult putResult = oss.putObject(put);
        } catch (ClientException e) {
            // 本地异常如网络异常等
        } catch (ServiceException e) {
            // 服务异常
        }
    }

    // 从本地文件上传，使用非阻塞的异步接口
    public void asyncUpload(Map<String, String> params) {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, fileName, filePath);
        try {
            PutObjectResult putObjectResult = oss.putObject(put);
            onPreUpload(putObjectResult);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadProgress(request, currentSize, totalSize);
                }
            }
        });
        put.setCallbackParam(params);
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadSuccess(true, request, result);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadSuccess(false, request, null);
                }
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
            }
        });
    }

    public void asyncResumableUpload(final Map<String, String> params) {
        final File recordDir = StorageUtils.getOssRecord();
        final BreakPointBean breakPointBean = new BreakPointBean();
        Selector<BreakPointBean> selectors = DbUtils.getInstance().getDbManager().selector(BreakPointBean.class).
                where(breakPointBean.getForKey(breakPointBean.getOriginalFileName()), "=", params.get("ORIGINAL_FILE_NAME"));
        BreakPointBean firstOriginalFileName = selectors.findFirst();
        ResumableUploadRequest request = null;
        if (firstOriginalFileName == null) {//数据库没有查寻到，进行数据的添加
            firstOriginalFileName = new BreakPointBean();
            firstOriginalFileName.setOriginalFileName(params.get("ORIGINAL_FILE_NAME"));
            firstOriginalFileName.setFileName(fileName);
            firstOriginalFileName.setFilePath(filePath);
            firstOriginalFileName.setRecordDirectory(recordDir.getAbsolutePath());
            DbUtils.getInstance().getDbManager().addOrUpdate(firstOriginalFileName);
            request = new ResumableUploadRequest(bucket, fileName, filePath, recordDir.getAbsolutePath());
        } else {
            request = new ResumableUploadRequest(bucket, fileName, filePath, firstOriginalFileName.getRecordDirectory());
        }
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadProgress(request, currentSize, totalSize);
                }
            }
        });
        request.setCallbackParam(params);
        OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                Selector<BreakPointBean> selectors = DbUtils.getInstance().getDbManager().selector(BreakPointBean.class).
                        where(breakPointBean.getForKey(breakPointBean.getOriginalFileName()), "=", request.getObjectKey());
                List<BreakPointBean> breakPoints = selectors.findAll();
                DbUtils.getInstance().getDbManager().delete(breakPoints);
                StorageUtils.deleteDirectory(recordDir);
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadSuccess(true, request, result);
                }
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadSuccess(false, request, null);
                }
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
            }
        });
        resumableTask.waitUntilFinished();
    }


    public void appendUpload(Map<String, String> params) {
        AppendObjectRequest append = new AppendObjectRequest(bucket, fileName, filePath);
        AppendPositionBean appendPosition = new AppendPositionBean();
        Selector<AppendPositionBean> selectors = DbUtils.getInstance().getDbManager().selector(AppendPositionBean.class).
                where(appendPosition.getForKey(appendPosition.getOriginalFileName()), "=", params.get("ORIGINAL_FILE_NAME"));
        AppendPositionBean firstOriginalFileName = selectors.findFirst();
        if (firstOriginalFileName == null) {//数据库没有查寻到，进行数据的添加
            firstOriginalFileName = new AppendPositionBean();
            firstOriginalFileName.setOriginalFileName(params.get("ORIGINAL_FILE_NAME"));
            firstOriginalFileName.setFileName(fileName);
            firstOriginalFileName.setFilePath(filePath);
            append.setPosition(0);
        } else {
            long position = 0;
            // 设置追加位置
            position = firstOriginalFileName.getPosition();
            append.setPosition(position);
        }
        append.setProgressCallback(new OSSProgressCallback<AppendObjectRequest>() {
            @Override
            public void onProgress(AppendObjectRequest request, long currentSize, long totalSize) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadProgress(request, currentSize, totalSize);
                }
            }
        });
        final AppendPositionBean finalFirstOriginalFileName = firstOriginalFileName;
        OSSAsyncTask task = oss.asyncAppendObject(append, new OSSCompletedCallback<AppendObjectRequest, AppendObjectResult>() {
            @Override
            public void onSuccess(AppendObjectRequest request, AppendObjectResult result) {
                finalFirstOriginalFileName.setPosition(request.getPosition());
                DbUtils.getInstance().getDbManager().addOrUpdate(finalFirstOriginalFileName);
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadSuccess(true, request, result);
                }
            }

            @Override
            public void onFailure(AppendObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (ossUploadListener != null) {
                    ossUploadListener.onOssUploadSuccess(false, request, null);
                }
                finalFirstOriginalFileName.setPosition(request.getPosition());
                DbUtils.getInstance().getDbManager().addOrUpdate(finalFirstOriginalFileName);
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
            }
        });
        task.waitUntilFinished();
    }
}
