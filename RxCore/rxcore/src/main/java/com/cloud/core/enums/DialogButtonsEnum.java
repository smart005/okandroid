package com.cloud.core.enums;

public enum DialogButtonsEnum {
    None(-1, ""),
    /**
     * 是/否
     */
    YesNo(0, "是/否"),
    /**
     * 确定取消
     */
    ConfirmCancel(1, "确定取消"),
    /**
     * 确 定
     */
    Confirm(2, "确  定"),
    /**
     * 自定义
     */
    Custom(3, "自定义"),
    /**
     * 取消登录/重新登录
     */
    CancelLoginReLogin(4, "取消登录/重新登录");

    // 枚举值
    private int value = 0;
    // 描述值
    private String des = "";

    private DialogButtonsEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

    // 获取值
    public int getValue() {
        return this.value;
    }

    // 设置值
    public void setValue(int value) {
        this.value = value;
    }

    // 获取描述
    public String getDes() {
        return this.des;
    }

    // 设置描述
    public void setDes(String des) {
        this.des = des;
    }

    // 根据枚举值获取对应的枚举
    public static final DialogButtonsEnum getEnumByValue(int value) {
        DialogButtonsEnum currEnum = null;
        for (DialogButtonsEnum e : DialogButtonsEnum.values()) {
            if (e.getValue() == value) {
                currEnum = e;
                break;
            }
        }
        return currEnum;
    }
}
