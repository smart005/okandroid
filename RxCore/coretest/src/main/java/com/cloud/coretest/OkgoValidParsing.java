package com.cloud.coretest;

import android.content.Context;

import com.cloud.core.Action;
import com.cloud.core.okgo.BaseOkgoValidParsing;
import com.cloud.core.okgo.BaseService;
import com.cloud.core.okgo.OkgoValidParam;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/8
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OkgoValidParsing {
    public static <T extends BaseService> OkgoValidParam check(Context context, T t) {
        return BaseOkgoValidParsing.getInstance().check(context, t, new Action<T>() {
            @Override
            public void execute(T t1) {

            }
        }, new Action() {
            @Override
            public void execute(Object t1) {

            }
        });
    }
}
