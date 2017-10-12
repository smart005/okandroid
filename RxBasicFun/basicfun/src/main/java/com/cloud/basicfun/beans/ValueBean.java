package com.cloud.basicfun.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/3
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ValueBean {
    /**
     * 数值
     */
    private String value = "";

    /**
     * 获取数值
     */
    public String getValue() {
        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * 设置数值
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
