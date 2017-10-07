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

import com.cloud.core.db.table.ColumnEntity;
import com.cloud.core.db.table.DbModel;
import com.cloud.core.db.table.TableEntity;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;

import java.util.HashMap;

import static android.R.attr.columnCount;

public class CursorUtils {

    public static <T> T getEntity(TableEntity<T> table, final Cursor cursor) {
        if (table == null || cursor == null) {
            return JsonUtils.newNull(table.getEntityType());
        }
        T entity = table.createEntity();
        try {
            if (entity == null) {
                return JsonUtils.newNull(table.getEntityType());
            }
            HashMap<String, ColumnEntity> columnMap = table.getColumnMap();
            if (columnMap == null) {
                return entity;
            }
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = cursor.getColumnName(i);
                ColumnEntity column = columnMap.get(columnName);
                if (column != null) {
                    column.setValueFromCursor(entity, cursor, i);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return entity;
    }

    public static DbModel getDbModel(Cursor cursor) {
        DbModel result = new DbModel();
        if (cursor == null) {
            return result;
        }
        int columnCount = cursor.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            result.add(cursor.getColumnName(i), cursor.getString(i));
        }
        return result;
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable ignored) {
                Logger.L.error(ignored.getMessage(), ignored);
            }
        }
    }
}
