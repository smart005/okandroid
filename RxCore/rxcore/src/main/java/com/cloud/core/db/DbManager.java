package com.cloud.core.db;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.cloud.core.db.sqlite.SqlInfo;
import com.cloud.core.db.sqlite.WhereBuilder;
import com.cloud.core.db.table.DbModel;
import com.cloud.core.db.table.TableEntity;

import java.io.Closeable;
import java.io.File;
import java.util.List;

/**
 * 数据库访问接口
 */
public interface DbManager extends Closeable {

    DaoConfig getDaoConfig();

    SQLiteDatabase getDatabase();

    /**
     * 保存实体类或实体类的List到数据库,
     * 如果该类型的id是自动生成的, 则保存完后会给id赋值.
     *
     * @param entity
     * @return
     * @throws DbException
     */
    boolean addBindingId(Object entity);

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id对应的数据是否存在.
     *
     * @param entity
     * @throws DbException
     */
    void addOrUpdate(Object entity);

    /**
     * 保存实体类或实体类的List到数据库
     *
     * @param entity
     * @throws DbException
     */
    void add(Object entity);

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id和其他唯一索引判断数据是否存在.
     *
     * @param entity
     * @throws DbException
     */
    void replace(Object entity);

    ///////////// delete
    void deleteById(Class<?> entityType, Object idValue);

    void delete(Object entity);

    void delete(Class<?> entityType);

    int delete(Class<?> entityType, WhereBuilder whereBuilder);

    ///////////// update
    void update(Object entity, String... updateColumnNames);

    int update(Class<?> entityType, WhereBuilder whereBuilder, KeyValue... nameValuePairs);

    ///////////// find
    <T> T findById(Class<T> entityType, Object idValue);

    <T> T findFirst(Class<T> entityType);

    <T> T findLast(Class<T> entityType);

    <T> List<T> findAll(Class<T> entityType);

    <T> Selector<T> selector(Class<T> entityType);

    DbModel findDbModelFirst(SqlInfo sqlInfo);

    List<DbModel> findDbModelAll(SqlInfo sqlInfo);

    ///////////// table

    /**
     * 获取表信息
     *
     * @param entityType
     * @param <T>
     * @return
     * @throws DbException
     */
    <T> TableEntity<T> getTable(Class<T> entityType);

    /**
     * 删除表
     *
     * @param entityType
     * @throws DbException
     */
    void dropTable(Class<?> entityType);

    /**
     * 添加一列,
     * 新的entityType中必须定义了这个列的属性.
     *
     * @param entityType
     * @param column
     * @throws DbException
     */
    void addColumn(Class<?> entityType, String column);

    ///////////// db

    /**
     * 删除库
     *
     * @throws DbException
     */
    void dropDb();

    /**
     * 关闭数据库,
     * xUtils对同一个库的链接是单实例的, 一般不需要关闭它.
     */
    void close();

    ///////////// custom
    int executeUpdateDelete(SqlInfo sqlInfo);

    int executeUpdateDelete(String sql);

    void execNonQuery(SqlInfo sqlInfo);

    void execNonQuery(String sql);

    Cursor execQuery(SqlInfo sqlInfo);

    Cursor execQuery(String sql);

    public interface DbOpenListener {
        void onDbOpened(DbManager db);
    }

    public interface DbUpgradeListener {
        void onUpgrade(DbManager db, int oldVersion, int newVersion);
    }

    public interface TableCreateListener {
        void onTableCreated(DbManager db, TableEntity<?> table);
    }

    public static class DaoConfig {
        private File dbDir;
        private String dbName = "c113c99844f941aa9f8a1a2f6ece0524.db";
        private int dbVersion = 1;
        private boolean allowTransaction = true;
        private DbUpgradeListener dbUpgradeListener;
        private TableCreateListener tableCreateListener;
        private DbOpenListener dbOpenListener;
        private Application application = null;

        public DaoConfig() {
        }

        public DaoConfig setDbDir(File dbDir) {
            if (dbDir != null && dbDir.isDirectory()) {
                this.dbDir = dbDir;
            }
            return this;
        }

        public DaoConfig setDbName(String dbName) {
            if (!TextUtils.isEmpty(dbName)) {
                this.dbName = dbName;
            }
            return this;
        }

        public DaoConfig setDbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
            return this;
        }

        public DaoConfig setAllowTransaction(boolean allowTransaction) {
            this.allowTransaction = allowTransaction;
            return this;
        }

        public DaoConfig setDbOpenListener(DbOpenListener dbOpenListener) {
            this.dbOpenListener = dbOpenListener;
            return this;
        }

        public DaoConfig setDbUpgradeListener(DbUpgradeListener dbUpgradeListener) {
            this.dbUpgradeListener = dbUpgradeListener;
            return this;
        }

        public DaoConfig setTableCreateListener(TableCreateListener tableCreateListener) {
            this.tableCreateListener = tableCreateListener;
            return this;
        }

        public File getDbDir() {
            return dbDir;
        }

        public String getDbName() {
            return dbName;
        }

        public int getDbVersion() {
            return dbVersion;
        }

        public boolean isAllowTransaction() {
            return allowTransaction;
        }

        public DbOpenListener getDbOpenListener() {
            return dbOpenListener;
        }

        public DbUpgradeListener getDbUpgradeListener() {
            return dbUpgradeListener;
        }

        public TableCreateListener getTableCreateListener() {
            return tableCreateListener;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DaoConfig daoConfig = (DaoConfig) o;

            if (!dbName.equals(daoConfig.dbName)) return false;
            return dbDir == null ? daoConfig.dbDir == null : dbDir.equals(daoConfig.dbDir);
        }

        @Override
        public int hashCode() {
            int result = dbName.hashCode();
            result = 31 * result + (dbDir != null ? dbDir.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return String.valueOf(dbDir) + "/" + dbName;
        }

        public Application getApplication() {
            return application;
        }

        public DaoConfig setApplication(Application application) {
            this.application = application;
            return this;
        }
    }
}
