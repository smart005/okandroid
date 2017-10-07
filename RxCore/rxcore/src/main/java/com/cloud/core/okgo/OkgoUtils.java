package com.cloud.core.okgo;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.cloud.core.enums.FileLevelEnum;
import com.cloud.core.enums.ResponseType;
import com.cloud.core.logger.Logger;
import com.cloud.core.okgo.callBack.JrFileCallback;
import com.cloud.core.okgo.callBack.JrJsonArrayCallback;
import com.cloud.core.okgo.callBack.JrJsonCallback;
import com.cloud.core.okgo.callBack.JrStringCallback;
import com.cloud.core.okgo.callBack.JrUploadCallback;
import com.cloud.core.okgo.callBack.JsonArrayCallback;
import com.cloud.core.okgo.callBack.JsonCallback;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.core.utils.ThreadPoolUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/2
 * @Description: 网络请求工具类，未集成RxJava
 * @Modifier:
 * @ModifyContent:
 */
public class OkgoUtils {

    private static String ALL_CACHE_KEY = "186896793";

    private static OkgoUtils okgoUtils = null;

    public static OkgoUtils getInstance() {
        return okgoUtils == null ? okgoUtils = new OkgoUtils() : okgoUtils;
    }

    private OkgoUtils() {
    }

    public Response getHttpResponse(Context context, String url) {
        return getHttpResponse(context, url, null);
    }

