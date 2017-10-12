package com.cloud.basicfun.provider;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/17
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class MatchItem {
    /**
     * uri
     */
    private String uri = "";
    /**
     * 匹配action
     */
    private String action = "";
    /**
     * 匹配成功返回码
     */
    private int code = 0;

    /**
     * 获取uri
     */
    public String getUri() {
        if (uri == null) {
            uri = "";
        }
        return uri;
    }

    /**
     * 设置uri
     *
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取匹配action
     */
    public String getAction() {
        if (action == null) {
            action = "";
        }
        return action;
    }

    /**
     * 设置匹配action
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 获取匹配成功返回码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置匹配成功返回码
     *
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }
}
