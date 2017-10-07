package com.cloud.core.cache;

import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "rx_cache_module_flags")
public class ModuleFlagItem extends BaseDataItem {
    /**
     * 模块名
     */
    @Column(name = "moduleName", isId = true, autoGen = false, isIndex = true)
    private String moduleName = "";
    /**
     * 状态
     */
    @Column(name = "flag")
    private boolean flag = false;

    /**
     * 获取模块名
     */
    public String getModuleName() {
        if (moduleName == null) {
            moduleName = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return moduleName;
    }

    /**
     * 设置模块名
     *
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
}
