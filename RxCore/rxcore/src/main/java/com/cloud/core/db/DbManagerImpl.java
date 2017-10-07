/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.core.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import com.cloud.core.ObjectJudge;
import com.cloud.core.db.sqlite.SqlInfo;
import com.cloud.core.db.sqlite.SqlInfoBuilder;
import com.cloud.core.db.sqlite.WhereBuilder;
import com.cloud.core.db.table.ColumnEntity;
import com.cloud.core.db.table.DbBase;
import com.cloud.core.db.table.DbModel;
import com.cloud.core.db.table.TableEntity;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DbManagerImpl extends DbBase {

    //*************************************** create instance ****************************************************

    /**
     * key: dbName
     */
    private final static HashMap<DaoConfig, DbManagerImpl> DAO_MAP = new HashMap<DaoConfig, DbManagerImpl>();

    private SQLiteDatabase database;
    private DaoConfig daoConfig;
    private boolean allowTransaction;

    private DbManagerImpl(DaoConfig config) {
        if (config == null) {
            return;
        }
        this.daoConfig = config;
        this.allowTransaction = config.isAllowTransaction();
        this.database = openOrCreateDatabase(config);
        DbOpenListener dbOpenListener = config.getDbOpenListener();
        if (dbOpenListener != null) {
            dbOpenListener.onDbOpened(this);
        }
    }

    public synchronized static DbManager getInstance(DaoConfig daoConfig) {
        if (daoConfig == null) {
            //使用默认配置
            daoConfig = new DaoConfig();
        }
        DbManagerImpl dao = DAO_MAP.get(daoConfig);
        if (dao == null) {
            dao = new DbManagerImpl(daoConfig);
            DAO_MAP.put(daoConfig, dao);
        } else {
            dao.daoConfig = daoConfig;
        }
        // update the database if needed
        SQLiteDatabase database = dao.database;
        if (database != null) {
            int oldVersion = database.getVersion();
            int newVersion = daoConfig.getDbVersion();
            if (oldVersion != newVersion) {
                if (oldVersion != 0) {
                    DbUpgradeListener upgradeListener = daoConfig.getDbUpgradeListener();
                    if (upgradeListener != null) {
                        upgradeListener.onUpgrade(dao, oldVersion, newVersion);
                    } else {
                        try {
                            dao.dropDb();
                        } catch (Exception e) {
                            Logger.L.error(e.getMessage(), e);
                        }
                    }
                }
                database.setVersion(newVersion);
            }
        }
        return dao;
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    //*********************************************** operations ********************************************************

    @Override
    public void addOrUpdate(Object entity) {
        try {
            if (entity == null) {
                return;
            }
            beginTransaction();
            if (entity instanceof List) {
                List<?> entities = (List<?>) entity;
                if (entities.isEmpty()) return;
                TableEntity<?> table = this.getTable(entities.get(0).getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    for (Object item : entities) {
                        saveOrUpdateWithoutTransaction(table, item);
                    }
                }
            } else {
                TableEntity<?> table = this.getTable(entity.getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    saveOrUpdateWithoutTransaction(table, entity);
                }
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override
    public void replace(Object entity) {
        try {
            if (entity == null) {
                return;
            }
            beginTransaction();
            if (entity instanceof List) {
                List<?> entities = (List<?>) entity;
                if (entities.isEmpty()) return;
                TableEntity<?> table = this.getTable(entities.get(0).getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    for (Object item : entities) {
                        execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(table, item));
                    }
                }
            } else {
                TableEntity<?> table = this.getTable(entity.getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(table, entity));
                }
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override
    public void add(Object entity) {
        try {
            if (entity == null) {
                return;
            }
            beginTransaction();
            if (entity instanceof List) {
                List<?> entities = (List<?>) entity;
                if (entities.isEmpty()) return;
                TableEntity<?> table = this.getTable(entities.get(0).getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    for (Object item : entities) {
                        execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(table, item));
                    }
                }
            } else {
                TableEntity<?> table = this.getTable(entity.getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(table, entity));
                }
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override
    public boolean addBindingId(Object entity) {
        boolean result = false;
        try {
            if (entity == null) {
                return false;
            }
            beginTransaction();
            if (entity instanceof List) {
                List<?> entities = (List<?>) entity;
                if (entities.isEmpty()) return false;
                TableEntity<?> table = this.getTable(entities.get(0).getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    for (Object item : entities) {
                        if (!saveBindingIdWithoutTransaction(table, item)) {
                            return false;
                        }
                    }
                }
            } else {
                TableEntity<?> table = this.getTable(entity.getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    result = saveBindingIdWithoutTransaction(table, entity);
                }
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
        return result;
    }

    @Override
    public void deleteById(Class<?> entityType, Object idValue) {
        if (entityType == null || idValue == null) {
            return;
        }
        try {
            beginTransaction();
            TableEntity<?> table = this.getTable(entityType);
            if (table != null && table.tableIsExist()) {
                execNonQuery(SqlInfoBuilder.buildDeleteSqlInfoById(table, idValue));
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override
    public void delete(Object entity) {
        try {
            if (entity == null) {
                return;
            }
            beginTransaction();
            if (entity instanceof List) {
                List<?> entities = (List<?>) entity;
                if (!ObjectJudge.isNullOrEmpty(entities)) {
                    TableEntity<?> table = this.getTable(entities.get(0).getClass());
                    if (table != null && table.tableIsExist()) {
                        for (Object item : entities) {
                            execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(table, item));
                        }
                    }
                }
            } else {
                TableEntity<?> table = this.getTable(entity.getClass());
                if (table != null && table.tableIsExist()) {
                    execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(table, entity));
                }
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override
    public void delete(Class<?> entityType) {
        delete(entityType, null);
    }

    @Override
    public int delete(Class<?> entityType, WhereBuilder whereBuilder) {
        if (entityType == null) {
            return 0;
        }
        TableEntity<?> table = this.getTable(entityType);
        if (table == null || !table.tableIsExist()) return 0;
        int result = 0;
        try {
            beginTransaction();
            result = executeUpdateDelete(SqlInfoBuilder.buildDeleteSqlInfo(table, whereBuilder));
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
        return result;
    }

    @Override
    public void update(Object entity, String... updateColumnNames) {
        try {
            if (entity == null || ObjectJudge.isNullOrEmpty(updateColumnNames)) {
                return;
            }
            beginTransaction();
            if (entity instanceof List) {
                List<?> entities = (List<?>) entity;
                if (!ObjectJudge.isNullOrEmpty(entities)) {
                    TableEntity<?> table = this.getTable(entities.get(0).getClass());
                    if (createTableIfNotExistAndAsyncColumns(table)) {
                        for (Object item : entities) {
                            execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(table, item, updateColumnNames));
                        }
                    }
                }

            } else {
                TableEntity<?> table = this.getTable(entity.getClass());
                if (createTableIfNotExistAndAsyncColumns(table)) {
                    execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(table, entity, updateColumnNames));
                }
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override
    public int update(Class<?> entityType, WhereBuilder whereBuilder, KeyValue... nameValuePairs) {
        if (entityType == null || whereBuilder == null || ObjectJudge.isNullOrEmpty(nameValuePairs)) {
            return 0;
        }
        int result = 0;
        try {
            beginTransaction();
            TableEntity<?> table = this.getTable(entityType);
            if (createTableIfNotExistAndAsyncColumns(table)) {
                result = executeUpdateDelete(SqlInfoBuilder.buildUpdateSqlInfo(table, whereBuilder, nameValuePairs));
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T findById(Class<T> entityType, Object idValue) {
        if (entityType == null) {
            return null;
        }
        if (idValue == null) {
            return JsonUtils.newNull(entityType);
        }
        TableEntity<T> table = this.getTable(entityType);
        if (createTableIfNotExistAndAsyncColumns(table)) {
            Selector selector = Selector.from(table).where(table.getId().getName(), "=", idValue);
            String sql = selector.limit(1).toString();
            Cursor cursor = execQuery(sql);
            if (cursor != null) {
                try {
                    if (cursor.moveToNext()) {
                        return CursorUtils.getEntity(table, cursor);
                    }
                } catch (Throwable e) {
                    Logger.L.error(e);
                    return JsonUtils.newNull(entityType);
                } finally {
                    CursorUtils.closeQuietly(cursor);
                }
            }
        }
        return JsonUtils.newNull(entityType);
    }

    @Override
    public <T> T findFirst(Class<T> entityType) {
        if (entityType == null) {
            return JsonUtils.newNull(entityType);
        }
        TableEntity<T> table = this.getTable(entityType);
        if (createTableIfNotExistAndAsyncColumns(table)) {
            T first = this.selector(entityType).findFirst();
            if (first == null) {
                first = JsonUtils.newNull(entityType);
            }
            return first;
        } else {
            return JsonUtils.newNull(entityType);
        }
    }

    @Override
    public <T> T findLast(Class<T> entityType) {
        if (entityType == null) {
            return JsonUtils.newNull(entityType);
        }
        TableEntity<T> table = this.getTable(entityType);
        if (createTableIfNotExistAndAsyncColumns(table)) {
            T last = this.selector(entityType).findLast();
            if (last == null) {
                last = JsonUtils.newNull(entityType);
            }
            return last;
        } else {
            return JsonUtils.newNull(entityType);
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) {
        if (entityType == null) {
            return new ArrayList<T>(0);
        }
        TableEntity<T> table = this.getTable(entityType);
        if (createTableIfNotExistAndAsyncColumns(table)) {
            List<T> all = this.selector(entityType).findAll();
            if (ObjectJudge.isNullOrEmpty(all)) {
                all = new ArrayList<T>(0);
            }
            return all;
        } else {
            return new ArrayList<T>();
        }
    }

    @Override
    public <T> Selector<T> selector(Class<T> entityType) {
        if (entityType == null) {
            return null;
        }
        TableEntity<T> table = this.getTable(entityType);
        return Selector.from(table);
    }

    @Override
    public DbModel findDbModelFirst(SqlInfo sqlInfo) {
        Cursor cursor = execQuery(sqlInfo);
        if (cursor != null) {
            try {
                if (cursor.moveToNext()) {
                    return CursorUtils.getDbModel(cursor);
                }
            } catch (Throwable e) {
                return null;
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return null;
    }

    @Override
    public List<DbModel> findDbModelAll(SqlInfo sqlInfo) {
        List<DbModel> dbModelList = new ArrayList<DbModel>();
        Cursor cursor = execQuery(sqlInfo);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    dbModelList.add(CursorUtils.getDbModel(cursor));
                }
            } catch (Throwable e) {
                return new ArrayList<DbModel>(0);
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return dbModelList;
    }

    //******************************************** config ******************************************************

    private SQLiteDatabase openOrCreateDatabase(DaoConfig config) {
        SQLiteDatabase result = null;
        if (config == null) {
            return null;
        }
        File dbDir = config.getDbDir();
        if (dbDir != null && (dbDir.exists() || dbDir.mkdirs())) {
            File dbFile = new File(dbDir, config.getDbName());
            result = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        } else {
            if (config.getApplication() == null) {
                return null;
            }
            result = config.getApplication().openOrCreateDatabase(config.getDbName(), 0, null);
        }
        return result;
    }

    //***************************** private operations with out transaction *****************************
    private void saveOrUpdateWithoutTransaction(TableEntity<?> table, Object entity) {
        if (table == null || entity == null) {
            return;
        }
        ColumnEntity id = table.getId();
        if (id == null) {
            return;
        }
        if (id.isAutoId()) {
            if (id.getColumnValue(entity) != null) {
                execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(table, entity));
            } else {
                saveBindingIdWithoutTransaction(table, entity);
            }
        } else {
            execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(table, entity));
        }
    }

    private boolean saveBindingIdWithoutTransaction(TableEntity<?> table, Object entity) {
        if (table == null || entity == null) {
            return false;
        }
        ColumnEntity id = table.getId();
        if (id == null) {
            return false;
        }
        if (id.isAutoId()) {
            execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(table, entity));
            long idValue = getLastAutoIncrementId(table.getName());
            if (idValue == -1) {
                return false;
            }
            id.setAutoIdValue(entity, idValue);
            return true;
        } else {
            execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(table, entity));
            return true;
        }
    }

    //************************************************ tools ***********************************

    private long getLastAutoIncrementId(String tableName) {
        long id = -1;
        Cursor cursor = execQuery("SELECT seq FROM sqlite_sequence WHERE name='" + tableName + "' LIMIT 1");
        if (cursor != null) {
            try {
                if (cursor.moveToNext()) {
                    id = cursor.getLong(0);
                }
            } catch (Throwable e) {
                Logger.L.error(e);
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return id;
    }

    @Override
    public void close() {
        if (DAO_MAP.containsKey(daoConfig)) {
            DAO_MAP.remove(daoConfig);
            this.database.close();
        }
    }

    ///////////////////////////////////// exec sql /////////////////////////////////////////////////////

    private void beginTransaction() {
        if (allowTransaction) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && database.isWriteAheadLoggingEnabled()) {
                database.beginTransactionNonExclusive();
            } else {
                database.beginTransaction();
            }
        }
    }

    private void setTransactionSuccessful() {
        if (allowTransaction) {
            database.setTransactionSuccessful();
        }
    }

    private void endTransaction() {
        if (allowTransaction) {
            database.endTransaction();
        }
    }


    @Override
    public int executeUpdateDelete(SqlInfo sqlInfo) {
        SQLiteStatement statement = null;
        try {
            if (!sqlInfo.isFlag()) {
                return 0;
            }
            statement = sqlInfo.buildStatement(database);
            return statement.executeUpdateDelete();
        } catch (Throwable e) {
            return 0;
        } finally {
            if (statement != null) {
                try {
                    statement.releaseReference();
                } catch (Throwable ex) {
                    Logger.L.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public int executeUpdateDelete(String sql) {
        SQLiteStatement statement = null;
        try {
            statement = database.compileStatement(sql);
            return statement.executeUpdateDelete();
        } catch (Throwable e) {
            return 0;
        } finally {
            if (statement != null) {
                try {
                    statement.releaseReference();
                } catch (Throwable ex) {
                    Logger.L.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public void execNonQuery(SqlInfo sqlInfo) {
        SQLiteStatement statement = null;
        try {
            if (!sqlInfo.isFlag()) {
                return;
            }
            statement = sqlInfo.buildStatement(database);
            statement.execute();
        } catch (Throwable e) {
            Logger.L.error(e);
        } finally {
            if (statement != null) {
                try {
                    statement.releaseReference();
                } catch (Throwable ex) {
                    Logger.L.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public void execNonQuery(String sql) {
        try {
            database.execSQL(sql);
        } catch (Throwable e) {
            Logger.L.error(e);
        }
    }

    @Override
    public Cursor execQuery(SqlInfo sqlInfo) {
        if (sqlInfo.isFlag()) {
            Cursor cursor = database.rawQuery(sqlInfo.getSql(), sqlInfo.getBindArgsAsStrArray());
            return database.rawQuery(sqlInfo.getSql(), sqlInfo.getBindArgsAsStrArray());
        } else {
            return null;
        }
    }

    @Override
    public Cursor execQuery(String sql) {
        return database.rawQuery(sql, null);
    }
}
