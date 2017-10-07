package com.cloud.core.okgo.callBack;

import com.lzy.okgo.callback.AbsCallback;
import com.cloud.core.utils.JsonUtils;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class JsonCallback<T, E> extends AbsCallback<T> {

    private Class<T> dataClass;
    private E extras = null;

    public JsonCallback(Class<T> dataClass) {
        this(dataClass, null);
    }

    public JsonCallback(Class<T> dataClass, E extras) {
        this.dataClass = dataClass;
        this.extras = extras;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        String json = response.body().string();
        //有数据类型，表示有data
        T data = JsonUtils.parseT(json, dataClass);
        response.close();
        return data;
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {
        onSuccessful(t, response, extras);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        onErrorCall(response, e, extras);
    }

    @Override
    public void onAfter(T t, Exception e) {
        onAfterCall(t, extras);
    }

    public void onSuccessful(T t, Response response, E extras) {

    }

    public void onErrorCall(Response response, Exception e, E extras) {

    }

    public void onAfterCall(T t, E extras) {

    }
}
