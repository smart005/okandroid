/**
 *
 */
package com.cloud.core.tagtext;

/**
 * @Author LIJINGHUAN
 * @Email:ljh0576123@163.com
 * @CreateTime:2016年2月29日 下午8:20:16
 * @Description: 标签项属性
 * @Modifier:
 * @ModifyContent:
 */
public class TagItemProperties {
    /**
     * 标签类型
     */
    TagItemType tagItemType = TagItemType.TextView;

    /**
     * 标签名称
     */
    private String tagName = "";
    /**
     * 标题背景资源
     */
    private int tagBackgroundResource = 0;

    public TagItemProperties() {
    }

    public TagItemProperties(String tagName) {
        this.tagName = tagName;
    }

    /**
     * @return 标签名称
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @param 标签名称 要设置的 tagName
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * 获取标题背景资源
     */
    public int getTagBackgroundResource() {
        return tagBackgroundResource;
    }

    /**
     * 设置标题背景资源
     *
     * @param tagBackgroundResource
     */
    public void setTagBackgroundResource(int tagBackgroundResource) {
        this.tagBackgroundResource = tagBackgroundResource;
    }

    public TagItemType getTagItemType() {
        return tagItemType;
    }

    public void setTagItemType(TagItemType tagItemType) {
        this.tagItemType = tagItemType;
    }
}
