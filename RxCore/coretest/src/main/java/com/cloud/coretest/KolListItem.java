package com.cloud.coretest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class KolListItem {
    /**
     * 大咖说id
     */
    private int id = 0;
    /**
     * 用户id
     */
    private int userId = 0;
    /**
     * 标题
     */
    private String title = "";
    /**
     * 创建时间
     */
    private long createDate = 0;
    /**
     * 昵称
     */
    private String nickname = "";
    /**
     * 头像
     */
    private String litpic = "";
    /**
     * 配置
     */
    private int isConfig = 0;
    /**
     * 位置
     */
    private int position = 0;
    /**
     * vip状态
     */
    private int vipStatus = 0;
    /**
     * 回复数
     */
    private int replyCount = 0;
    /**
     * 展现图片样式 1 单图 2大图 3多图
     */
    private int showImgType = 0;
    /**
     * 大咖说标识
     * 0：无；1：视频；2：音频；3视频、音频都有
     */
    private int videoAudioSign = 0;
    /**
     * 点击数
     */
    private int clickNumber = 0;
    /**
     * 大咖说图片
     */
    private String financeImage = "";
    private List<ConfigsItem> configs;

    /**
     * 获取位置
     */
    public int getPosition() {
        return position;
    }

    /**
     * 设置位置
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 获取配置
     */
    public int getIsConfig() {
        return isConfig;
    }

    /**
     * 设置配置
     *
     * @param isConfig
     */
    public void setIsConfig(int isConfig) {
        this.isConfig = isConfig;
    }

    public List<ConfigsItem> getConfigs() {
        if (configs == null) {
            configs = new ArrayList<>();
        }
        return configs;
    }

    public void setConfigs(List<ConfigsItem> configs) {
        this.configs = configs;
    }

    /**
     * 获取展现图片样式
     * 1
     * 单图
     * 2大图
     * 3多图
     */
    public int getShowImgType() {
        return showImgType;
    }

    /**
     * 设置展现图片样式
     * 1
     * 单图
     * 2大图
     * 3多图
     *
     * @param showImgType
     */
    public void setShowImgType(int showImgType) {
        this.showImgType = showImgType;
    }

    /**
     * 获取大咖说id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置大咖说id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取用户id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取标题
     */
    public String getTitle() {
        if (title == null) {
            title = "";
        }
        return title;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取创建时间
     */
    public long getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate
     */
    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取昵称
     */
    public String getNickname() {
        if (nickname == null) {
            nickname = "";
        }
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取头像
     */
    public String getLitpic() {
        if (litpic == null) {
            litpic = "";
        }
        return litpic;
    }

    /**
     * 设置头像
     *
     * @param litpic
     */
    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    /**
     * 获取vip状态
     */
    public int getVipStatus() {
        return vipStatus;
    }

    /**
     * 设置vip状态
     *
     * @param vipStatus
     */
    public void setVipStatus(int vipStatus) {
        this.vipStatus = vipStatus;
    }

    /**
     * 获取回复数
     */
    public int getReplyCount() {
        return replyCount;
    }

    /**
     * 设置回复数
     *
     * @param replyCount
     */
    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * 获取大咖说标识
     * 0:无;1:视频;2:音频;3视频.音频都有
     */
    public int getVideoAudioSign() {
        return videoAudioSign;
    }

    /**
     * 设置大咖说标识
     * 0:无;1:视频;2:音频;3视频.音频都有
     *
     * @param videoAudioSign
     */
    public void setVideoAudioSign(int videoAudioSign) {
        this.videoAudioSign = videoAudioSign;
    }

    /**
     * 获取点击数
     */
    public int getClickNumber() {
        return clickNumber;
    }

    /**
     * 设置点击数
     *
     * @param clickNumber
     */
    public void setClickNumber(int clickNumber) {
        this.clickNumber = clickNumber;
    }

    /**
     * 获取大咖说图片
     */
    public String getFinanceImage() {
        if (financeImage == null) {
            financeImage = "";
        }
        return financeImage;
    }

    /**
     * 设置大咖说图片
     *
     * @param financeImage
     */
    public void setFinanceImage(String financeImage) {
        this.financeImage = financeImage;
    }
}