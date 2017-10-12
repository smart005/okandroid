package com.cloud.basicfun.notifications;

import com.umeng.socialize.media.UMImage;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/26
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class RxNotificationProperties {
    /**
     * 通知id
     */
    private int notificationId = 0;
    /**
     * 通知栏显示的图标
     */
    private int icon = 0;
    /**
     * 通知时在状态栏显示的内容
     */
    private String tickerText = "";
    /**
     * 通知标题
     */
    private String title = "";
    /**
     * 通知内容
     */
    private String text = "";
    /**
     * largeIcon
     */
    private UMImage largeIcon = null;

    /**
     * 通知栏显示的图标
     */
    public int getIcon() {
        return icon;
    }

    /**
     * 通知栏显示的图标
     *
     * @param icon
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * 获取通知时在状态栏显示的内容
     */
    public String getTickerText() {
        if (tickerText == null) {
            tickerText = "";
        }
        return tickerText;
    }

    /**
     * 设置通知时在状态栏显示的内容
     *
     * @param tickerText
     */
    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    /**
     * 获取通知标题
     */
    public String getTitle() {
        if (title == null) {
            title = "";
        }
        return title;
    }

    /**
     * 设置通知标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取通知内容
     */
    public String getText() {
        if (text == null) {
            text = "";
        }
        return text;
    }

    /**
     * 设置通知内容
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取largeIcon
     */
    public UMImage getLargeIcon() {
        return largeIcon;
    }

    /**
     * 设置largeIcon
     *
     * @param largeIcon
     */
    public void setLargeIcon(UMImage largeIcon) {
        this.largeIcon = largeIcon;
    }

    /**
     * 获取通知id
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * 设置通知id
     *
     * @param notificationId
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
