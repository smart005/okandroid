package com.cloud.core.beans;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-30 上午10:41:34
 * @Description: 通知栏下载视图实体
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class NoticeDownloadViewEntity {
	/**
	 * 通知栏布局
	 */
	private int notificationLayout = 0;
	/**
	 * 图片
	 */
	private int icon = 0;
	/**
	 * 图片资源
	 */
	private int iconResid = 0;
	/**
	 * 主题文本
	 */
	private int subjectText = 0;
	/**
	 * 进度文本
	 */
	private int progressText = 0;
	/**
	 * 进度条
	 */
	private int progressPBar = 0;

	/**
	 * @return 获取通知栏布局
	 */
	public int getNotificationLayout() {
		return notificationLayout;
	}

	/**
	 * @param 设置通知栏布局
	 */
	public void setNotificationLayout(int notificationLayout) {
		this.notificationLayout = notificationLayout;
	}

	/**
	 * @return 获取图片
	 */
	public int getIcon() {
		return icon;
	}

	/**
	 * @param 设置图片
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	/**
	 * @return 获取图片资源
	 */
	public int getIconResid() {
		return iconResid;
	}

	/**
	 * @param 设置图片资源
	 */
	public void setIconResid(int iconResid) {
		this.iconResid = iconResid;
	}

	/**
	 * @return 获取主题文本
	 */
	public int getSubjectText() {
		return subjectText;
	}

	/**
	 * @param 设置主题文本
	 */
	public void setSubjectText(int subjectText) {
		this.subjectText = subjectText;
	}

	/**
	 * @return 获取进度文本
	 */
	public int getProgressText() {
		return progressText;
	}

	/**
	 * @param 设置进度文本
	 */
	public void setProgressText(int progressText) {
		this.progressText = progressText;
	}

	/**
	 * @return 获取进度条
	 */
	public int getProgressPBar() {
		return progressPBar;
	}

	/**
	 * @param 设置进度条
	 */
	public void setProgressPBar(int progressPBar) {
		this.progressPBar = progressPBar;
	}
}
