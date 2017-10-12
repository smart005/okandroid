package com.cloud.basicfun.beans;

import com.cloud.core.db.BaseDataItem;
import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.annotation.Table;

/**
 * @Author chenghailei
 * @Email:maplelucy1991@163.com
 * @CreateTime:17/7/4
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Table(name = "breakPoint")
public class BreakPointBean extends BaseDataItem {
    @Column(name = "id", isId = true, isIndex = true)
    private int id;
    @Column(name = "originalFileName", isIndex = true)
    private String originalFileName;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "filePath")
    private String filePath;
    @Column(name = "recordDirectory")
    private String recordDirectory;

    /**
     *
     */
    public String getRecordDirectory() {
        if (recordDirectory == null) {
            recordDirectory = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return recordDirectory;
    }

    /**
     * @param recordDirectory
     */
    public void setRecordDirectory(String recordDirectory) {
        this.recordDirectory = recordDirectory;
    }

    /**
     *
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     */
    public String getOriginalFileName() {
        if (originalFileName == null) {
            originalFileName = "";
        }
        super.setCurrAttrName(super.getInvokingAttrName());
        return originalFileName;
    }

    /**
     * @param originalFileName
     */
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    /**
     *
     */
    public String getFileName() {
        if (fileName == null) {
            fileName = "";
        }
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     */
    public String getFilePath() {
        if (filePath == null) {
            filePath = "";
        }
        return filePath;
    }

    /**
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
