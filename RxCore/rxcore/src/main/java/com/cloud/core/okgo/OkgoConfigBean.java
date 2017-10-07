package com.cloud.core.okgo;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.InputStream;

import okhttp3.Interceptor;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/1
 * @Description: OkGo基础信息配置
 * @Modifier:
 * @ModifyContent:
 */

public class OkgoConfigBean {

    //是否开启debug模式
    private boolean debug = false;
    //连接超时时间
    private long connectTimeout = 10000;
    //读取超时时间
    private long readTimeOut = 10000;
    //写入超时时间
    private long writeTimeOut = 10000;
    //缓存模式
    private CacheMode cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
    //缓存时间
    private long cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
    //重连次数
    private int retryCount = 3;
    //是否开启管理cookie
    private boolean isEnableCookieStore = true;
    //管理cookie模式 0内存管理 1持久化
    private int manageCookieType = 1;
    //证书文件流
    private InputStream certificates = null;
    //拦截器
    private Interceptor interceptor = null;
    //公共头参数
    private HttpHeaders commonHeaders = null;
    //公共参数
    private HttpParams commonParams = null;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public long getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isEnableCookieStore() {
        return isEnableCookieStore;
    }

    public void setEnableCookieStore(boolean enableCookieStore) {
        isEnableCookieStore = enableCookieStore;
    }

    public int getManageCookieType() {
        return manageCookieType;
    }

    public void setManageCookieType(int manageCookieType) {
        this.manageCookieType = manageCookieType;
    }

    public InputStream getCertificates() {
        return certificates;
    }

    public void setCertificates(InputStream certificates) {
        this.certificates = certificates;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public HttpHeaders getCommonHeaders() {
        return commonHeaders;
    }

    public void setCommonHeaders(HttpHeaders commonHeaders) {
        this.commonHeaders = commonHeaders;
    }

    public HttpParams getCommonParams() {
        return commonParams;
    }

    public void setCommonParams(HttpParams commonParams) {
        this.commonParams = commonParams;
    }
}
