package com.cloud.core.beans;

import android.content.Context;

import com.cloud.core.enums.ApkDownloadType;


/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-28 下午6:47:48
 * @Description: 初始化更新服务实体
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class InstanceUpdateServiceInfoEntity {
	/**
	 * 上下文
	 */
	private Context context = null;
	/**
	 * 自动更新时是否显示更新提醒(true表示自动更新时显示,否则不显示)
	 */
	private boolean isDisplayUpdateRemindForAutoUpdate = false;
	/**
	 * 是否自动检测更新
	 */
	private boolean isAutoUpdate = true;
	/**
	 * 更新信息请求地址
	 */
	private String updateInfoUrl = "";
	/**
	 * 是否显示检测更新提示(即更新之前是否最新版或正在更新字样提示)
	 */
	private boolean isDisplayCheckUpdatePrompt = false;
	/**
	 * 遮罩加载中窗口主题
	 */
	private int maskLoadingTheme = 0;
	/**
	 * 遮罩加载中窗口背景
	 */
	private int maskLoadingBackground = 0;
	/**
	 * 遮罩加载中窗口动画
	 */
	private int maskLoadingAnimation = 0;
	/**
	 * 应用程序版本号
	 */
	private int versionCode = 0;
	/**
	 * 对话框分隔线背景
	 */
	private int dialogSplitLineBackground = 0;
	/**
	 * 对话框背景
	 */
	private int dialogBackground = 0;
	/**
	 * 对话框按钮背景
	 */
	private int dialogButtonsBackground = 0;
	/**
	 * 对话框按钮文本颜色
	 */
	private int dialogButtonTextColor = 0;
	/**
	 * 对话框关闭按钮背景
	 */
	private int dialogCloseButtonBackground = 0;
	/**
	 * 对话框下载进度布局
	 */
	private int dialogDownloadProgressLayout = 0;
	/**
	 * apk下载方式
	 */
	private ApkDownloadType downloadType = ApkDownloadType.NOTIFICATION;
	/**
	 * 用于远程更新时传进来的相关信息
	 */
	private ApkInfo apkInfo = new ApkInfo();
	/**
	 * 通知栏下载视图实体
	 */
	private NoticeDownloadViewEntity noticeDownloadViewEntity = new NoticeDownloadViewEntity();
	/**
	 * 检查更新提示文本
	 */
	private String checkUpdatePromptText = "";
	/**
	 * 没有网络提示文本
	 */
	private String noNetworkPromptText = "";
	/**
	 * 最后版本提示文本
	 */
	private String lastVersionPromptText = "";

	/**
	 * @return 获取上下文
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param 设置上下文
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return 获取自动更新时是否显示更新提醒
	 */
	public boolean isDisplayUpdateRemindForAutoUpdate() {
		return isDisplayUpdateRemindForAutoUpdate;
	}

	/**
	 * @param 设置自动更新时是否显示更新提醒
	 */
	public void setDisplayUpdateRemindForAutoUpdate(
			boolean isDisplayUpdateRemindForAutoUpdate) {
		this.isDisplayUpdateRemindForAutoUpdate = isDisplayUpdateRemindForAutoUpdate;
	}

	/**
	 * @return 获取是否自动检测更新
	 */
	public boolean isAutoUpdate() {
		return isAutoUpdate;
	}

	/**
	 * @param 设置是否自动检测更新
	 */
	public void setAutoUpdate(boolean isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}

	/**
	 * @return 获取更新信息请求地址
	 */
	public String getUpdateInfoUrl() {
		return updateInfoUrl;
	}

	/**
	 * @param 设置更新信息请求地址
	 */
	public void setUpdateInfoUrl(String updateInfoUrl) {
		this.updateInfoUrl = updateInfoUrl;
	}

	/**
	 * @return 获取是否显示检测更新提示(即更新之前是否最新版或正在更新字样提示)
	 */
	public boolean isDisplayCheckUpdatePrompt() {
		return isDisplayCheckUpdatePrompt;
	}

	/**
	 * @param 设置是否显示检测更新提示
	 *            (即更新之前是否最新版或正在更新字样提示)
	 */
	public void setDisplayCheckUpdatePrompt(boolean isDisplayCheckUpdatePrompt) {
		this.isDisplayCheckUpdatePrompt = isDisplayCheckUpdatePrompt;
	}

	/**
	 * @return 获取遮罩显示加载中窗口主题
	 */
	public int getMaskLoadingTheme() {
		return maskLoadingTheme;
	}

	/**
	 * @param 设置遮罩显示加载中窗口主题
	 */
	public void setMaskLoadingTheme(int maskLoadingTheme) {
		this.maskLoadingTheme = maskLoadingTheme;
	}

	/**
	 * @return 获取遮罩加载中窗口背景
	 */
	public int getMaskLoadingBackground() {
		return maskLoadingBackground;
	}

	/**
	 * @param 设置遮罩加载中窗口背景
	 */
	public void setMaskLoadingBackground(int maskLoadingBackground) {
		this.maskLoadingBackground = maskLoadingBackground;
	}

	/**
	 * @return 获取遮罩加载中窗口动画
	 */
	public int getMaskLoadingAnimation() {
		return maskLoadingAnimation;
	}

	/**
	 * @param 设置遮罩加载中窗口动画
	 */
	public void setMaskLoadingAnimation(int maskLoadingAnimation) {
		this.maskLoadingAnimation = maskLoadingAnimation;
	}

	/**
	 * @return 获取应用程序版本号
	 */
	public int getVersionCode() {
		return versionCode;
	}

	/**
	 * @param 设置应用程序版本号
	 */
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * @return 获取对话框分隔线背景
	 */
	public int getDialogSplitLineBackground() {
		return dialogSplitLineBackground;
	}

	/**
	 * @param 设置对话框分隔线背景
	 */
	public void setDialogSplitLineBackground(int dialogSplitLineBackground) {
		this.dialogSplitLineBackground = dialogSplitLineBackground;
	}

	/**
	 * @return 获取对话框背景
	 */
	public int getDialogBackground() {
		return dialogBackground;
	}

	/**
	 * @param 设置对话框背景
	 */
	public void setDialogBackground(int dialogBackground) {
		this.dialogBackground = dialogBackground;
	}

	/**
	 * @return 获取对话框按钮背景
	 */
	public int getDialogButtonsBackground() {
		return dialogButtonsBackground;
	}

	/**
	 * @param 设置对话框按钮背景
	 */
	public void setDialogButtonsBackground(int dialogButtonsBackground) {
		this.dialogButtonsBackground = dialogButtonsBackground;
	}

	/**
	 * @return 获取对话框按钮文本颜色
	 */
	public int getDialogButtonTextColor() {
		return dialogButtonTextColor;
	}

	/**
	 * @param 设置对话框按钮文本颜色
	 */
	public void setDialogButtonTextColor(int dialogButtonTextColor) {
		this.dialogButtonTextColor = dialogButtonTextColor;
	}

	/**
	 * @return 获取对话框关闭按钮背景
	 */
	public int getDialogCloseButtonBackground() {
		return dialogCloseButtonBackground;
	}

	/**
	 * @param 设置对话框关闭按钮背景
	 */
	public void setDialogCloseButtonBackground(int dialogCloseButtonBackground) {
		this.dialogCloseButtonBackground = dialogCloseButtonBackground;
	}

	/**
	 * @return 获取对话框下载进度布局
	 */
	public int getDialogDownloadProgressLayout() {
		return dialogDownloadProgressLayout;
	}

	/**
	 * @param 设置对话框下载进度布局
	 */
	public void setDialogDownloadProgressLayout(int dialogDownloadProgressLayout) {
		this.dialogDownloadProgressLayout = dialogDownloadProgressLayout;
	}

	/**
	 * @return 获取apk下载方式
	 */
	public ApkDownloadType getDownloadType() {
		return downloadType;
	}

	/**
	 * @param 设置apk下载方式
	 */
	public void setDownloadType(ApkDownloadType downloadType) {
		this.downloadType = downloadType;
	}

	/**
	 * @return 获取用于远程更新时传进来的相关信息
	 */
	public ApkInfo getApkInfo() {
		return apkInfo;
	}

	/**
	 * @param 设置用于远程更新时传进来的相关信息
	 */
	public void setApkInfo(ApkInfo apkInfo) {
		this.apkInfo = apkInfo;
	}

	/**
	 * @return 获取通知栏下载视图实体
	 */
	public NoticeDownloadViewEntity getNoticeDownloadViewEntity() {
		return noticeDownloadViewEntity;
	}

	/**
	 * @param 设置通知栏下载视图实体
	 */
	public void setNoticeDownloadViewEntity(
			NoticeDownloadViewEntity noticeDownloadViewEntity) {
		this.noticeDownloadViewEntity = noticeDownloadViewEntity;
	}

	/**
	 * @return 获取检查更新提示文件
	 */
	public String getCheckUpdatePromptText() {
		return checkUpdatePromptText;
	}

	/**
	 * @param 设置检查更新提示文件
	 */
	public void setCheckUpdatePromptText(String checkUpdatePromptText) {
		this.checkUpdatePromptText = checkUpdatePromptText;
	}

	/**
	 * @return 获取没有网络提示文本
	 */
	public String getNoNetworkPromptText() {
		return noNetworkPromptText;
	}

	/**
	 * @param 设置没有网络提示文本
	 */
	public void setNoNetworkPromptText(String noNetworkPromptText) {
		this.noNetworkPromptText = noNetworkPromptText;
	}

	/**
	 * @return 获取最后版本提示文本
	 */
	public String getLastVersionPromptText() {
		return lastVersionPromptText;
	}

	/**
	 * @param 设置最后版本提示文本
	 */
	public void setLastVersionPromptText(String lastVersionPromptText) {
		this.lastVersionPromptText = lastVersionPromptText;
	}
}