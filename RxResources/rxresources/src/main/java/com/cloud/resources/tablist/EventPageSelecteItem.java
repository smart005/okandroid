package com.cloud.resources.tablist;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/10
 * @Description:event bus 分页选择项
 * @Modifier:
 * @ModifyContent:
 */
public class EventPageSelecteItem {
    /**
     * 索引
     */
    private int position = 0;
    /**
     * event key
     */
    private String key = "";

    /**
     * 获取索引
     */
    public int getPosition() {
        return position;
    }

    /**
     * 设置索引
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 获取event
     * key
     */
    public String getKey() {
        if (key == null) {
            key = "";
        }
        return key;
    }

    /**
     * 设置event
     * key
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
}
