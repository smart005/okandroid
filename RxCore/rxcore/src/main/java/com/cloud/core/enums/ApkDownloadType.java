package com.cloud.core.enums;

public enum ApkDownloadType {
	NONE(-1, "无更新"), WINDOW(0, "窗口模式"), NOTIFICATION(1, "通知栏模式");

	// 枚举值
	private int value = 0;
	// 描述值
	private String des = "";

	private ApkDownloadType(int value, String des) {
		this.value = value;
		this.des = des;
	}

	// 获取值
	public int getValue() {
		return this.value;
	}

	// 设置值
	public void setValue(int value) {
		this.value = value;
	}

	// 获取描述
	public String getDes() {
		return this.des;
	}

	// 设置描述
	public void setDes(String des) {
		this.des = des;
	}

	// 根据枚举值获取对应的枚举
	public static final ApkDownloadType getEnumByValue(int value) {
		ApkDownloadType currEnum = null;
		for (ApkDownloadType e : ApkDownloadType.values()) {
			if (e.getValue() == value) {
				currEnum = e;
				break;
			}
		}
		return currEnum;
	}
}
