package com.cloud.core.okgo.callBack;

import com.lzy.okgo.callback.AbsCallback;
import com.cloud.core.utils.JsonUtils;

import java.util.List;

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

public class JsonArrayCallback<T, E> extends AbsCallback<List<T>> {

    private Class<T> dataClass;
    private E extras = null;

    public JsonArrayCallback(Class<T> dataClass) {
        this(dataClass, null);
    }

    public JsonArrayCallback(Class<T> dataClass, E extras) {
        this.dataClass = dataClass;
        this.extras = extras;
    }

    @Override
    public List<T> convertSuccess(Response response) throws Exception {
        String json = response.body().string();
        //有数据类型，表示有data
        List<T> data = JsonUtils.parseArray(json, dataClass);
        response.close();
        return data;
    }

    @Override
    public void onSuccess(List<T> t, Call call, Response response) {
        onSuccessful(t, response, extras);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        onErrorCall(response, e, extras);
    }

    @Override
    public void onAfter(List<T> t, Exception e) {
        onAfterCall(t, extras);
    }

    public void onSuccessful(List<T> t, Response response, E extras) {

    }

    public void onErrorCall(Response response, Exception e, E extras) {

    }

    public void onAfterCall(List<T> t, E extras) {

    }
}
