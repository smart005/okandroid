package com.cloud.basicfun.oss;

import com.alibaba.sdk.android.oss.model.AppendObjectRequest;
import com.alibaba.sdk.android.oss.model.AppendObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/1
 * @Description:oss上传监听
 * @Modifier:
 * @ModifyContent:
 */
public interface OssUploadListener {

    public void onOssUploadSuccess(boolean success, PutObjectRequest request, PutObjectResult result);

    public void onOssUploadSuccess(boolean success, ResumableUploadRequest request, ResumableUploadResult result);

    public void onOssUploadSuccess(boolean success, AppendObjectRequest request, AppendObjectResult result);

    public void onOssUploadProgress(PutObjectRequest request, long currentSize, long totalSize);

    public void onOssUploadProgress(ResumableUploadRequest request, long currentSize, long totalSize);

    public void onOssUploadProgress(AppendObjectRequest request, long currentSize, long totalSize);
}
