package com.cloud.basicfun.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/14
 * @Description: 选择的图片属性
 * @Modifier:
 * @ModifyContent:
 */
public class SelectImageProperties {
    /**
     * 图片路径
     */
    private String imagePath = "";
    /**
     * 图片文件名
     */
    private String imageFileName = "";
    /**
     * 缩略图路径
     */
    private String thumImagePath = "";
    /**
     * 缩略图文件名
     */
    private String thumImageFileName = "";

    /**
     * 获取图片路径
     */
    public String getImagePath() {
        if (imagePath == null) {
            imagePath = "";
        }
        return imagePath;
    }

    /**
     * 设置图片路径
     *
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * 获取图片文件名
     */
    public String getImageFileName() {
        if (imageFileName == null) {
            imageFileName = "";
        }
        return imageFileName;
    }

    /**
     * 设置图片文件名
     *
     * @param imageFileName
     */
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    /**
     * 获取缩略图路径
     */
    public String getThumImagePath() {
        if (thumImagePath == null) {
            thumImagePath = "";
        }
        return thumImagePath;
    }

    /**
     * 设置缩略图路径
     *
     * @param thumImagePath
     */
    public void setThumImagePath(String thumImagePath) {
        this.thumImagePath = thumImagePath;
    }

    /**
     * 获取缩略图文件名
     */
    public String getThumImageFileName() {
        if (thumImageFileName == null) {
            thumImageFileName = "";
        }
        return thumImageFileName;
    }

    /**
     * 设置缩略图文件名
     *
     * @param thumImageFileName
     */
    public void setThumImageFileName(String thumImageFileName) {
        this.thumImageFileName = thumImageFileName;
    }
}
