package com.cloud.basicfun.beans;

import android.os.Bundle;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/12
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class FlagEvent<T> {

    private boolean falg = false;

    /**
     * 是否刷新
     */
    private boolean isRefresh = false;
    /**
     * 是否通知数据集
     */
    private boolean isNoticeDataSet = falg;
    /**
     * 是否跳转页面
     */
    private boolean isGoPage = falg;
    /**
     * 页面Class
     */
    private Class<?> pageClass = null;
    /**
     * 数据对象
     */
    private Bundle bundle = null;
    /**
     * 是否回复
     */
    private boolean isReplay = false;
    /**
     * 数据
     */
    private T data = null;
    /**
     * 是否移除数据
     */
    private boolean isRemove = true;
    /**
     * 数据索引
     */
    private int dataPosition = 0;
    /**
     * 是否点赞
     */
    private boolean isLike = false;
    /**
     * 是否点赞处理
     */
    private boolean isLikeProcess = false;
    /**
     * 是否单点登录
     */
    private boolean isSSO = false;
    /**
     * 是否标记(视图)
     */
    private boolean isMark = false;
    /**
     * 键
     */
    private String key = "";

    /**
     *
     */
    public boolean getFalg() {
        return falg;
    }

    /**
     * @param falg
     */
    public void setFalg(boolean falg) {
        this.falg = falg;
    }


    /**
     * 获取是否刷新
     */
    public boolean getIsRefresh() {
        return isRefresh;
    }

    /**
     * 设置是否刷新
     *
     * @param isRefresh
     */
    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    /**
     * 获取是否通知数据集
     */
    public boolean getIsNoticeDataSet() {
        return isNoticeDataSet;
    }

    /**
     * 设置是否通知数据集
     *
     * @param isNoticeDataSet
     */
    public void setIsNoticeDataSet(boolean isNoticeDataSet) {
        this.isNoticeDataSet = isNoticeDataSet;
    }

    /**
     * 获取是否跳转页面
     */
    public boolean getIsGoPage() {
        return isGoPage;
    }

    /**
     * 设置是否跳转页面
     *
     * @param isGoPage
     */
    public void setIsGoPage(boolean isGoPage) {
        this.isGoPage = isGoPage;
    }

    /**
     * 获取页面Class
     */
    public Class<?> getPageClass() {
        return pageClass;
    }

    /**
     * 设置页面Class
     *
     * @param pageClass
     */
    public void setPageClass(Class<?> pageClass) {
        this.pageClass = pageClass;
    }

    /**
     * 获取数据对象
     */
    public Bundle getBundle() {
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    /**
     * 设置数据对象
     *
     * @param bundle
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * 获取是否回复
     */
    public boolean getIsReplay() {
        return isReplay;
    }

    /**
     * 设置是否回复
     *
     * @param isReplay
     */
    public void setIsReplay(boolean isReplay) {
        this.isReplay = isReplay;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public T getData() {
        return this.data;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取是否移除数据
     */
    public boolean getIsRemove() {
        return isRemove;
    }

    /**
     * 设置是否移除数据
     *
     * @param isRemove
     */
    public void setIsRemove(boolean isRemove) {
        this.isRemove = isRemove;
    }

    /**
     * 获取数据索引
     */
    public int getDataPosition() {
        return dataPosition;
    }

    /**
     * 设置数据索引
     *
     * @param dataPosition
     */
    public void setDataPosition(int dataPosition) {
        this.dataPosition = dataPosition;
    }

    /**
     * 获取是否赞p
     */
    public boolean getIsLike() {
        return isLike;
    }

    /**
     * 设置是否赞p
     *
     * @param isLike
     */
    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }


    /**
     * 获取是否点赞处理
     */
    public boolean getIsLikeProcess() {
        return isLikeProcess;
    }

    /**
     * 设置是否点赞处理
     *
     * @param isLikeProcess
     */
    public void setIsLikeProcess(boolean isLikeProcess) {
        this.isLikeProcess = isLikeProcess;
    }

    /**
     * 获取是否单点登录
     */
    public boolean getIsSSO() {
        return isSSO;
    }

    /**
     * 设置是否单点登录
     *
     * @param isSSO
     */
    public void setIsSSO(boolean isSSO) {
        this.isSSO = isSSO;
    }

    /**
     * 获取是否标记(视图)
     */
    public boolean getIsMark() {
        return isMark;
    }

    /**
     * 设置是否标记(视图)
     *
     * @param isMark
     */
    public void setIsMark(boolean isMark) {
        this.isMark = isMark;
    }

    /**
     * 获取键
     */
    public String getKey() {
        if (key == null) {
            key = "";
        }
        return key;
    }

    /**
     * 设置键
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
}
