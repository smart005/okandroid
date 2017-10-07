package com.cloud.core.cache;

import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/28
 * @Description:缓存数据项
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "rx_cache_data")
public class CacheDataItem extends BaseDataItem {

    /**
     * 缓存键
     */
    @Column(name = "key", isId = true, autoGen = false, isIndex = true)
    private String key = "";
    /**
     * 缓存值
     */
    @Column(name = "value")
    private String value = "";
    /**
     * 到期时间(缓存时间+时间段)
     */
    @Column(name = "effective")
    private long effective = 0;
    /**
     * 状态
     */
    @Column(name = "flag")
    private boolean flag = false;
    /**
     * int值
     */
    @Column(name = "iniValue")
    private int iniValue = 0;
    /**
     * long值
     */
    @Column(name = "longValue")
    private long longValue = 0;

    /**
     * 获取缓存键
     */
    public String getKey() {
        if (key == null) {
            key = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return key;
    }

    /**
     * 设置缓存键
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取缓存值
     */
    public String getValue() {
        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * 设置缓存值
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取到期时间(缓存时间+时间段)
     */
    public long getEffective() {
        return effective;
    }

    /**
     * 设置到期时间(缓存时间+时间段)
     *
     * @param effective
     */
    public void setEffective(long effective) {
        this.effective = effective;
    }

    /**
     * 获取状态
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * 设置状态
     *
     * @param flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * 获取int值
     */
    public int getIniValue() {
        return iniValue;
    }

    /**
     * 设置int值
     *
     * @param iniValue
     */
    public void setIniValue(int iniValue) {
        this.iniValue = iniValue;
    }

    /**
     * 获取long值
     */
    public long getLongValue() {
        return longValue;
    }

    /**
     * 设置long值
     *
     * @param longValue
     */
    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }
}