    public Response getHttpResponse(Context context, String url, HashMap<String, Object> params) {
        Response response = null;
        try {
            GetRequest getRequest = OkGo.get(url).
                    tag(context)
                    .connTimeOut(3000)
                    .readTimeOut(3000)
                    .writeTimeOut(3000);
            if (params != null && params.size() > 0) {
                for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                    //参数名
                    String key = entry.getKey();
                    //文件路径
                    Object value = entry.getValue();
                    if (TextUtils.isEmpty(key) || value == null) {
                        continue;
                    }
                    if (value instanceof Integer) {
                        getRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        getRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        getRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        getRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        getRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        getRequest.params(key, (Boolean) value);
                    }
                }
            }
            response = getRequest.execute();
        } catch (Exception e) {
            Logger.L.error("get http request error:", e);
        }
        return response;
    }

    public String getHttpResponseMain(Context context, String url) {
        return getHttpResponseMain(context, url, null);
    }

    public String getHttpResponseMain(final Context context, final String url, final HashMap<String, Object> params) {
        String response = null;
        try {
            List<Callable<String>> callList = new ArrayList<>();
            callList.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    GetRequest getRequest = OkGo.get(url).
                            tag(context)
                            .connTimeOut(3000)
                            .readTimeOut(3000)
                            .writeTimeOut(3000);
                    if (params != null && params.size() > 0) {
                        for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                            //参数名
                            String key = entry.getKey();
                            //文件路径
                            Object value = entry.getValue();
                            if (TextUtils.isEmpty(key) || value == null) {
                                continue;
                            }
                            if (value instanceof Integer) {
                                getRequest.params(key, (Integer) value);
                            } else if (value instanceof Long) {
                                getRequest.params(key, (Long) value);
                            } else if (value instanceof String) {
                                getRequest.params(key, (String) value);
                            } else if (value instanceof Double) {
                                getRequest.params(key, (Double) value);
                            } else if (value instanceof Float) {
                                getRequest.params(key, (Float) value);
                            } else if (value instanceof Boolean) {
                                getRequest.params(key, (Boolean) value);
                            }
                        }
                    }
                    Response execute = getRequest.execute();
                    return execute.body().string();
                }
            });
            ExecutorService es = ThreadPoolUtils.fixThread();
            List<Future<String>> futures = es.invokeAll(callList);
            Future<String> responseFuture = futures.get(0);
            if (responseFuture != null) {
                if (responseFuture.isDone()) {
                    response = responseFuture.get();
                }
            }
            es.shutdown();

        } catch (Exception e) {
            Logger.L.error("get http request main error:", e);
        }
        return response;
    }

    public <E> void getHttpByString(Context context, String url, JrStringCallback jrStringCallback) {
        getHttpByString(context, url, jrStringCallback, null);
    }

    public <E> void getHttpByString(Context context, String url, JrStringCallback jrStringCallback, E extras) {
        getHttpByString(context, url, null, jrStringCallback, extras);
    }

    public <E> void getHttpByString(Context context, String url, HashMap<String, Object> params, JrStringCallback jrStringCallback) {
        getHttpByString(context, url, params, jrStringCallback, null);
    }

    public <E> void getHttpByString(Context context, String url, HashMap<String, Object> params, JrStringCallback jrStringCallback, E extras) {
        getHttpByString(context, url, null, params, jrStringCallback, extras);
    }

    /**
     * get请求回调string类型
     *
     * @param context          上下文
     * @param url              请求地址
     * @param params           请求参数可为空
     * @param jrStringCallback 请求回调
     */
    public <E> void getHttpByString(Context context,
                                    String url, HashMap<String, String> headerParams,
                                    HashMap<String, Object> params,
                                    final JrStringCallback jrStringCallback,
                                    E extras) {
        try {
            GetRequest getRequest = OkGo.get(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    getRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        getRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        getRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        getRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        getRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        getRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        getRequest.params(key, (Boolean) value);
                    }
                }
            }
            getRequest.execute(new JsonCallback<String, E>(String.class, extras) {
                @Override
                public void onSuccessful(String s, Response response, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onSuccess(s, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(String s, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrStringCallback != null) {
                jrStringCallback.onAfter(extras);
            }
            Logger.L.error("get http by string request error:", e);
        }
    }

    public <T, E> void getHttpByJson(Context context,
                                     String url,
                                     Class<T> dataClass,
                                     JrJsonCallback<T, E> jrJsonCallback) {
        getHttpByJson(context, url, dataClass, jrJsonCallback, null);
    }

    public <T, E> void getHttpByJson(Context context,
                                     String url,
                                     Class<T> dataClass,
                                     JrJsonCallback<T, E> jrJsonCallback,
                                     E extras) {
        getHttpByJson(context, url, null, dataClass, jrJsonCallback, extras);
    }

    public <T, E> void getHttpByJson(Context context,
                                     String url,
                                     HashMap<String, Object> params,
                                     Class<T> dataClass,
                                     JrJsonCallback<T, E> jrJsonCallback) {
        getHttpByJson(context, url, params, dataClass, jrJsonCallback, null);
    }

    public <T, E> void getHttpByJson(Context context,
                                     String url,
                                     HashMap<String, Object> params,
                                     Class<T> dataClass,
                                     JrJsonCallback<T, E> jrJsonCallback,
                                     E extras) {
        getHttpByJson(context, url, null, params, dataClass, jrJsonCallback, extras);
    }

    /**
     * get请求回调json类型
     *
     * @param context        上下文
     * @param url            请求地址
     * @param params         请求参数可为空
     * @param dataClass      json解析class
     * @param jrJsonCallback 请求回调
     * @param <T>            解析类型
     */
    public <T, E> void getHttpByJson(Context context,
                                     String url, HashMap<String, String> headerParams,
                                     HashMap<String, Object> params,
                                     final Class<T> dataClass,
                                     final JrJsonCallback<T, E> jrJsonCallback,
                                     E extras) {
        try {
            GetRequest getRequest = OkGo.get(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    getRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        getRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        getRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        getRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        getRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        getRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        getRequest.params(key, (Boolean) value);
                    }
                }
            }
            getRequest.execute(new JsonCallback<T, E>(dataClass, extras) {
                @Override
                public void onSuccessful(T t, Response response, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onSuccess(t, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(T t, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrJsonCallback != null) {
                jrJsonCallback.onAfter(extras);
            }
            Logger.L.error("get http by json request error:", e);
        }
    }

    public <T, E> void getHttpByArray(Context context,
                                      String url,
                                      Class<T> dataClass,
                                      JrJsonArrayCallback<T, E> jrJsonArrayCallback) {
        getHttpByArray(context, url, dataClass, jrJsonArrayCallback, null);
    }

    public <T, E> void getHttpByArray(Context context,
                                      String url,
                                      Class<T> dataClass,
                                      JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                      E extras) {
        getHttpByArray(context, url, null, dataClass, jrJsonArrayCallback, extras);
    }

    public <T, E> void getHttpByArray(Context context,
                                      String url,
                                      HashMap<String, Object> params,
                                      Class<T> dataClass,
                                      JrJsonArrayCallback<T, E> jrJsonArrayCallback) {
        getHttpByArray(context, url, params, dataClass, jrJsonArrayCallback, null);
    }

    public <T, E> void getHttpByArray(Context context,
                                      String url,
                                      HashMap<String, Object> params,
                                      Class<T> dataClass,
                                      JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                      E extras) {
        getHttpByArray(context, url, null, params, dataClass, jrJsonArrayCallback, extras);
    }

    /**
     * get请求回调json类型
     *
     * @param context             上下文
     * @param url                 请求地址
     * @param params              请求参数可为空
     * @param dataClass           json解析class
     * @param jrJsonArrayCallback 请求回调
     * @param <T>                 解析类型
     */
    public <T, E> void getHttpByArray(Context context,
                                      String url, HashMap<String, String> headerParams,
                                      HashMap<String, Object> params,
                                      final Class<T> dataClass,
                                      final JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                      E extras) {
        try {
            GetRequest getRequest = OkGo.get(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    getRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        getRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        getRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        getRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        getRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        getRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        getRequest.params(key, (Boolean) value);
                    }
                }
            }
            getRequest.execute(new JsonArrayCallback<T, E>(dataClass, extras) {
                @Override
                public void onSuccessful(List<T> t, Response response, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onSuccess(t, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(List<T> t, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrJsonArrayCallback != null) {
                jrJsonArrayCallback.onAfter(extras);
            }
            Logger.L.error("get http by json array request error:", e);
        }
    }

    public <E> void postHttpByString(Context context,
                                     String url,
                                     JrStringCallback jrStringCallback) {
        postHttpByString(context, url, jrStringCallback, null);
    }

    public <E> void postHttpByString(Context context,
                                     String url,
                                     JrStringCallback jrStringCallback,
                                     E extras) {
        postHttpByString(context, url, null, jrStringCallback, extras);
    }

    public <E> void postHttpByString(Context context,
                                     String url,
                                     HashMap<String, Object> params,
                                     JrStringCallback jrStringCallback) {
        postHttpByString(context, url, params, jrStringCallback, null);
    }

    public <E> void postHttpByString(Context context,
                                     String url,
                                     HashMap<String, Object> params,
                                     JrStringCallback jrStringCallback,
                                     E extras) {
        postHttpByString(context, url, null, params, jrStringCallback, extras);
    }

    /**
     * post请求回调string类型
     *
     * @param context          上下文
     * @param url              请求地址
     * @param params           请求参数可为空
     * @param jrStringCallback 请求回调
     */
    public <E> void postHttpByString(Context context,
                                     String url, HashMap<String, String> headerParams,
                                     HashMap<String, Object> params,
                                     final JrStringCallback jrStringCallback,
                                     E extras) {
        try {
            PostRequest postRequest = OkGo.post(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    postRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        postRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        postRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        postRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        postRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        postRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        postRequest.params(key, (Boolean) value);
                    }
                }
            }
            postRequest.execute(new JsonCallback<String, E>(String.class, extras) {
                @Override
                public void onSuccessful(String s, Response response, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onSuccess(s, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(String s, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrStringCallback != null) {
                jrStringCallback.onAfter(extras);
            }
            Logger.L.error("post http by string request error:", e);
        }
    }

    public <T, E> void postHttpByJson(Context context,
                                      String url,
                                      Class<T> dataClass,
                                      JrJsonCallback<T, E> jrJsonCallback) {
        postHttpByJson(context, url, dataClass, jrJsonCallback, null);
    }

    public <T, E> void postHttpByJson(Context context,
                                      String url,
                                      Class<T> dataClass,
                                      JrJsonCallback<T, E> jrJsonCallback,
                                      E extras) {
        postHttpByJson(context, url, null, dataClass, jrJsonCallback, extras);
    }

    public <T, E> void postHttpByJson(Context context,
                                      String url, HashMap<String, Object> params,
                                      Class<T> dataClass,
                                      JrJsonCallback<T, E> jrJsonCallback) {
        postHttpByJson(context, url, params, dataClass, jrJsonCallback, null);
    }

    public <T, E> void postHttpByJson(Context context,
                                      String url, HashMap<String, Object> params,
                                      Class<T> dataClass,
                                      JrJsonCallback<T, E> jrJsonCallback,
                                      E extras) {
        postHttpByJson(context, url, null, params, dataClass, jrJsonCallback, extras);
    }

    /**
     * post请求回调json类型
     *
     * @param context        上下文
     * @param url            请求地址
     * @param params         请求参数可为空
     * @param dataClass      json解析class
     * @param jrJsonCallback 请求回调
     * @param <T>            解析类型
     */
    public <T, E> void postHttpByJson(Context context,
                                      String url, HashMap<String, String> headerParams,
                                      HashMap<String, Object> params,
                                      final Class<T> dataClass,
                                      final JrJsonCallback<T, E> jrJsonCallback,
                                      E extras) {
        try {

            PostRequest postRequest = OkGo.post(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    postRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        postRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        postRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        postRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        postRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        postRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        postRequest.params(key, (Boolean) value);
                    }
                }
            }
            postRequest.execute(new JsonCallback<T, E>(dataClass, extras) {
                @Override
                public void onSuccessful(T t, Response response, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onSuccess(t, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(T t, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrJsonCallback != null) {
                jrJsonCallback.onAfter(extras);
            }
            Logger.L.error("post http by json request error:", e);
        }
    }

    public <T, E> void postHttpByArray(Context context,
                                       String url,
                                       Class<T> dataClass,
                                       JrJsonArrayCallback<T, E> jrJsonArrayCallback) {
        postHttpByArray(context, url, dataClass, jrJsonArrayCallback, null);
    }

    public <T, E> void postHttpByArray(Context context,
                                       String url,
                                       Class<T> dataClass,
                                       JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                       E extras) {
        postHttpByArray(context, url, null, dataClass, jrJsonArrayCallback, extras);
    }

    public <T, E> void postHttpByArray(Context context,
                                       String url, HashMap<String, Object> params,
                                       Class<T> dataClass,
                                       JrJsonArrayCallback<T, E> jrJsonArrayCallback) {
        postHttpByArray(context, url, params, dataClass, jrJsonArrayCallback, null);
    }

    public <T, E> void postHttpByArray(Context context,
                                       String url, HashMap<String, Object> params,
                                       Class<T> dataClass,
                                       JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                       E extras) {
        postHttpByArray(context, url, null, params, dataClass, jrJsonArrayCallback, extras);
    }

    /**
     * post请求回调json类型
     *
     * @param context             上下文
     * @param url                 请求地址
     * @param params              请求参数可为空
     * @param dataClass           json解析class
     * @param jrJsonArrayCallback 请求回调
     * @param <T>                 解析类型
     */
    public <T, E> void postHttpByArray(Context context,
                                       String url, HashMap<String, String> headerParams,
                                       HashMap<String, Object> params,
                                       final Class<T> dataClass,
                                       final JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                       E extras) {
        try {

            PostRequest postRequest = OkGo.post(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    postRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        postRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        postRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        postRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        postRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        postRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        postRequest.params(key, (Boolean) value);
                    }
                }
            }
            postRequest.execute(new JsonArrayCallback<T, E>(dataClass, extras) {
                @Override
                public void onSuccessful(List<T> t, Response response, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onSuccess(t, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(List<T> t, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrJsonArrayCallback != null) {
                jrJsonArrayCallback.onAfter(extras);
            }
            Logger.L.error("post http by json array request error:", e);
        }
    }

    public <E> void deleteHttpByString(Context context,
                                       String url,
                                       JrStringCallback jrStringCallback) {
        deleteHttpByString(context, url, jrStringCallback, null);
    }

    public <E> void deleteHttpByString(Context context,
                                       String url,
                                       JrStringCallback jrStringCallback,
                                       E extras) {
        deleteHttpByString(context, url, null, jrStringCallback, extras);
    }

    public <E> void deleteHttpByString(Context context,
                                       String url,
                                       HashMap<String, Object> params,
                                       JrStringCallback jrStringCallback) {
        deleteHttpByString(context, url, params, jrStringCallback, null);
    }

    public <E> void deleteHttpByString(Context context,
                                       String url,
                                       HashMap<String, Object> params,
                                       JrStringCallback jrStringCallback,
                                       E extras) {
        deleteHttpByString(context, url, null, params, jrStringCallback, extras);
    }

    /**
     * delete请求回调string类型
     *
     * @param context          上下文
     * @param url              请求地址
     * @param params           请求参数可为空
     * @param jrStringCallback 请求回调
     */
    public <E> void deleteHttpByString(Context context,
                                       String url,
                                       HashMap<String, String> headerParams,
                                       HashMap<String, Object> params,
                                       final JrStringCallback<E> jrStringCallback,
                                       E extras) {
        try {
            DeleteRequest deleteRequest = OkGo.delete(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    deleteRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        deleteRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        deleteRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        deleteRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        deleteRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        deleteRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        deleteRequest.params(key, (Boolean) value);
                    }
                }
            }
            deleteRequest.execute(new JsonCallback<String, E>(String.class, extras) {
                @Override
                public void onSuccessful(String s, Response response, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onSuccess(s, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(String s, E extras) {
                    if (jrStringCallback != null) {
                        jrStringCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrStringCallback != null) {
                jrStringCallback.onAfter(extras);
            }
            Logger.L.error("delete http by string request error:", e);
        }
    }

    public <T, E> void deleteHttpByJson(Context context,
                                        String url,
                                        Class<T> dataClass,
                                        JrJsonCallback<T, E> jrJsonCallback) {
        deleteHttpByJson(context, url, dataClass, jrJsonCallback, null);
    }

    public <T, E> void deleteHttpByJson(Context context,
                                        String url,
                                        Class<T> dataClass,
                                        JrJsonCallback<T, E> jrJsonCallback,
                                        E extras) {
        deleteHttpByJson(context, url, null, dataClass, jrJsonCallback, extras);
    }

    public <T, E> void deleteHttpByJson(Context context,
                                        String url,
                                        HashMap<String, Object> params,
                                        Class<T> dataClass,
                                        JrJsonCallback<T, E> jrJsonCallback) {
        deleteHttpByJson(context, url, params, dataClass, jrJsonCallback, null);
    }

    public <T, E> void deleteHttpByJson(Context context,
                                        String url,
                                        HashMap<String, Object> params,
                                        Class<T> dataClass,
                                        JrJsonCallback<T, E> jrJsonCallback,
                                        E extras) {
        deleteHttpByJson(context, url, null, params, dataClass, jrJsonCallback, extras);
    }

    /**
     * delete请求回调json类型
     *
     * @param context        上下文
     * @param url            请求地址
     * @param params         请求参数可为空
     * @param dataClass      json解析class
     * @param jrJsonCallback 请求回调
     * @param <T>            解析类型
     */
    public <T, E> void deleteHttpByJson(Context context,
                                        String url,
                                        HashMap<String, String> headerParams,
                                        HashMap<String, Object> params,
                                        Class<T> dataClass,
                                        final JrJsonCallback<T, E> jrJsonCallback,
                                        E extras) {
        try {
            DeleteRequest deleteRequest = OkGo.delete(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    deleteRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        deleteRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        deleteRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        deleteRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        deleteRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        deleteRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        deleteRequest.params(key, (Boolean) value);
                    }
                }
            }
            deleteRequest.execute(new JsonCallback<T, E>(dataClass, extras) {
                @Override
                public void onSuccessful(T t, Response response, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onSuccess(t, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(T t, E extras) {
                    if (jrJsonCallback != null) {
                        jrJsonCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrJsonCallback != null) {
                jrJsonCallback.onAfter(extras);
            }
            Logger.L.error("delete http by json request error:", e);
        }
    }

    public <T, E> void deleteHttpByArray(Context context,
                                         String url,
                                         Class<T> dataClass,
                                         JrJsonArrayCallback<T, E> jrJsonArrayCallback) {
        deleteHttpByArray(context, url, dataClass, jrJsonArrayCallback, null);
    }

    public <T, E> void deleteHttpByArray(Context context,
                                         String url,
                                         Class<T> dataClass,
                                         JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                         E extras) {
        deleteHttpByArray(context, url, null, dataClass, jrJsonArrayCallback, extras);
    }

    public <T, E> void deleteHttpByArray(Context context,
                                         String url,
                                         HashMap<String, Object> params,
                                         Class<T> dataClass,
                                         JrJsonArrayCallback<T, E> jrJsonArrayCallback) {
        deleteHttpByArray(context, url, params, dataClass, jrJsonArrayCallback, null);
    }

    public <T, E> void deleteHttpByArray(Context context,
                                         String url,
                                         HashMap<String, Object> params,
                                         Class<T> dataClass,
                                         JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                         E extras) {
        deleteHttpByArray(context, url, null, params, dataClass, jrJsonArrayCallback, extras);
    }

    /**
     * delete请求回调json类型
     *
     * @param context             上下文
     * @param url                 请求地址
     * @param params              请求参数可为空
     * @param dataClass           json解析class
     * @param jrJsonArrayCallback 请求回调
     * @param <T>                 解析类型
     */
    public <T, E> void deleteHttpByArray(Context context,
                                         String url,
                                         HashMap<String, String> headerParams,
                                         HashMap<String, Object> params,
                                         Class<T> dataClass,
                                         final JrJsonArrayCallback<T, E> jrJsonArrayCallback,
                                         E extras) {
        try {
            DeleteRequest deleteRequest = OkGo.delete(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    deleteRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        deleteRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        deleteRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        deleteRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        deleteRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        deleteRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        deleteRequest.params(key, (Boolean) value);
                    }
                }
            }
            deleteRequest.execute(new JsonArrayCallback<T, E>(dataClass, extras) {
                @Override
                public void onSuccessful(List<T> t, Response response, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onSuccess(t, response, extras);
                    }
                }

                @Override
                public void onErrorCall(Response response, Exception e, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onError(response, e, extras);
                    }
                }

                @Override
                public void onAfterCall(List<T> t, E extras) {
                    if (jrJsonArrayCallback != null) {
                        jrJsonArrayCallback.onAfter(extras);
                    }
                }
            });
        } catch (Exception e) {
            if (jrJsonArrayCallback != null) {
                jrJsonArrayCallback.onAfter(extras);
            }
            Logger.L.error("delete http by json array request error:", e);
        }
    }

    public void uploadFile(Context context, String url, String fileKey, File file, JrUploadCallback jrUploadCallback) {
        uploadFile(context, url, fileKey, file, null, jrUploadCallback);
    }

    public void uploadFile(Context context, String url, String fileKey, File file, HashMap<String, Object> params, JrUploadCallback jrUploadCallback) {
        uploadFile(context, url, fileKey, file, null, params, jrUploadCallback);
    }

    /**
     * 文件上传
     *
     * @param context          上下文
     * @param url              请求地址
     * @param fileKey          文件key不能为空
     * @param file             上传文件不能为空
     * @param params           请求参数可为空
     * @param jrUploadCallback 请求回调
     */
    public void uploadFile(Context context, String url, String fileKey, File file, HashMap<String, String> headerParams, HashMap<String, Object> params, JrUploadCallback jrUploadCallback) {
        try {
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(fileKey) || file == null) {
                return;
            }
            HashMap<String, File> map = new HashMap<>();
            map.put(fileKey, file);
            uploadFileList(context, url, map, headerParams, params, jrUploadCallback);
        } catch (Exception e) {
            if (jrUploadCallback != null) {
                jrUploadCallback.onAfter("");
            }
            Logger.L.error("file upload error:", e);
        }
    }

    public void uploadFileList(Context context, String url, HashMap<String, File> files, JrUploadCallback jrUploadCallback) {
        uploadFileList(context, url, files, null, jrUploadCallback);
    }

    public void uploadFileList(Context context, String url, HashMap<String, File> files, HashMap<String, Object> params, JrUploadCallback jrUploadCallback) {
        uploadFileList(context, url, files, null, params, jrUploadCallback);
    }

    /**
     * 多文件上传
     *
     * @param context          网上下文
     * @param url              请求地址
     * @param files            文件集合
     * @param params           请求参数可为空
     * @param jrUploadCallback 请求回调
     */
    public void uploadFileList(Context context, String url, HashMap<String, File> files, HashMap<String, String> headerParams, HashMap<String, Object> params, final JrUploadCallback jrUploadCallback) {
        try {
            if (TextUtils.isEmpty(url) || files == null || !(files.size() > 0)) {
                return;
            }
            PostRequest postRequest = OkGo.post(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    postRequest.headers(key, value);
                }
            }
            //添加文件
            for (HashMap.Entry<String, File> entry : files.entrySet()) {
                //参数名
                String key = entry.getKey();
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                //文件
                File value = entry.getValue();
                if (value == null) {
                    continue;
                }
                postRequest.params(key, value);
            }
            //请求参数
            if (params != null && params.size() > 0) {
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
                        postRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        postRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        postRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        postRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        postRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        postRequest.params(key, (Boolean) value);
                    }
                }
            }

            postRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    if (jrUploadCallback != null) {
                        jrUploadCallback.onSuccess(s, response, "");
                    }
                }

                @Override
                public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                    if (jrUploadCallback != null) {
                        //progress默认为0-1,乘以100改为百分制
                        jrUploadCallback.upProgress(currentSize, totalSize, progress * 100, networkSpeed);
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    if (jrUploadCallback != null) {
                        jrUploadCallback.onError(response, e, "");
                    }
                }

                @Override
                public void onAfter(String s, Exception e) {
                    if (jrUploadCallback != null) {
                        jrUploadCallback.onAfter("");
                    }
                }
            });
        } catch (Exception e) {
            if (jrUploadCallback != null) {
                jrUploadCallback.onAfter("");
            }
            Logger.L.error("file list upload error:", e);
        }
    }

    public void downloadFile(Context context,
                             String url,
                             JrFileCallback jrFileCallback,
                             ResponseType responseType) {
        downloadFile(context, url, "", jrFileCallback, responseType);
    }

    public void downloadFile(Context context,
                             String url,
                             HashMap<String, Object> params,
                             JrFileCallback jrFileCallback,
                             ResponseType responseType) {
        downloadFile(context, url, "", params, jrFileCallback, responseType);
    }

    public void downloadFile(Context context,
                             String url,
                             String filePath,
                             JrFileCallback jrFileCallback,
                             ResponseType responseType) {
        downloadFile(context, url, "", filePath, jrFileCallback, responseType);
    }

    public void downloadFile(Context context,
                             String url,
                             String filePath,
                             HashMap<String, Object> params,
                             JrFileCallback jrFileCallback,
                             ResponseType responseType) {
        downloadFile(context, url, "", filePath, params, jrFileCallback, responseType);
    }

    public void downloadFile(Context context,
                             String url,
                             String fileName,
                             String filePath,
                             JrFileCallback jrFileCallback,
                             ResponseType responseType) {
        downloadFile(context, url, fileName, filePath, null, jrFileCallback, responseType);
    }

    public void downloadFile(Context context,
                             String url,
                             String fileName,
                             String filePath,
                             HashMap<String, Object> params,
                             JrFileCallback jrFileCallback,
                             ResponseType responseType) {
        downloadFile(context, url, fileName, filePath, null, params, jrFileCallback, FileLevelEnum.Normal, responseType);
    }

    /**
     * 文件下载
     *
     * @param context        上下文
     * @param url            请求地址
     * @param fileName       文件名称
     * @param filePath       文件路径
     * @param jrFileCallback 请求回调
     * @param fileLevelEnum  文件级别
     * @param responseType   文件类型
     */
    public void downloadFile(Context context,
                             String url,
                             String fileName,
                             String filePath,
                             HashMap<String, String> headerParams,
                             HashMap<String, Object> params,
                             final JrFileCallback jrFileCallback,
                             FileLevelEnum fileLevelEnum,
                             ResponseType responseType) {
        try {
            if (TextUtils.isEmpty(filePath)) {
                filePath = StorageUtils.getDownloadDir().getAbsolutePath();
            }
            if (TextUtils.isEmpty(fileName)) {
                fileName = GlobalUtils.getGuidNoConnect() + "." + responseType.name();
            }
            GetRequest getRequest = OkGo.get(url).tag(context);
            if (headerParams != null && headerParams.size() > 0) {
                for (HashMap.Entry<String, String> entry : headerParams.entrySet()) {
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
                    //添加头参数
                    getRequest.headers(key, value);
                }
            }
            if (params != null && params.size() > 0) {
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
                        getRequest.params(key, (Integer) value);
                    } else if (value instanceof Long) {
                        getRequest.params(key, (Long) value);
                    } else if (value instanceof String) {
                        getRequest.params(key, (String) value);
                    } else if (value instanceof Double) {
                        getRequest.params(key, (Double) value);
                    } else if (value instanceof Float) {
                        getRequest.params(key, (Float) value);
                    } else if (value instanceof Boolean) {
                        getRequest.params(key, (Boolean) value);
                    }
                }
            }
            getRequest.execute(new FileCallback(filePath, fileName) {
                @Override
                public void onSuccess(File file, Call call, Response response) {
                    if (jrFileCallback != null) {
                        jrFileCallback.onSuccess(file, response, "");
                    }
                }

                @Override
                public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                    if (jrFileCallback != null) {
                        //progress默认为0-1,乘以100改为百分制
                        jrFileCallback.downloadProgress(currentSize, totalSize, progress * 100, networkSpeed);
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    if (jrFileCallback != null) {
                        jrFileCallback.onError(response, e, "");
                    }
                }

                @Override
                public void onAfter(File file, Exception e) {
                    if (jrFileCallback != null) {
                        jrFileCallback.onAfter("");
                    }
                }
            });
        } catch (Exception e) {
            if (jrFileCallback != null) {
                jrFileCallback.onAfter("");
            }
            Logger.L.error("down load File error:", e);
        }
    }

    /**
     * 取消请求
     *
     * @param tag 请求的tag
     */

    public void cancelRequest(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 清除所有api缓存
     */
    public static void clearAllCache() {
        clearCache(ALL_CACHE_KEY);
    }

    /**
     * 清除包含cacheKey的api缓存
     *
     * @param cacheKey
     */
    public static void clearCache(String cacheKey) {
        try {
            if (!TextUtils.isEmpty(cacheKey)) {
                CacheManager cm = CacheManager.INSTANCE;
                if (TextUtils.equals(cacheKey, ALL_CACHE_KEY)) {
                    cm.clear();
                } else {
                    List<CacheEntity<Object>> all = cm.getAll();
                    for (CacheEntity<Object> objectCacheEntity : all) {
                        String key = objectCacheEntity.getKey();
                        if (!TextUtils.isEmpty(key) && key.contains(cacheKey)) {
                            cm.remove(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("clear api cache error:", e);
        }
    }
}
