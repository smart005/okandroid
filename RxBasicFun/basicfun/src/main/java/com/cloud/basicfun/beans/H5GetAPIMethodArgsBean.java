package com.cloud.basicfun.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/1/3
 * @Description:H5获取API方法参数
 * @Modifier:
 * @ModifyContent:
 */
public class H5GetAPIMethodArgsBean {
    /**
     * api名称
     */
    private String apiName = "";
    /**
     * 区分那个接口回调
     */
    private String target = "";
    /**
     * api参数列表
     */
    private List<ArgFieldItem> args = null;

    /**
     * 获取api名称
     */
    public String getApiName() {
        if (apiName == null) {
            apiName = "";
        }
        return apiName;
    }

    /**
     * 设置api名称
     *
     * @param apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * 获取区分那个接口回调
     */
    public String getTarget() {
        if (target == null) {
            target = "";
        }
        return target;
    }

    /**
     * 设置区分那个接口回调
     *
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 获取api参数列表
     */
    public List<ArgFieldItem> getArgs() {
        if (args == null) {
            args = new ArrayList<ArgFieldItem>();
        }
        return args;
    }

    /**
     * 设置api参数列表
     *
     * @param args
     */
    public void setArgs(List<ArgFieldItem> args) {
        this.args = args;
    }
}
