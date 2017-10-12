package com.cloud.basicfuntest;

import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.enums.ComRequestUrlType;
import com.cloud.core.beans.StorageInitParam;
import com.cloud.core.db.DbConfigParam;
import com.cloud.core.db.DbUtils;
import com.cloud.core.okgo.OkgoBase;
import com.cloud.core.utils.StorageUtils;

import rx.functions.Func0;
import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/23
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BisicFunApplication extends BaseApplication {

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
        OkgoBase.getInstance().init(this);
        //Db初始化
        DbUtils.getInstance().instance(new Func1<DbConfigParam, DbConfigParam>() {
            @Override
            public DbConfigParam call(DbConfigParam dbConfigParam) {
                dbConfigParam.setApplication(BisicFunApplication.this);
                dbConfigParam.setDbDir(StorageUtils.getApksDir());
                dbConfigParam.setDbName("rxDb");
                return dbConfigParam;
            }
        });
        registerAction();
    }

    private void registerAction() {
        setUrlAction(new Func1<ComRequestUrlType, String>() {
            @Override
            public String call(ComRequestUrlType comRequestUrlType) {
                if (comRequestUrlType == ComRequestUrlType.ALiYunAssumeRole) {
                    return "https://mobile.findaily.cn/rest/aliyunAssumeRole";
                }
                if (comRequestUrlType == ComRequestUrlType.Endpoint) {
                    return "http://oss-cn-hangzhou.aliyuncs.com";
                } else if (comRequestUrlType == ComRequestUrlType.Bucket) {
                    return "farong";
                }
                return "";
            }
        });
        setGetTokenAction(new Func0<String>() {
            @Override
            public String call() {
                return "b341062fc4291609c51aa6fc8b2f057c";
            }
        });
    }
}
