package com.cloud.core.beans;

public class DeviceInfo {
	/**
	 * 移动设备国际辨识码
	 */
	private String imei = "";

	/**
	 * 国际移动用户识别码
	 */
	private String imsi = "";

	/**
	 * 手机序列号
	 */
	private String serialNumber = "";

	/**
	 * sim卡序列号
	 */
	private String simSerialNumber = "";

	/**
	 * 本机电话号码
	 */
	private String localTel = "";

	/**
	 * 手机型号
	 */
	private String model = "";

	/**
	 * sdk版本号
	 */
	private int sdkVersion = 0;

	/**
	 * 系统版本
	 */
	private String release = "";

	/**
	 * 供应商名称
	 */
	private String simPprovidersName = "";

	/**
	 * 本机mac地址
	 */
	private String mac = "";

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSimSerialNumber() {
		return simSerialNumber;
	}

	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}

	public String getLocalTel() {
		return localTel;
	}

	public void setLocalTel(String localTel) {
		this.localTel = localTel;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(int sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getSimPprovidersName() {
		return simPprovidersName;
	}

	public void setSimPprovidersName(String simPprovidersName) {
		this.simPprovidersName = simPprovidersName;
	}

	/**
	 * @return 获取本机mac地址
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param 设置本机mac地址
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
}
