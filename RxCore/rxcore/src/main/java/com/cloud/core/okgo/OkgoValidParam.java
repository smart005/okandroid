package com.cloud.core.okgo;

import com.cloud.core.annotations.ApiCheckAnnotation;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/8
 * @Description:验证返回参数
 * @Modifier:
 * @ModifyContent:
 */
public class OkgoValidParam {
    private ApiCheckAnnotation apiCheckAnnotation = null;

    private boolean flag = false;

    private String cacheKey = "";

    public ApiCheckAnnotation getApiCheckAnnotation() {
        return apiCheckAnnotation;
    }

    public void setApiCheckAnnotation(ApiCheckAnnotation apiCheckAnnotation) {
        this.apiCheckAnnotation = apiCheckAnnotation;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
