package com.cloud.core.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/1
 * @Description:加密版本
 * @Modifier:
 * @ModifyContent:
 */
public enum EncryptVersion {

    V1("h34dot");

    private String value = "";

    EncryptVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
