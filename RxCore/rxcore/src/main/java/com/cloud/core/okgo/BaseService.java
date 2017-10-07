package com.cloud.core.okgo;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.core.enums.RequestType;
import com.cloud.core.logger.Logger;
import com.cloud.core.okgo.callBack.JsonConvert;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.PathsUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.OptionsRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.lzy.okrx.RxAdapter;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/14
 * @Description:api请求基类 请求方式1
 * Observable observable = baseConfig(context, getServiceAPI(context,Class<API接口类>).接口名(参数值));
 * observable.subscribe(new BaseSubscriber<返回实体类型>(context) {
 * @Override protected void onSuccessful(返回实体类型 bean) {
 * //成功回调
 * }
 * @Override protected void onErrored(Throwable throwable) {
 * //失败回调
 * }
 * @Override protected void onFinished() {
 * //请求结束回调
 * }
 * });
 */
public class BaseService {

    /**
     * token值
     */
    private String token = "";
    /**
     * 接口名
     */
    private String apiName = "";

    private BaseSubscriber baseSubscriber = null;

    /**
     * 获取token值
     */

    public String getToken() {
        if (token == null) {
            token = "";
        }
        return token;
    }

    /**
     * 设置token值
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取接口名
     */
    public String getApiName() {
        if (apiName == null) {
            apiName = "";
        }
        return apiName;
    }

    /**
     * 设置接口名
     *
     * @param apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * API请求异常
     *
     * @param throwable 异常信息
     */
    protected void onRequestError(Throwable throwable) {

    }

    /**
     * API请求完成(结束)
     */
    protected void onRequestCompleted() {

    }

    public void setBaseSubscriber(BaseSubscriber subscriber) {
        this.baseSubscriber = subscriber;
    }

    public BaseSubscriber getBaseSubscriber() {
        return this.baseSubscriber;
    }

