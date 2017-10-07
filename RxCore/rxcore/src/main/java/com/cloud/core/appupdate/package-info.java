package com.cloud.core.appupdate;

class VersionUpdateAction {
	static final String UPDATE_RECEIVER_ACTION = "Action:android.tnt.UPDATE_APK";
	static final String RECEIVER_DATA_KEY = "12570862";
	static final String NOTICE_MODE_KEY = "49333878";
}

enum NoticeUpdateEnum {
	/**
	 * 检测到有新版本
	 */
	NewVersion(0),
	/**
	 * 稍候更新
	 */
	LaterUpdate(1),
	/**
	 * 最后版本
	 */
	LasterVersion(2),
	/**
	 * 更新完成
	 */
	UpdateComplete(3),
	/**
	 * 不再提醒
	 */
	NotAgainRemind(4),
	/**
	 * 立即更新
	 */
	NowUpdate(5);

	private int value = 0;

	private NoticeUpdateEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}