package com.cloud.core;

import java.lang.reflect.ParameterizedType;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/30
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class TypeClass<T> {
    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
