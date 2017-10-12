package com.cloud.basicfun.beans;

import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/9/27
 * @Description:省市区item
 * @Modifier:
 * @ModifyContent:
 */
public class AddressItem {
    /**
     * 地址id
     */
    private String id = "";
    /**
     * 地址名称
     */
    private String name = "";
    /**
     * 是否选中
     */
    private boolean isChk = false;

    /**
     * 子节点
     */
    private List<AddressItem> children = null;

    public AddressItem() {

    }

    public AddressItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChk() {
        return isChk;
    }

    public void setChk(boolean chk) {
        isChk = chk;
    }

    public List<AddressItem> getChildren() {
        return children;
    }

    public void setChildren(List<AddressItem> children) {
        this.children = children;
    }
}
