package com.cloud.core.beans;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-12-31 下午2:19:51
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseUpwardDialogParams {

    private Context context = null;
    private int layoutResid = 0;
    private View contentView = null;
    /**
     * 在窗口之外touch是否关闭当前dialog
     */
    private boolean isCancelOutLayout = false;
    /**
     * 底部进入与退出动画
     */
    private int animBottom = 0;
    /**
     * 内容类型
     */
    private int contenttype = 0;
    /**
     * 菜单项
     */
    private List<MenuDialogItem> menuItems = new ArrayList<MenuDialogItem>();
    /**
     * (菜单)第一项背景
     */
    private int firstItemBackgroundResid = 0;
    /**
     * (菜单)项背景
     */
    private int itemBackgroundrResid = 0;
    /**
     * (菜单)最后一项背景
     */
    private int endItemBackgroundResid = 0;
    /**
     * 分隔线颜色
     */
    private int splitlinecolor = 0;
    /**
     * (菜单)背景
     */
    private int menuBackgroundResid = 0;
    /**
     * (菜单)标题
     */
    private String title = "";
    /**
     * 面板背景颜色值(兼容部分机型),默认为透明
     */
    private int panelBackgroundColor = android.R.color.transparent;
    /**
     * 面板y轴上距离父控件的距离
     */
    private int offsetY = 0;
    /**
     * 标题颜色
     */
    private int titleColor = 0;
    /**
     * 取消文本颜色
     */
    private int cancelTextColor = 0;

    /**
     * @return 获取context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param 设置context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return 获取layoutResid
     */
    public int getLayoutResid() {
        return layoutResid;
    }

    /**
     * @param 设置layoutResid
     */
    public void setLayoutResid(int layoutResid) {
        this.layoutResid = layoutResid;
    }

    /**
     * @return 获取contentView
     */
    public View getContentView() {
        return contentView;
    }

    /**
     * @param 设置contentView
     */
    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    /**
     * @return 获取底部进入与退出动画
     */
    public int getAnimBottom() {
        return animBottom;
    }

    /**
     * @param 设置底部进入与退出动画
     */
    public void setAnimBottom(int animBottom) {
        this.animBottom = animBottom;
    }

    /**
     * @return 获取在窗口之外touch是否关闭当前dialog
     */
    public boolean isCancelOutLayout() {
        return isCancelOutLayout;
    }

    /**
     * @param 设置在窗口之外touch是否关闭当前dialog
     */
    public void setCancelOutLayout(boolean isCancelOutLayout) {
        this.isCancelOutLayout = isCancelOutLayout;
    }

    /**
     * @return 获取内容类型
     */
    public int getContenttype() {
        return contenttype;
    }

    /**
     * @param 设置内容类型
     */
    public void setContenttype(int contenttype) {
        this.contenttype = contenttype;
    }

    /**
     * @return 获取菜单项
     */
    public List<MenuDialogItem> getMenuItems() {
        return menuItems;
    }

    /**
     * @param 设置菜单项
     */
    public void setMenuItems(List<MenuDialogItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * @return 获取(菜单)第一项背景
     */
    public int getFirstItemBackgroundResid() {
        return firstItemBackgroundResid;
    }

    /**
     * @param 设置 (菜单)第一项背景
     */
    public void setFirstItemBackgroundResid(int firstItemBackgroundResid) {
        this.firstItemBackgroundResid = firstItemBackgroundResid;
    }

    /**
     * @return 获取(菜单)项背景
     */
    public int getItemBackgroundrResid() {
        return itemBackgroundrResid;
    }

    /**
     * @param 设置 (菜单)项背景
     */
    public void setItemBackgroundrResid(int itemBackgroundrResid) {
        this.itemBackgroundrResid = itemBackgroundrResid;
    }

    /**
     * @return 获取(菜单)最后一项背景
     */
    public int getEndItemBackgroundResid() {
        return endItemBackgroundResid;
    }

    /**
     * @param 设置 (菜单)最后一项背景
     */
    public void setEndItemBackgroundResid(int endItemBackgroundResid) {
        this.endItemBackgroundResid = endItemBackgroundResid;
    }

    /**
     * @return 获取分隔线颜色
     */
    public int getSplitlinecolor() {
        return splitlinecolor;
    }

    /**
     * @param 设置分隔线颜色
     */
    public void setSplitlinecolor(int splitlinecolor) {
        this.splitlinecolor = splitlinecolor;
    }

    /**
     * @return 获取(菜单)背景
     */
    public int getMenuBackgroundResid() {
        return menuBackgroundResid;
    }

    /**
     * @param 设置 (菜单)背景
     */
    public void setMenuBackgroundResid(int menuBackgroundResid) {
        this.menuBackgroundResid = menuBackgroundResid;
    }

    /**
     * @return 获取(菜单)标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param 设置 (菜单)标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 获取面板背景颜色值(兼容部分机型)默认为透明
     */
    public int getPanelBackgroundColor() {
        return panelBackgroundColor;
    }

    /**
     * @param 设置面板背景颜色值 (兼容部分机型)默认为透明
     */
    public void setPanelBackgroundColor(int panelBackgroundColor) {
        this.panelBackgroundColor = panelBackgroundColor;
    }

    /**
     * @return 获取面板y轴上离父控件的距离
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * @param 设置面板y轴上离父控件的距离
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getCancelTextColor() {
        return cancelTextColor;
    }

    public void setCancelTextColor(int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
    }
}
