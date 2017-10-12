package com.cloud.basicfun.oss;

import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/1
 * @Description:oss 上传文件属性
 * @Modifier:
 * @ModifyContent:
 */
public class OssUploadFileItem {
    /**
     * 文件名
     */
    private String fileName = "";
    /**
     * 文件路径
     */
    private String filePath = "";
    /**
     * 请求参数
     */
    private Map<String, String> params = null;

    /**
     * 获取文件名
     */
    public String getFileName() {
        if (fileName == null) {
            fileName = "";
        }
        return fileName;
    }

    /**
     * 设置文件名
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件路径
     */
    public String getFilePath() {
        if (filePath == null) {
            filePath = "";
        }
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取请求参数
     */
    public Map<String, String> getParams() {
        return this.params;
    }

    /**
     * 设置请求参数
     *
     * @param String>
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
