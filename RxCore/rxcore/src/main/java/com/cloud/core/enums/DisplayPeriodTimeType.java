package com.cloud.core.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 上午8:44:24
 * @Description: 显示时间段类型
 * @Modifier:
 * @ModifyContent:
 */
public enum DisplayPeriodTimeType {
    /**
     * 无
     */
    None(""),
    /**
     * 月-日 时-分
     */
    MMDDHHMM("MM-dd HH:mm");

    private String value = "";

    private DisplayPeriodTimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
