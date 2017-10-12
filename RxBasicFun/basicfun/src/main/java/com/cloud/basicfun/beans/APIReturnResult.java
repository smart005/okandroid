package com.cloud.basicfun.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/1/4
 * @Description:api返回结果
 * @Modifier:
 * @ModifyContent:
 */
public class APIReturnResult {
    /**
     * api返回结果
     */
    private String response = "";
    /**
     * 区分那个接口回调
     */
    private String target = "";

    /**
     * 获取api返回结果
     */
    public String getResponse() {
        if (response == null) {
            response = "";
        }
        return response;
    }

    /**
     * 设置api返回结果
     *
     * @param response
     */
    public void setResponse(String response) {
        this.response = response;
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
}
