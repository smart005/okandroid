package com.cloud.resources.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/16
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseImageItem {
    /**
     * 图片url
     */
    private String url = "";
    /**
     * 图片名称
     */
    private String name = "";

    /**
     * @return 获取图片url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置图片url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图片名称
     */
    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    /**
     * 设置图片名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
