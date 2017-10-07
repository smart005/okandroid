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

package com.cloud.core.db.table;

import android.database.Cursor;
import android.text.TextUtils;

import com.cloud.core.db.CursorUtils;
import com.cloud.core.db.DbManager;
import com.cloud.core.db.annotation.Table;
import com.cloud.core.logger.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;


public final class TableEntity<T> {

    private DbManager db;
    private String name;
    private ColumnEntity id;
    private Class<T> entityType;
    private Constructor<T> constructor;
    private volatile boolean checkedDatabase;

    /**
     * key: columnName
     */
    private LinkedHashMap<String, ColumnEntity> columnMap;

    TableEntity(DbManager db, Class<T> entityType) {
        try {
            this.db = db;
            this.entityType = entityType;
            this.constructor = entityType.getConstructor();
            this.constructor.setAccessible(true);
            Table table = entityType.getAnnotation(Table.class);
            this.name = table.name();
            this.columnMap = TableUtils.findColumnMap(entityType);
            for (ColumnEntity column : columnMap.values()) {
                if (column.isId()) {
                    this.id = column;
                    break;
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public T createEntity() {
        try {
            return this.constructor.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public boolean tableIsExist() {
        if (this.isCheckedDatabase()) {
            return true;
        }
        Cursor cursor = db.execQuery("SELECT COUNT(*) AS c FROM sqlite_master WHERE type='table' AND name='" + name + "'");
        if (cursor != null) {
            try {
                if (cursor.moveToNext()) {
                    int count = cursor.getInt(0);
                    if (count > 0) {
                        this.setCheckedDatabase(true);
                        return true;
                    }
                }
            } catch (Throwable e) {
                Logger.L.error(e);
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return false;
    }

    public HashMap<String, TableInfo> getTableColumns(String tableName) {
        HashMap<String, TableInfo> lst = new HashMap<String, TableInfo>();
        Cursor cursor = db.execQuery(MessageFormat.format("pragma table_info([{0}])", tableName));
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    if (!TextUtils.isEmpty(name) && !lst.containsKey(name)) {
                        TableInfo tableInfo = new TableInfo();
                        tableInfo.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
                        tableInfo.setName(name);
                        tableInfo.setNotnull(cursor.getInt(cursor.getColumnIndex("notnull")));
                        tableInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                        tableInfo.setPk(cursor.getInt(cursor.getColumnIndex("pk")));
                        lst.put(name, tableInfo);
                    }
                } while (cursor.moveToNext());
            }
        }
        return lst;
    }


    public DbManager getDb() {
        return db;
    }

    public String getName() {
        return name;
    }

    public Class<T> getEntityType() {
        return entityType;
    }

    public ColumnEntity getId() {
        return id;
    }

    public LinkedHashMap<String, ColumnEntity> getColumnMap() {
        return columnMap;
    }

    boolean isCheckedDatabase() {
        return checkedDatabase;
    }

    void setCheckedDatabase(boolean checkedDatabase) {
        this.checkedDatabase = checkedDatabase;
    }

    @Override
    public String toString() {
        return name;
    }
}
