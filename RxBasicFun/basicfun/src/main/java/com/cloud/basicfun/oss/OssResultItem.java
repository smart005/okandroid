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
 * @Description:oss返回结束数据
 * @Modifier:
 * @ModifyContent:
 */
public class OssResultItem {
    /**
     * 相对url
     */
    private String url = "";

    private PutObjectRequest request = null;

    private PutObjectResult result = null;

    private ResumableUploadRequest resumableUploadRequest = null;

    private ResumableUploadResult resumableUploadResult = null;

    private AppendObjectRequest appendObjectRequest = null;

    private AppendObjectResult appendObjectResult = null;

    /**
     * 获取相对url
     */
    public String getUrl() {
        if (url == null) {
            url = "";
        }
        return url;
    }

    /**
     * 设置相对url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     */
    public PutObjectRequest getRequest() {
        return request;
    }

    /**
     * @param request
     */
    public void setRequest(PutObjectRequest request) {
        this.request = request;
    }

    /**
     *
     */
    public PutObjectResult getResult() {
        return result;
    }

    /**
     * @param result
     */
    public void setResult(PutObjectResult result) {
        this.result = result;
    }

    /**
     *
     */
    public ResumableUploadRequest getResumableUploadRequest() {
        return resumableUploadRequest;
    }

    /**
     * @param resumableUploadRequest
     */
    public void setResumableUploadRequest(ResumableUploadRequest resumableUploadRequest) {
        this.resumableUploadRequest = resumableUploadRequest;
    }

    /**
     *
     */
    public ResumableUploadResult getResumableUploadResult() {
        return resumableUploadResult;
    }

    /**
     * @param resumableUploadResult
     */
    public void setResumableUploadResult(ResumableUploadResult resumableUploadResult) {
        this.resumableUploadResult = resumableUploadResult;
    }

//    /**
//     *
//     */
//    public AppendObjectRequest getAppendObjectRequest() {
//        if (appendObjectRequest == null) {
//            appendObjectRequest = new AppendObjectRequest();
//        }
//        return appendObjectRequest;
//    }

//    /**
//     * @param appendObjectRequest
//     */
//    public void setAppendObjectRequest(AppendObjectRequest appendObjectRequest) {
//        this.appendObjectRequest = appendObjectRequest;
//    }
//
    /**
     *
     */
    public AppendObjectResult getAppendObjectResult() {
        if (appendObjectResult == null) {
            appendObjectResult = new AppendObjectResult();
        }
        return appendObjectResult;
    }

    /**
     * @param appendObjectResult
     */
    public void setAppendObjectResult(AppendObjectResult appendObjectResult) {
        this.appendObjectResult = appendObjectResult;
    }

}