    protected <T> Observable<T> baseConfig(Context context, BaseService baseService,
                                           RetrofitParams retrofitParams,
                                           OkgoValidParam validParam) {
        Observable<T> observable = null;
        Class<T> dataClass = retrofitParams.getDataClass();
        try {
            if (retrofitParams != null && !TextUtils.isEmpty(retrofitParams.getRequestUrl())) {
                String requestUrl = retrofitParams.getRequestUrl();
                //请求头
                HttpHeaders httpHeaders = new HttpHeaders();
                if (retrofitParams.getHeadParams() != null && retrofitParams.getHeadParams().size() > 0) {
                    HashMap<String, String> headParams = retrofitParams.getHeadParams();
                    for (HashMap.Entry<String, String> entry : headParams.entrySet()) {
                        //参数名
                        String key = entry.getKey();
                        if (TextUtils.isEmpty(key)) {
                            continue;
                        }
                        //参数值
                        String value = entry.getValue();
                        if (TextUtils.isEmpty(value)) {
                            continue;
                        }
                        httpHeaders.put(key, value);
                    }
                }
                //检查头部是否已添加token，没有则添加
                if (!TextUtils.isEmpty(token)) {
                    if (validParam.getApiCheckAnnotation().IsTokenValid()) {
                        String tokenName = retrofitParams.getTokenName();
                        if (!TextUtils.isEmpty(tokenName)) {
                            httpHeaders.put(tokenName, token);
                        }
                    }
                }
                //请求参数
                HttpParams httpParams = getHttpParams(retrofitParams);
                if (retrofitParams.getRequestType() == RequestType.POST) {
                    PostRequest postRequest = OkGo.post(requestUrl).
                            tag(context)
                            .headers(httpHeaders);
                    if (retrofitParams.getRequestContentType() == RequestContentType.Json) {
                        String postJson = JsonUtils.toStr(retrofitParams.getParams());
                        postRequest.upJson(postJson);
                    } else {
                        postRequest.params(httpParams);
                    }
                    if (retrofitParams.isCache()) {
                        if (!TextUtils.isEmpty(retrofitParams.getCacheKey())) {
                            postRequest.cacheKey(retrofitParams.getCacheKey());
                        }
                        postRequest.cacheTime(retrofitParams.getCacheTime());
                        postRequest.cacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
                    }
                    observable = postRequest.getCall(new JsonConvert(dataClass), RxAdapter.<T>create()).observeOn(AndroidSchedulers.mainThread());
                } else if (retrofitParams.getRequestType() == RequestType.DELETE) {
                    DeleteRequest deleteRequest = OkGo.delete(requestUrl)
                            .tag(context)
                            .headers(httpHeaders);
                    if (retrofitParams.getRequestContentType() == RequestContentType.Json) {
                        String postJson = JsonUtils.toStr(retrofitParams.getParams());
                        deleteRequest.upJson(postJson);
                    } else {
                        deleteRequest.params(httpParams);
                    }
                    if (retrofitParams.isCache()) {
                        if (!TextUtils.isEmpty(retrofitParams.getCacheKey())) {
                            deleteRequest.cacheKey(retrofitParams.getCacheKey());
                        }
                        deleteRequest.cacheTime(retrofitParams.getCacheTime());
                        deleteRequest.cacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
                    }
                    observable = deleteRequest.getCall(new JsonConvert(dataClass), RxAdapter.<T>create()).observeOn(AndroidSchedulers.mainThread());
                } else if (retrofitParams.getRequestType() == RequestType.PUT) {
                    PutRequest putRequest = OkGo.put(requestUrl)
                            .tag(context)
                            .headers(httpHeaders);
                    if (retrofitParams.getRequestContentType() == RequestContentType.Json) {
                        String postJson = JsonUtils.toStr(retrofitParams.getParams());
                        putRequest.upJson(postJson);
                    } else {
                        putRequest.params(httpParams);
                    }
                    if (retrofitParams.isCache()) {
                        if (!TextUtils.isEmpty(retrofitParams.getCacheKey())) {
                            putRequest.cacheKey(retrofitParams.getCacheKey());
                        }
                        putRequest.cacheTime(retrofitParams.getCacheTime());
                        putRequest.cacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
                    }
                    observable = putRequest.getCall(new JsonConvert(dataClass), RxAdapter.<T>create()).observeOn(AndroidSchedulers.mainThread());
                } else if (retrofitParams.getRequestType() == RequestType.PATCH) {
                    OptionsRequest optionsRequest = OkGo.options(requestUrl)
                            .tag(context)
                            .headers(httpHeaders);
                    if (retrofitParams.getRequestContentType() == RequestContentType.Json) {
                        String postJson = JsonUtils.toStr(retrofitParams.getParams());
                        optionsRequest.upJson(postJson);
                    } else {
                        optionsRequest.params(httpParams);
                    }
                    if (retrofitParams.isCache()) {
                        if (!TextUtils.isEmpty(retrofitParams.getCacheKey())) {
                            optionsRequest.cacheKey(retrofitParams.getCacheKey());
                        }
                        optionsRequest.cacheTime(retrofitParams.getCacheTime());
                        optionsRequest.cacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
                    }
                    observable = optionsRequest.getCall(new JsonConvert(dataClass), RxAdapter.<T>create()).observeOn(AndroidSchedulers.mainThread());
                } else {
                    GetRequest getRequest = OkGo.get(requestUrl)
                            .tag(context)
                            .headers(httpHeaders)
                            .params(httpParams);
                    if (retrofitParams.isCache()) {
                        if (!TextUtils.isEmpty(retrofitParams.getCacheKey())) {
                            getRequest.cacheKey(retrofitParams.getCacheKey());
                        }
                        getRequest.cacheTime(retrofitParams.getCacheTime());
                        getRequest.cacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
                    }
                    observable = getRequest.getCall(new JsonConvert(dataClass), RxAdapter.<T>create()).observeOn(AndroidSchedulers.mainThread());
                }
            } else {
                baseService.onRequestCompleted();
            }
        } catch (Exception e) {
            baseService.onRequestCompleted();
            Logger.L.error(e);
        }
        return observable;
    }

    private HttpParams getHttpParams(RetrofitParams retrofitParams) {
        HttpParams httpParams = null;
        if (retrofitParams.getRequestType() == RequestType.GET
                || retrofitParams.getRequestContentType() != RequestContentType.Json) {
            httpParams = new HttpParams();
            if (retrofitParams.getParams() != null && retrofitParams.getParams().size() > 0) {
                HashMap<String, Object> params = retrofitParams.getParams();
                for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                    //参数名
                    String key = entry.getKey();
                    if (TextUtils.isEmpty(key)) {
                        continue;
                    }
                    //参数值
                    Object value = entry.getValue();
                    if (value == null) {
                        continue;
                    }
                    if (value instanceof Integer) {
                        httpParams.put(key, (Integer) value);
                    } else if (value instanceof Long) {
                        httpParams.put(key, (Long) value);
                    } else if (value instanceof String) {
                        httpParams.put(key, (String) value);
                    } else if (value instanceof Double) {
                        httpParams.put(key, (Double) value);
                    } else if (value instanceof Float) {
                        httpParams.put(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        httpParams.put(key, (Boolean) value);
                    }
                }
            }
        }
        return httpParams;
    }

