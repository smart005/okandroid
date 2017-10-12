package com.cloud.basicfun.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/1/3
 * @Description:参数字段属性
 * @Modifier:
 * @ModifyContent:
 */
public class ArgFieldItem {
    /**
     * 字段名
     */
    private String fieldName = "";
    /**
     * 字段值
     */
    private String fieldValue = "";
    /**
     * 字段类型
     */
    private String fieldType = "";

    /**
     * 获取字段名
     */
    public String getFieldName() {
        if (fieldName == null) {
            fieldName = "";
        }
        return fieldName;
    }

    /**
     * 设置字段名
     *
     * @param fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 获取字段值
     */
    public String getFieldValue() {
        if (fieldValue == null) {
            fieldValue = "";
        }
        return fieldValue;
    }

    /**
     * 设置字段值
     *
     * @param fieldValue
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * 获取字段类型
     */
    public String getFieldType() {
        if (fieldType == null) {
            fieldType = "";
        }
        return fieldType;
    }

    /**
     * 设置字段类型
     *
     * @param fieldType
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
