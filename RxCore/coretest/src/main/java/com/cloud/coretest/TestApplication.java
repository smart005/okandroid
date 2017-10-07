package com.cloud.coretest;

import android.app.Application;

import com.cloud.core.beans.StorageInitParam;
import com.cloud.core.db.DbConfigParam;
import com.cloud.core.db.DbUtils;
import com.cloud.core.okgo.OkgoBase;
import com.cloud.core.utils.StorageUtils;

import rx.functions.Func1;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/1
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class TestApplication extends Application {

    static {
        //目录配置
        StorageUtils.setOnStorageInitListener(new StorageUtils.OnStorageInitListener() {
            @Override
            public StorageInitParam getStorageInit() {
                StorageInitParam storageInitParam = new StorageInitParam();
                storageInitParam.setAppDir("jrjr");
                return storageInitParam;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //OkGo初始化
        OkgoBase.getInstance().init(this);

        //Db初始化
        DbUtils.getInstance().instance(new Func1<DbConfigParam, DbConfigParam>() {
            @Override
            public DbConfigParam call(DbConfigParam dbConfigParam) {
                dbConfigParam.setApplication(TestApplication.this);
                dbConfigParam.setDbDir(StorageUtils.getApksDir());
                dbConfigParam.setDbName("rxDb");
                return dbConfigParam;
            }
        });
    }
}
