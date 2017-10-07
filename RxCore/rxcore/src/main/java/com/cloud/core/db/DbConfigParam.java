package com.cloud.core.db;

import android.app.Application;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/27
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class DbConfigParam {
    /**
     * 数据库名称
     */
    private String dbName = "";
    /**
     * 数据库目录
     */
    private File dbDir = null;

    private Application application = null;

    /**
     * 获取数据库名称
     */
    public String getDbName() {
        if (dbName == null) {
            dbName = "";
        }
        return dbName;
    }

    /**
     * 设置数据库名称
     *
     * @param dbName
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 获取数据库目录
     */
    public File getDbDir() {
        return dbDir;
    }

    /**
     * 设置数据库目录
     *
     * @param dbDir
     */
    public void setDbDir(File dbDir) {
        this.dbDir = dbDir;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
