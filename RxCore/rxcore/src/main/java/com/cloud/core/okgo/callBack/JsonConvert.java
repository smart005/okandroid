package com.cloud.core.okgo.callBack;

import com.cloud.core.logger.BuildConfig;
import com.cloud.core.logger.Logger;
import com.lzy.okgo.convert.Converter;
import com.cloud.core.utils.JsonUtils;

import okhttp3.Response;


/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class JsonConvert<T> implements Converter<T> {

    private Class<T> dataClass;

    public JsonConvert(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        String json = response.body().string();
        if (BuildConfig.DEBUG) {
            Logger.L.info(json);
        }
        //有数据类型，表示有data
        T data = JsonUtils.parseT(json, dataClass);
        response.close();
        return data;
    }
}
