package com.cloud.core.enums;

public enum DialogButtonEnum {
    /**
     * 是
     */
    Yes("988836739", "是"),
    /**
     * 否
     */
    No("1238183772", "否"),
    /**
     * 确定
     */
    Confirm("711756514", "确  定"),
    /**
     * 取消
     */
    Cancel("1598871971", "取  消"),
    /**
     * 取消登录
     */
    CancelLogin("1908013481", "取消登录"),
    /**
     * 重新登录
     */
    ReLogin("1974988507", "重新登录");

    // 枚举值
    private String value = "";
    // 描述值
    private String des = "";

    private DialogButtonEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    // 获取值
    public String getValue() {
        return this.value;
    }

    // 设置值
    public void setValue(String value) {
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
    public static final DialogButtonEnum getEnumByValue(String value) {
        DialogButtonEnum currEnum = null;
        for (DialogButtonEnum e : DialogButtonEnum.values()) {
            if (e.getValue() == value) {
                currEnum = e;
                break;
            }
        }
        return currEnum;
    }
}