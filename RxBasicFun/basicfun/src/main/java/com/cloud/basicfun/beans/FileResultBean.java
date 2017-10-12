package com.cloud.basicfun.beans;

import com.cloud.core.beans.BaseBean;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/19
 * @Description:文件返回属性
 * @Modifier:
 * @ModifyContent:
 */
public class FileResultBean extends BaseBean {

    /**
     * part path
     */
    private String url = "";
    /**
     * full path
     */
    private String fullUrl = "";

    /**
     * @return 获取part
     * path
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置part
     * path
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return 获取full
     * path
     */
    public String getFullUrl() {
        return fullUrl;
    }

    /**
     * 设置full
     * path
     *
     * @param fullUrl
     */
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }
}