    protected <I, S extends BaseService> void requestObject(Context context,
                                                            Class<I> apiClass,
                                                            S server,
                                                            BaseSubscriber baseSubscriber,
                                                            OkgoValidParam validParam,
                                                            Func1<String, String> urlAction,
                                                            Func1<I, RetrofitParams> decApiAction,
                                                            Func0<String> configBaseUrlAction) {
        try {
            if (validParam.isFlag()) {
                I decApi = BaseOkgoValidParsing.getInstance().createAPI(apiClass, urlAction,
                        validParam.getApiCheckAnnotation().isGetBaseUrl());
                if (decApiAction == null || decApi == null || validParam.getApiCheckAnnotation() == null) {
                    baseSubscriber.onCompleted();
                } else {
                    RetrofitParams retrofitParams = decApiAction.call(decApi);
                    if (retrofitParams == null || !retrofitParams.getFlag()) {
                        baseSubscriber.onCompleted();
                    } else {
                        //若api类未指定base url类型名称则不作请求处理
                        if (retrofitParams.getUrlTypeName() == null) {
                            baseSubscriber.onCompleted();
                        } else {
                            if (urlAction == null || TextUtils.isEmpty(retrofitParams.getUrlTypeName().value())) {
                                baseSubscriber.onCompleted();
                            } else {
                                retrofitParams.setIsGetBaseUrl(validParam.getApiCheckAnnotation().isGetBaseUrl());
                                String baseUrl = "";
                                if (!retrofitParams.getIsGetBaseUrl()) {
                                    baseUrl = urlAction.call(retrofitParams.getUrlTypeName().value());
                                }
                                //设置token名字
                                retrofitParams.setTokenName(retrofitParams.getUrlTypeName().tokenName());
                                //NO_CACHE: 不使用缓存,该模式下,cacheKey,cacheTime 参数均无效
                                //DEFAULT: 按照HTTP协议的默认缓存规则，例如有304响应头时缓存。
                                //REQUEST_FAILED_READ_CACHE：先请求网络，如果请求网络失败，则读取缓存，如果读取缓存失败，本次请求失败。
                                //IF_NONE_CACHE_REQUEST：如果缓存不存在才请求网络，否则使用缓存。
                                //FIRST_CACHE_THEN_REQUEST：先使用缓存，不管是否存在，仍然请求网络。
                                //缓存的过期时间,单位毫秒
                                //为确保未设置缓存请求几乎不做缓存，此处默认缓存时间暂设为5秒
                                retrofitParams.setCache(validParam.getApiCheckAnnotation().IsCache());
                                String cacheKey = MessageFormat.format(validParam.getCacheKey(), apiClass.getSimpleName());
                                retrofitParams.setCacheKey(cacheKey);
                                if (retrofitParams.isCache()) {
                                    long milliseconds = ConvertUtils.toMilliseconds(validParam.getApiCheckAnnotation().CacheTime(),
                                            validParam.getApiCheckAnnotation().CacheTimeUnit());
                                    retrofitParams.setCacheTime(milliseconds);
                                } else {
                                    retrofitParams.setCacheTime(5000);
                                }
                                //拼接完整的url
                                //del请求看delQuery参数是不是为空
                                if (!ObjectJudge.isNullOrEmpty(retrofitParams.getDelQueryParams())) {
                                    StringBuffer querysb = new StringBuffer();
                                    for (Map.Entry<String, String> entry : retrofitParams.getDelQueryParams().entrySet()) {
                                        querysb.append(MessageFormat.format("{0}={1},", entry.getKey(), entry.getValue()));
                                    }
                                    if (querysb.length() > 0) {
                                        if (retrofitParams.getRequestUrl().indexOf("?") < 0) {
                                            retrofitParams.setRequestUrl(String.format("%s?%s",
                                                    retrofitParams.getRequestUrl(),
                                                    querysb.substring(0, querysb.length() - 1)));
                                        } else {
                                            retrofitParams.setRequestUrl(String.format("%s&%s",
                                                    retrofitParams.getRequestUrl(),
                                                    querysb.substring(0, querysb.length() - 1)));
                                        }
                                    }
                                }
                                if (configBaseUrlAction != null && retrofitParams.getIsGetBaseUrl()) {
                                    retrofitParams.setRequestUrl(PathsUtils.combine(configBaseUrlAction.call(),
                                            retrofitParams.getRequestUrl()));
                                } else {
                                    if (retrofitParams.getIsJoinUrl()) {
                                        retrofitParams.setRequestUrl(PathsUtils.combine(baseUrl, retrofitParams.getRequestUrl()));
                                    }
                                }
                                Observable observable = server.baseConfig(context, server, retrofitParams, validParam);
                                observable.subscribe(baseSubscriber);
                            }
                        }
                    }
                }
            } else {
                baseSubscriber.onCompleted();
            }
        } catch (Exception e) {
            baseSubscriber.onCompleted();
        }
    }
}
