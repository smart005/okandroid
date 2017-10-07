package com.cloud.core.beans;

import android.content.pm.PackageInfo;

import com.cloud.core.enums.LogLevel;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-11 下午3:18:44
 * @Description: 缓存日志文件参数
 * @Modifier:
 * @ModifyContent:
 */
public class CrashFileParam {
    /**
     * 消息
     */
    private String message = "";
    /**
     * 缓存值
     */
    private Object crashValue = null;
    /**
     * 日志级别
     */
    private LogLevel level = LogLevel.ERROR;
    /**
     * 设备信息
     */
    private DeviceInfo deviceInfo = null;
    /**
     * 包信息
     */
    private PackageInfo packageInfo = null;

    /**
     * @return 获取消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param 设置消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return 获取缓存值
     */
    public Object getCrashValue() {
        return crashValue;
    }

    /**
     * @param 设置缓存值
     */
    public void setCrashValue(Object crashValue) {
        this.crashValue = crashValue;
    }

    /**
     * @return 获取日志级别
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * @param 设置日志级别
     */
    public void setLevel(LogLevel level) {
        this.level = level;
    }

    /**
     * @return 获取设备信息
     */
    public DeviceInfo getDeviceInfo() {
        if (deviceInfo == null) {
            return new DeviceInfo();
        } else {
            return deviceInfo;
        }
    }

    /**
     * 设置设备信息
     *
     * @param deviceInfo
     */
    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    /**
     * @return 获取包信息
     */
    public PackageInfo getPackageInfo() {
        if (packageInfo == null) {
            return new PackageInfo();
        } else {
            return packageInfo;
        }
    }

    /**
     * 设置包信息
     *
     * @param packageInfo
     */
    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }
}
