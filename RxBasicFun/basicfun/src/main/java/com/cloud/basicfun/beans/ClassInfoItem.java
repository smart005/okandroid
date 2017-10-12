package com.cloud.basicfun.beans;


import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/5/5
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "rx_classinfo")
public class ClassInfoItem extends BaseDataItem {
    /**
     * 类名
     */
    @Column(name = "className", isId = true, autoGen = false, isIndex = true)
    private String className = "";
    /**
     * 类全名
     */
    @Column(name = "classFullName", isIndex = true)
    private String classFullName = "";

    /**
     * 获取类名
     */
    public String getClassName() {
        if (className == null) {
            className = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return className;
    }

    /**
     * 设置类名
     *
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取类全名
     */
    public String getClassFullName() {
        if (classFullName == null) {
            classFullName = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return classFullName;
    }

    /**
     * 设置类全名
     *
     * @param classFullName
     */
    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }
}
