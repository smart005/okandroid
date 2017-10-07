package com.cloud.core.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/5
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public enum ContextMenuIType {
    /**
     * 全选
     */
    AllSelect(16908319),
    /**
     * 选择
     */
    Select(16908328),
    /**
     * 剪贴
     */
    Cut(16908320),
    /**
     * 复制
     */
    Copy(16908321),
    /**
     * 粘贴
     */
    Paste(16908322),
    /**
     * 输入法
     */
    InputMethod(16908324);

    private int value = 0;

    private ContextMenuIType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
