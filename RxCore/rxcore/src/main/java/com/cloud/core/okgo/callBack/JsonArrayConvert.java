package com.cloud.core.okgo.callBack;

import com.lzy.okgo.convert.Converter;
import com.cloud.core.utils.JsonUtils;

import java.util.List;

import okhttp3.Response;


/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class JsonArrayConvert<T> implements Converter<List<T>> {

    private Class<T> dataClass;

    public JsonArrayConvert(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    @Override
    public List<T> convertSuccess(Response response) throws Exception {
        String json = response.body().string();
        //有数据类型，表示有data
        List<T> data = JsonUtils.parseArray(json, dataClass);
        response.close();
        return data;
    }
}
