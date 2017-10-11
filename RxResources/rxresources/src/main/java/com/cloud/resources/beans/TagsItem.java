package com.cloud.resources.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/28
 * @Description:平台标签项
 * @Modifier:
 * @ModifyContent:
 */
public class TagsItem {

    /**
     * 获取获取标签id
     */
    private int id = 0;

    /**
     * 获取获取标签名称
     */
    private String name = "";

    /**
     * 获取获取key
     */
    private String key = "";

    /**
     * 获取获取标签值
     */
    private String value = "";

    /**
     * 获取获取标签logo
     */
    private String logo = "";

    /**
     * 获取获取标签类型
     */
    private int type = 0;

    /**
     * 获取获取是否启用
     */
    private int isEnabled = 0;

    /**
     * 获取数量(评论)
     */
    public int num = 0;

    /**
     * 获取获取创建时间
     */
    private long createDate = 0;

    /**
     * 获取获取修改时间
     */
    private long modifyDate = 0;

    /**
     * 是否选中
     */
    private boolean isChk = false;

    public TagsItem() {

    }

    public TagsItem(int id, String name, boolean isChk) {
        this.id = id;
        this.name = name;
        this.isChk = isChk;
    }

    public TagsItem(int id, String name) {
        this(id, name, false);
    }

    /**
     * 获取获取标签id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置获取标签id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取获取标签名称
     */
    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    /**
     * 设置获取标签名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取获取key
     */
    public String getKey() {
        if (key == null) {
            key = "";
        }
        return key;
    }

    /**
     * 设置获取key
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取获取标签值
     */
    public String getValue() {
        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * 设置获取标签值
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取获取标签logo
     */
    public String getLogo() {
        if (logo == null) {
            logo = "";
        }
        return logo;
    }

    /**
     * 设置获取标签logo
     *
     * @param logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取获取标签类型
     */
    public int getType() {
        return type;
    }

    /**
     * 设置获取标签类型
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取获取是否启用
     */
    public int getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置获取是否启用
     *
     * @param isEnabled
     */
    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取数量(评论)
     */
    public int getNum() {
        return num;
    }

    /**
     * 设置数量(评论)
     *
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 获取获取创建时间
     */
    public long getCreateDate() {
        return createDate;
    }

    /**
     * 设置获取创建时间
     *
     * @param createDate
     */
    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取获取修改时间
     */
    public long getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置获取修改时间
     *
     * @param modifyDate
     */
    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 获取是否选中
     */
    public boolean getIsChk() {
        return isChk;
    }

    /**
     * 设置是否选中
     *
     * @param isChk
     */
    public void setIsChk(boolean isChk) {
        this.isChk = isChk;
    }
}