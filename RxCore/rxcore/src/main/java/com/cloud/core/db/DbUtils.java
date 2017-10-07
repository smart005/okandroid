package com.cloud.core.db;

import com.cloud.core.logger.Logger;

import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/27
 * @Description:数据库工具类
 * @Modifier:
 * @ModifyContent:
 */
public class DbUtils {
    private static DbUtils dbUtils = null;
    private Func1<DbConfigParam, DbConfigParam> configParamAction = null;
    private DbManager.DaoConfig daoConfig = null;
    private DbManager dbManager = null;

    public static DbUtils getInstance() {
        return dbUtils == null ? dbUtils = new DbUtils() : dbUtils;
    }

    public DbManager getDbManager() {
        return DbManagerImpl.getInstance(daoConfig);
    }

    /**
     * 初始化配置(在Application中初始化)
     *
     * @param configParamAction
     */
    public void instance(Func1<DbConfigParam, DbConfigParam> configParamAction) {
        try {
            if (configParamAction == null) {
                return;
            }
            this.configParamAction = configParamAction;
            DbConfigParam configParam = new DbConfigParam();
            configParam = configParamAction.call(configParam);
            if (configParam == null || daoConfig != null) {
                return;
            }
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(String.format("%s.db", configParam.getDbName()))
                    .setDbDir(configParam.getDbDir())
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setApplication(configParam.getApplication())
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL, 对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    });
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
