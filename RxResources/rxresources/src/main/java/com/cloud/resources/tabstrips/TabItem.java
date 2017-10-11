package com.cloud.resources.tabstrips;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/22
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class TabItem {
    /**
     * id
     */
    private String id = "";
    /**
     * item name
     */
    private CharSequence name;
    /**
     * 副标题
     */
    private CharSequence subtitle;

    public TabItem() {

    }

    public TabItem(String id, CharSequence name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获取id
     */
    public String getId() {
        if (id == null) {
            id = "";
        }
        return id;
    }

    /**
     * 设置id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取item
     * name
     */
    public CharSequence getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    /**
     * 设置item
     * name
     *
     * @param name
     */
    public void setName(CharSequence name) {
        this.name = name;
    }

    /**
     * 获取副标题
     */
    public CharSequence getSubtitle() {
        if (subtitle == null) {
            subtitle = "";
        }
        return subtitle;
    }

    /**
     * 设置副标题
     *
     * @param subtitle
     */
    public void setSubtitle(CharSequence subtitle) {
        this.subtitle = subtitle;
    }
}
