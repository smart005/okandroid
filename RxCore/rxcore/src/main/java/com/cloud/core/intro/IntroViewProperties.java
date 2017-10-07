package com.cloud.core.intro;

import android.content.res.ColorStateList;
import android.graphics.Color;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/12
 * @Description:基本属性
 * @Modifier:
 * @ModifyContent:
 */
public class IntroViewProperties {

    /**
     * 文本大小(单位sp,默认为12)
     */
    private int textSize = 12;
    /**
     * 文本颜色(默认颜色#4d4d4d或77,77,77)
     */
    private ColorStateList textColor = ColorStateList.valueOf(Color.rgb(77, 77, 77));
    /**
     * 标记文本大小(单位dp)
     */
    private int tagTextSize = 12;
    /**
     * 标记文本颜色(默认颜色#f24949或242,73,73)
     */
    private ColorStateList tagTextColor = ColorStateList.valueOf(Color.rgb(242, 73, 73));
    /**
     * 视图行间距（必须大于0,单位dp）
     */
    private float viewLineSpacing = 0;
    /**
     * 最多显示行数(小于0默认记为0;0表示无限制;)
     */
    private int lines = 0;
    /**
     * 最后一行显示字数
     */
    private int lastLineChars = 0;

    /**
     * @return 获取文本大小(单位dp)
     */
    public int getTextSize() {
        return textSize;
    }

    /**
     * 设置文本大小(单位dp)
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * @return 获取文本颜色
     */
    public ColorStateList getTextColor() {
        return textColor;
    }

    /**
     * 设置文本颜色
     *
     * @param textColor
     */
    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
    }

    /**
     * @return 获取标记文本大小(单位dp)
     */
    public int getTagTextSize() {
        return tagTextSize;
    }

    /**
     * 设置标记文本大小(单位dp)
     *
     * @param tagTextSize
     */
    public void setTagTextSize(int tagTextSize) {
        this.tagTextSize = tagTextSize;
    }

    /**
     * @return 获取标记文本颜色
     */
    public ColorStateList getTagTextColor() {
        return tagTextColor;
    }

    /**
     * 设置标记文本颜色
     *
     * @param tagTextColor
     */
    public void setTagTextColor(ColorStateList tagTextColor) {
        this.tagTextColor = tagTextColor;
    }

    /**
     * @return 获取视图行间距
     */
    public float getViewLineSpacing() {
        return viewLineSpacing;
    }

    /**
     * 设置视图行间距
     *
     * @param viewLineSpacing
     */
    public void setViewLineSpacing(float viewLineSpacing) {
        this.viewLineSpacing = viewLineSpacing;
    }

    /**
     * @return 获取最多显示行数
     */
    public int getLines() {
        return lines;
    }

    /**
     * 设置最多显示行数
     *
     * @param lines
     */
    public void setLines(int lines) {
        this.lines = lines;
    }

    /**
     * @return 获取最后一行显示字数
     */
    public int getLastLineChars() {
        return lastLineChars;
    }

    /**
     * 设置最后一行显示字数
     *
     * @param lastLineChars
     */
    public void setLastLineChars(int lastLineChars) {
        this.lastLineChars = lastLineChars;
    }
}
