package com.cloud.core.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/1
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public enum EncryptType {
    /**
     * des
     */
    DES("6ng94e"),
    /**
     * 3des
     */
    ThreeDES("8lyefz");

    private String value = "";

    private EncryptType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
