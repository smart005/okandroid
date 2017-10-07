package com.cloud.core.db.table;

import android.database.Cursor;

import com.cloud.core.ObjectJudge;
import com.cloud.core.db.CursorUtils;
import com.cloud.core.db.DbManager;
import com.cloud.core.db.sqlite.SqlInfo;
import com.cloud.core.db.sqlite.SqlInfoBuilder;
import com.cloud.core.logger.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DbManager基类, 包含表结构的基本操作.
 * Created by wyouflf on 16/1/22.
 */
public abstract class DbBase implements DbManager {

    private final HashMap<Class<?>, TableEntity<?>> tableMap = new HashMap<Class<?>, TableEntity<?>>();

    @Override
    public <T> TableEntity<T> getTable(Class<T> entityType) {
        if (entityType == null) {
            return new TableEntity<T>(this, entityType);
        }
        synchronized (tableMap) {
            TableEntity<T> table = (TableEntity<T>) tableMap.get(entityType);
            if (table == null) {
                table = new TableEntity<T>(this, entityType);
                tableMap.put(entityType, table);
            }
            return table;
        }
    }

    @Override
    public void dropTable(Class<?> entityType) {
        try {
            if (entityType == null) {
                return;
            }
            TableEntity<?> table = this.getTable(entityType);
            if (table == null || !table.tableIsExist()) return;
            execNonQuery("DROP TABLE \"" + table.getName() + "\"");
            table.setCheckedDatabase(false);
            this.removeTable(entityType);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    @Override
    public void dropDb() {
        try {
            Cursor cursor = execQuery("SELECT name FROM sqlite_master WHERE type='table' AND name<>'sqlite_sequence'");
            if (cursor != null) {
                try {
                    while (cursor.moveToNext()) {
                        try {
                            String tableName = cursor.getString(0);
                            execNonQuery("DROP TABLE " + tableName);
                        } catch (Throwable e) {
                            Logger.L.error(e.getMessage(), e);
                        }
                    }
                    synchronized (tableMap) {
                        for (TableEntity<?> table : tableMap.values()) {
                            table.setCheckedDatabase(false);
                        }
                        tableMap.clear();
                    }
                } catch (Throwable e) {
                    Logger.L.error(e);
                } finally {
                    CursorUtils.closeQuietly(cursor);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    @Override
    public void addColumn(Class<?> entityType, String column) {
        try {
            if (entityType == null || column == null) {
                return;
            }
            TableEntity<?> table = this.getTable(entityType);
            if (table == null || table.getColumnMap() == null || !table.getColumnMap().containsKey(column)) {
                return;
            }
            ColumnEntity col = table.getColumnMap().get(column);
            if (col != null) {
                StringBuilder builder = new StringBuilder();
                builder.append("ALTER TABLE ").append("\"").append(table.getName()).append("\"").
                        append(" ADD COLUMN ").append("\"").append(col.getName()).append("\"").
                        append(" ").append(col.getColumnDbType()).
                        append(" ").append(col.getProperty());
                execNonQuery(builder.toString());
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    protected boolean createTableIfNotExistAndAsyncColumns(TableEntity<?> table) {
        if (table == null) {
            return false;
        }
        if (!table.tableIsExist()) {
            synchronized (table.getClass()) {
                if (!table.tableIsExist()) {
                    SqlInfo sqlInfo = SqlInfoBuilder.buildCreateTableSqlInfo(table);
                    if (sqlInfo.isFlag()) {
                        execNonQuery(sqlInfo);
                        if (!ObjectJudge.isNullOrEmpty(table.getColumnMap())) {
                            boolean hasIndex = false;
                            StringBuilder indexsb = new StringBuilder();
                            indexsb.append("CREATE UNIQUE INDEX IF NOT EXISTS index_name ON ");
                            indexsb.append(String.format("%s (", table.getName()));
                            for (Map.Entry<String, ColumnEntity> entry : table.getColumnMap().entrySet()) {
                                ColumnEntity columnEntity = entry.getValue();
                                if (columnEntity != null && columnEntity.isIndex()) {
                                    hasIndex = true;
                                    indexsb.append(String.format("%s,", columnEntity.getName()));
                                }
                            }
                            if (hasIndex && indexsb.length() > 0) {
                                String isql = String.format("%s)", indexsb.substring(0, indexsb.length() - 1));
                                execNonQuery(isql);
                            }
                        }
                        table.setCheckedDatabase(true);
                    } else {
                        table.setCheckedDatabase(false);
                    }
                    TableCreateListener listener = this.getDaoConfig().getTableCreateListener();
                    if (listener != null) {
                        listener.onTableCreated(this, table);
                    }
                }
            }
            return true;
        } else {
            //获取db表信息
            HashMap<String, TableInfo> tableColumns = table.getTableColumns(table.getName());
            if (ObjectJudge.isNullOrEmpty(tableColumns)) {
                return false;
            } else {
                LinkedHashMap<String, ColumnEntity> columnMap = table.getColumnMap();
                if (ObjectJudge.isNullOrEmpty(columnMap)) {
                    return false;
                } else {
                    for (Map.Entry<String, ColumnEntity> entry : columnMap.entrySet()) {
                        if (!tableColumns.containsKey(entry.getKey())) {
                            addColumn(table.getEntityType(), entry.getKey());
                        }
                    }
                    return true;
                }
            }
        }
    }

    protected void removeTable(Class<?> entityType) {
        if (entityType == null || tableMap == null || !tableMap.containsKey(entityType)) {
            return;
        }
        synchronized (tableMap) {
            tableMap.remove(entityType);
        }
    }
}
