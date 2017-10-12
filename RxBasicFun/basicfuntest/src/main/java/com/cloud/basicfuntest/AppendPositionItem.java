package com.cloud.basicfuntest;

import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author chenghailei
 * @Email:maplelucy1991@163.com
 * @CreateTime:17/7/3
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "appendPosition")
public class AppendPositionItem extends BaseDataItem {
    @Column(name = "id", isId = true, isIndex = true)
    private int id;
    @Column(name = "position")
    private long position;

    /**
     *
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     */
    public long getPosition() {
        super.setCurrAttrName(super.getInvokingAttrName());
        return position;
    }

    /**
     *
     * @param position
     */
    public void setPosition(long position) {
        this.position = position;
    }

}
