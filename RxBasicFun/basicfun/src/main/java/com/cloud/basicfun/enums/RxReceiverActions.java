package com.cloud.basicfun.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public enum RxReceiverActions {
    /**
     * 网络改变
     */
    NETWORK_CHANGE("589877701"),
    /**
     * 版本更新提醒
     */
    VERSION_UPDATE_REMIND("1988873551");

    private String value = "";

    RxReceiverActions(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
