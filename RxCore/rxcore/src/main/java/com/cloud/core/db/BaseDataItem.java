package com.cloud.core.db;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import com.cloud.core.ObjectJudge;
import com.cloud.core.db.annotation.Column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/28
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseDataItem {

    private String currAttrName = "";

    protected String getInvokingAttrName() {
        Throwable throwable = new Throwable();
        if (throwable == null) {
            return "";
        }
        StackTraceElement[] stacks = throwable.getStackTrace();
        if (ObjectJudge.isNullOrEmpty(stacks)) {
            return "";
        }
        String[] fms = {"getInvokingAttrName", "getForKey"};
        List<String> fmslst = Arrays.asList(fms);
        String methodName = "";
        for (StackTraceElement stack : stacks) {
            if (fmslst.contains(stack.getMethodName())) {
                continue;
            } else {
                methodName = stack.getMethodName();
                break;
            }
        }
        return methodName;
    }

    protected void setCurrAttrName(String currAttrName) {
        this.currAttrName = currAttrName;
    }

    public String getForKey(Object getMethod, boolean isDbConfig) {
        if (TextUtils.isEmpty(currAttrName) || currAttrName.length() <= 3) {
            return "";
        }
        String prefix = currAttrName.substring(0, 3).toLowerCase();
        if (!TextUtils.equals(prefix, "get")) {
            return "";
        }
        String name = currAttrName.substring(3);
        if (name.length() > 1) {
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        } else {
            name = name.substring(0, 1).toLowerCase();
        }
        if (isDbConfig) {
            Field[] declaredFields = this.getClass().getDeclaredFields();
            if (ObjectJudge.isNullOrEmpty(declaredFields)) {
                return "";
            }
            Field field = null;
            for (Field declaredField : declaredFields) {
                if (TextUtils.equals(declaredField.getName(), name)) {
                    field = declaredField;
                    break;
                }
            }
            if (field == null) {
                return "";
            }
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (ObjectJudge.isNullOrEmpty(annotations)) {
                return "";
            }
            Column annotation = (Column) annotations[0];
            if (annotation == null) {
                return "";
            }
            return annotation.name();
        } else {
            return name;
        }
    }

    public String getForKey(Object getMethod) {
        return getForKey(getMethod, true);
    }
}
