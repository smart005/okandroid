package com.cloud.core.beans;

import android.text.TextUtils;

import com.cloud.core.utils.GlobalUtils;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-1-19 上午10:02:09
 * @Description: 下拉列表实体项
 * @Modifier:
 * @ModifyContent:
 */
public class DropListItem {

    /**
     * 数据id
     */
    private String id = "";

    /**
     * 显示名称
     */
    private String displayName = "";

    /**
     * int 值
     */
    private int intValue = 0;

    /**
     * 字符串值
     */
    private String stringValue = "";

    /**
     * 对象值
     */
    private Object objValue = null;

    /**
     * 是否选中
     */
    private boolean isChk = false;

    public DropListItem() {

    }

    public DropListItem(String id, String displayName, int intValue,
                        String stringValue, Object objValue, boolean isChk) {
        this.id = id;
        this.displayName = displayName;
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.objValue = objValue;
        this.isChk = isChk;
    }

    public DropListItem(String id, String displayName, int intValue,
                        String stringValue, Object objValue) {
        this(id, displayName, intValue, stringValue, objValue, false);
    }

    public DropListItem(String displayName, int intValue, String stringValue,
                        Object objValue) {
        this("", displayName, intValue, stringValue, objValue);
    }

    public DropListItem(String displayName, int intValue) {
        this(displayName, intValue, "", null);
    }

    public DropListItem(String displayName, String stringValue) {
        this(displayName, 0, stringValue, null);
    }

    public DropListItem(String displayName, Object objValue) {
        this(displayName, 0, "", objValue);
    }

    /**
     * @return 获取数据id
     */
    public String getId() {
        return TextUtils.isEmpty(id) ? GlobalUtils.getGuidNoConnect() : id;
    }

    /**
     * @param 设置数据id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 获取显示名称
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param 设置显示名称
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return 获取int值
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * @param 设置int值
     */
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    /**
     * @return 获取字符串值
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * @param 设置字符串值
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * @return 获取对象值
     */
    public Object getObjValue() {
        return objValue;
    }

    /**
     * @param 设置对象值
     */
    public void setObjValue(Object objValue) {
        this.objValue = objValue;
    }

    /**
     * @return 获取是否选中
     */
    public boolean isChk() {
        return isChk;
    }

    /**
     * @param 设置是否选中
     */
    public void setChk(boolean isChk) {
        this.isChk = isChk;
    }
}
