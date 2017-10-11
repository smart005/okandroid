package com.cloud.resources.beans;

import android.graphics.Color;

import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-19 下午3:20:47
 * @Description: 流布局初始
 * @Modifier:
 * @ModifyContent:
 */
public class FlowLayoutInstance {
    /**
     * 容器背景
     */
    private int flowBackground = R.drawable.flow_edit_bg;
    /**
     * 是否启用编辑
     */
    private boolean isEnableEdit = false;
    /**
     * 项背景
     */
    private int flowItemBackground = 0;
    /**
     * 标题文本颜色
     */
    private int titleTextColor = Color.rgb(54, 147, 207);
    /**
     * 选中标题文本颜色
     */
    private int selectedTitleTextColor = Color.rgb(255, 255, 255);
    /**
     * 是否启用删除处理
     */
    private boolean isEnableDelete = true;
    /**
     * 是否启用选择处理
     */
    private boolean isEnableCheck = false;
    /**
     * 是否单选
     */
    private boolean isSingleChecked = true;
    /**
     * 选中项背景
     */
    private int selectedItemBackground = 0;
    /**
     * 左右间距
     */
    private int orSoSpacing = 0;
    /**
     * 上下间距
     */
    private int upAndDownSpacing = 0;
    /**
     * 视图项左边距
     */
    private int flowItemPaddingLeft = 0;
    /**
     * 视图项上边距
     */
    private int flowItemPaddingTop = 0;
    /**
     * 视图项右边距
     */
    private int flowItemPaddingRight = 0;
    /**
     * 视图项下边距
     */
    private int flowItemPaddingBottom = 0;
    /**
     * 项视图(指layout id)
     */
    private int flowItemContentView = 0;
    /**
     * 是否强制选中一个
     */
    private boolean isLeastSelected = false;

    public boolean isLeastSelected() {
        return isLeastSelected;
    }

    public void setLeastSelected(boolean leastSelected) {
        isLeastSelected = leastSelected;
    }

    /**
     * @return 获取容器背景
     */
    public int getFlowBackground() {
        return flowBackground;
    }

    /**
     * @param 设置容器背景
     */
    public void setFlowBackground(int flowBackground) {
        this.flowBackground = flowBackground;
    }

    /**
     * @return 获取是否启用编辑
     */
    public boolean isEnableEdit() {
        return isEnableEdit;
    }

    /**
     * @param 设置是否启用编辑
     */
    public void setEnableEdit(boolean isEnableEdit) {
        this.isEnableEdit = isEnableEdit;
    }

    /**
     * @return 获取项背景
     */
    public int getFlowItemBackground() {
        return flowItemBackground;
    }

    /**
     * @param 设置项背景
     */
    public void setFlowItemBackground(int flowItemBackground) {
        this.flowItemBackground = flowItemBackground;
    }

    /**
     * @return 获取标题文本颜色
     */
    public int getTitleTextColor() {
        return titleTextColor;
    }

    /**
     * @param 设置标题文本颜色
     */
    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    /**
     * @return 获取选中标题文本颜色
     */
    public int getSelectedTitleTextColor() {
        return selectedTitleTextColor;
    }

    /**
     * @param 设置选中标题文本颜色
     */
    public void setSelectedTitleTextColor(int selectedTitleTextColor) {
        this.selectedTitleTextColor = selectedTitleTextColor;
    }

    /**
     * @return 获取是否启用删除处理
     */
    public boolean isEnableDelete() {
        return isEnableDelete;
    }

    /**
     * @param 设置是否启用删除处理
     */
    public void setEnableDelete(boolean isEnableDelete) {
        this.isEnableDelete = isEnableDelete;
    }

    /**
     * @return 获取是否启用选择处理
     */
    public boolean isEnableCheck() {
        return isEnableCheck;
    }

    /**
     * @param 设置是否启用选择处理
     */
    public void setEnableCheck(boolean isEnableCheck) {
        this.isEnableCheck = isEnableCheck;
    }

    /**
     * @return 获取是否单选
     */
    public boolean isSingleChecked() {
        return isSingleChecked;
    }

    /**
     * @param 设置是否单选
     */
    public void setSingleChecked(boolean isSingleChecked) {
        this.isSingleChecked = isSingleChecked;
    }

    /**
     * @return 获取选中项背景
     */
    public int getSelectedItemBackground() {
        return selectedItemBackground;
    }

    /**
     * @param 设置选中项背景
     */
    public void setSelectedItemBackground(int selectedItemBackground) {
        this.selectedItemBackground = selectedItemBackground;
    }

    /**
     * @return 获取左右间距
     */
    public int getOrSoSpacing() {
        return orSoSpacing;
    }

    /**
     * @param 设置左右间距
     */
    public void setOrSoSpacing(int orSoSpacing) {
        this.orSoSpacing = orSoSpacing;
    }

    /**
     * @return 获取上下间距
     */
    public int getUpAndDownSpacing() {
        return upAndDownSpacing;
    }

    /**
     * @param 设置上下间距
     */
    public void setUpAndDownSpacing(int upAndDownSpacing) {
        this.upAndDownSpacing = upAndDownSpacing;
    }

    /**
     * @return 获取视图项左边距
     */
    public int getFlowItemPaddingLeft() {
        return flowItemPaddingLeft;
    }

    /**
     * @param 设置视图项左边距
     */
    public void setFlowItemPaddingLeft(int flowItemPaddingLeft) {
        this.flowItemPaddingLeft = flowItemPaddingLeft;
    }

    /**
     * @return 获取视图项上边距
     */
    public int getFlowItemPaddingTop() {
        return flowItemPaddingTop;
    }

    /**
     * @param 设置视图项上边距
     */
    public void setFlowItemPaddingTop(int flowItemPaddingTop) {
        this.flowItemPaddingTop = flowItemPaddingTop;
    }

    /**
     * @return 获取视图项右边距
     */
    public int getFlowItemPaddingRight() {
        return flowItemPaddingRight;
    }

    /**
     * @param 设置视图项右边距
     */
    public void setFlowItemPaddingRight(int flowItemPaddingRight) {
        this.flowItemPaddingRight = flowItemPaddingRight;
    }

    /**
     * @return 获取视图项下边距
     */
    public int getFlowItemPaddingBottom() {
        return flowItemPaddingBottom;
    }

    /**
     * @param 设置视图项下边距
     */
    public void setFlowItemPaddingBottom(int flowItemPaddingBottom) {
        this.flowItemPaddingBottom = flowItemPaddingBottom;
    }

    /**
     * @return 获取项视图(指layoutid)
     */
    public int getFlowItemContentView() {
        return flowItemContentView;
    }

    /**
     * @param 设置项视图 (指layoutid)
     */
    public void setFlowItemContentView(int flowItemContentView) {
        this.flowItemContentView = flowItemContentView;
    }
}