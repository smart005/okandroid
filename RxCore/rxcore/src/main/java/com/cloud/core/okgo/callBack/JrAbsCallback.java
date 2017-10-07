package com.cloud.core.okgo.callBack;

import okhttp3.Response;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public abstract class JrAbsCallback<T, E> {

    private E extras = null;

    public abstract void onSuccess(T t, Response response, E extras);

    public void onError(Response response, Exception e, E extras) {
    }

    public void onAfter(E extras) {
    }

    public JrAbsCallback(E extras) {
        this.extras = extras;
    }

    public JrAbsCallback() {
        this(null);
    }
}
