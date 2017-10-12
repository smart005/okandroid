package com.cloud.basicfun.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/17
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public enum ListStateEnum {
    /**
     * 刷新
     */
    Refresh("408540094");

    private String value = "";

    ListStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
