package com.cloud.basicfun.enums;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/1
 * @Description:上传文件类型
 * @Modifier:
 * @ModifyContent:
 */
public enum UpdateFileType {
    /**
     * 头像
     */
    Portrait(-1),
    /**
     * 图片
     */
    Images(-2),
    /**
     * 产品图片
     */
    Product(1),
    /**
     * 用户图片
     */
    User(2);

    private int value = 0;

    private UpdateFileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
