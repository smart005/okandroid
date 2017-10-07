package com.cloud.core.beans;

import android.text.TextUtils;

import com.cloud.core.annotations.BaseUrlTypeName;
import com.cloud.core.enums.RequestType;
import com.cloud.core.okgo.RequestContentType;

import java.util.HashMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/1
 * @Description:http 改造参数
 * @Modifier:
 * @ModifyContent:
 */
public class RetrofitParams {
    /**
     * 请求类型
     */
    private RequestType requestType = RequestType.GET;
    /**
     * 请求地址
     */
    private String requestUrl = "";
    /**
     * 头信息
     */
    private HashMap<String, String> headParams = null;
    /**
     * 请求参数
     */
    private HashMap<String, Object> params = null;
    /**
     * del query请求参数
     */
    private HashMap<String, String> delQueryParams = null;

    /**
     * 是否缓存
     */
    private boolean isCache = false;

    /**
     * 缓存key
     */
    private String cacheKey = "";

    /**
     * 缓存时间(单位秒)
     */
    private long cacheTime = 0;
    /**
     * api名称
     */
    private String apiName = "";
    /**
     * api名称
     */
    private Class dataClass = null;
    /**
     * 请求验证是否通过
     */
    private boolean flag = false;
    /**
     * 是否拼接url
     */
    private boolean isJoinUrl = false;

    private BaseUrlTypeName urlTypeName = null;
    /**
     * 是否获取基础url
     */
    private boolean isGetBaseUrl = false;
    /**
     * token名称默认为token
     */
    private String tokenName = "token";
    /**
     * 数据提交方式
     */
    private RequestContentType requestContentType = null;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public HashMap<String, String> getHeadParams() {
        if (headParams == null) {
            headParams = new HashMap<String, String>();
        }
        return headParams;
    }

    public HashMap<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        return params;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    /**
     * 获取api名称
     */
    public String getApiName() {
        if (apiName == null) {
            apiName = "";
        }
        return apiName;
    }

    /**
     * 设置api名称
     *
     * @param apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Class getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class dataClass) {
        this.dataClass = dataClass;
    }

    /**
     * 获取请求验证是否通过
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * 设置请求验证是否通过
     *
     * @param flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * 获取是否拼接url
     */
    public boolean getIsJoinUrl() {
        return isJoinUrl;
    }

    /**
     * 设置是否拼接url
     *
     * @param isJoinUrl
     */
    public void setIsJoinUrl(boolean isJoinUrl) {
        this.isJoinUrl = isJoinUrl;
    }

    public HashMap<String, String> getDelQueryParams() {
        if (delQueryParams == null) {
            delQueryParams = new HashMap<String, String>();
        }
        return delQueryParams;
    }

    public void setDelQueryParams(HashMap<String, String> delQueryParams) {
        this.delQueryParams = delQueryParams;
    }

    public BaseUrlTypeName getUrlTypeName() {
        return urlTypeName;
    }

    public void setUrlTypeName(BaseUrlTypeName urlTypeName) {
        this.urlTypeName = urlTypeName;
    }

    /**
     * 获取是否获取基础url
     */
    public boolean getIsGetBaseUrl() {
        return isGetBaseUrl;
    }

    /**
     * 设置是否获取基础url
     *
     * @param isGetBaseUrl
     */
    public void setIsGetBaseUrl(boolean isGetBaseUrl) {
        this.isGetBaseUrl = isGetBaseUrl;
    }

    public String getTokenName() {
        if (TextUtils.isEmpty(tokenName)) {
            tokenName = "token";
        }
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public RequestContentType getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(RequestContentType requestContentType) {
        this.requestContentType = requestContentType;
    }
}
