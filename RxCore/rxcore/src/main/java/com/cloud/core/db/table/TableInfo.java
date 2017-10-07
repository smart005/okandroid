package com.cloud.core.db.table;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/27
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class TableInfo {

    private int cid = 0;

    private String name = "";

    private String type = "";

    private int notnull = 0;

    private int pk = 0;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNotnull() {
        return notnull;
    }

    public void setNotnull(int notnull) {
        this.notnull = notnull;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }
}
