package com.cloud.core.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class StorageInitParam {
    /**
     * app目录
     */
    private String appDir = "rongxun";
    /**
     * 图片目录
     */
    private String imagesDir = "images";
    /**
     * 语音文件
     */
    private String audiosDir = "audios";
    /**
     * 视频文件
     */
    private String videoDir = "videos";
    /**
     * 错误日志目录
     */
    private String errorsDir = "errors";
    /**
     * debug日志目录
     */
    private String debugsDir = "debugs";
    /**
     * 信息日志目录
     */
    private String InfosDir = "infos";
    /**
     * 警告类日志目录
     */
    private String warningsDir = "warnings";
    /**
     * 插件目录
     */
    private String pluginsDir = "plugins";
    /**
     * 程序更新目录
     */
    private String apksDir = "apks";
    /**
     * 缓存目录
     */
    private String cachesDir = "caches";
    /**
     * 数据缓存目录
     */
    private String dataCachesDir = "datacaches";
    /**
     * 二维码
     */
    private String qrcodesDir = "qrcodes";
    /**
     * 热补丁目录
     */
    private String apatchsDir = "apatchs";
    /**
     * 待处理
     */
    private String todosDir = "processings";
    /**
     * oss持久保存记录目录
     */
    private String ossRecord = "ossrecord";
    /**
     * 默认下载路径
     */
    private String download = "download";

    /**
     * 获取app目录
     */
    public String getAppDir() {
        return appDir;
    }

    /**
     * 设置app目录
     *
     * @param appDir
     */
    public void setAppDir(String appDir) {
        this.appDir = appDir;
    }

    /**
     * 获取图片目录
     */
    public String getImagesDir() {
        return imagesDir;
    }

    /**
     * 获取语音文件
     */
    public String getAudiosDir() {
        return audiosDir;
    }

    /**
     * 获取视频文件
     */
    public String getVideoDir() {
        return videoDir;
    }

    /**
     * 获取错误日志目录
     */
    public String getErrorsDir() {
        return errorsDir;
    }

    /**
     * 获取debug日志目录
     */
    public String getDebugsDir() {
        return debugsDir;
    }

    /**
     * 获取信息日志目录
     */
    public String getInfosDir() {
        return InfosDir;
    }

    /**
     * 获取警告类日志目录
     */
    public String getWarningsDir() {
        return warningsDir;
    }

    /**
     * 获取插件目录
     */
    public String getPluginsDir() {
        return pluginsDir;
    }

    /**
     * 获取程序更新目录
     */
    public String getApksDir() {
        return apksDir;
    }

    /**
     * 获取数据缓存目录
     */
    public String getDataCachesDir() {
        return dataCachesDir;
    }

    /**
     * 获取缓存目录
     */
    public String getCachesDir() {
        return cachesDir;
    }

    /**
     * 获取二维码
     */
    public String getQrcodesDir() {
        return qrcodesDir;
    }

    /**
     * 获取热补丁目录
     */
    public String getApatchsDir() {
        return apatchsDir;
    }

    /**
     * 获取待处理
     */
    public String getTodosDir() {
        return todosDir;
    }

    /**
     * 获取oss持久保存记录目录
     */
    public String getOssRecord() {
        if (ossRecord == null) {
            ossRecord = "";
        }
        return ossRecord;
    }

    /**
     * 设置oss持久保存记录目录
     *
     * @param ossRecord
     */
    public void setOssRecord(String ossRecord) {
        this.ossRecord = ossRecord;
    }

    public String getDownload() {
        return download;
    }
}
