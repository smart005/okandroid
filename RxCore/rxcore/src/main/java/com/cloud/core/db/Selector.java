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

import com.cloud.core.ObjectJudge;
import com.cloud.core.cache.CacheDataItem;
import com.cloud.core.db.sqlite.WhereBuilder;
import com.cloud.core.db.table.ColumnEntity;
import com.cloud.core.db.table.DbModel;
import com.cloud.core.db.table.TableEntity;
import com.cloud.core.exception.DbException;
import com.cloud.core.utils.JsonUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: wyouflf
 * Date: 13-8-9
 * Time: 下午10:19
 */
public final class Selector<T> extends CacheDataItem {

    private final TableEntity<T> table;

    private WhereBuilder whereBuilder;
    private List<OrderBy> orderByList;
    private int limit = 0;
    private int offset = 0;

    public Selector(TableEntity<T> table) {
        this.table = table;
    }

    /*package*/
    static <T> Selector<T> from(TableEntity<T> table) {
        return new Selector<T>(table);
    }

    public Selector<T> where(WhereBuilder whereBuilder) {
        this.whereBuilder = whereBuilder;
        return this;
    }

    public Selector<T> where(String columnName, String op, Object value) {
        this.whereBuilder = WhereBuilder.b(columnName, op, value);
        return this;
    }

    public Selector<T> and(String columnName, String op, Object value) {
        this.whereBuilder.and(columnName, op, value);
        return this;
    }

    public Selector<T> and(WhereBuilder where) {
        this.whereBuilder.and(where);
        return this;
    }

    public Selector<T> or(String columnName, String op, Object value) {
        this.whereBuilder.or(columnName, op, value);
        return this;
    }

    public Selector or(WhereBuilder where) {
        this.whereBuilder.or(where);
        return this;
    }

    public Selector<T> expr(String expr) {
        if (this.whereBuilder == null) {
            this.whereBuilder = WhereBuilder.b();
        }
        this.whereBuilder.expr(expr);
        return this;
    }

    public DbModelSelector groupBy(String columnName) {
        return new DbModelSelector(this, columnName);
    }

    public DbModelSelector select(String... columnExpressions) {
        return new DbModelSelector(this, columnExpressions);
    }

    public Selector<T> orderBy(String columnName) {
        if (orderByList == null) {
            orderByList = new ArrayList<OrderBy>(5);
        }
        orderByList.add(new OrderBy(columnName));
        return this;
    }

    public Selector<T> orderBy(String columnName, boolean desc) {
        if (orderByList == null) {
            orderByList = new ArrayList<OrderBy>(5);
        }
        orderByList.add(new OrderBy(columnName, desc));
        return this;
    }

    public Selector<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Selector<T> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public TableEntity<T> getTable() {
        return table;
    }

    public WhereBuilder getWhereBuilder() {
        return whereBuilder;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public T findFirst() {
        if (!table.tableIsExist()) {
            return null;
        }
        this.limit(1);
        Cursor cursor = table.getDb().execQuery(this.toString());
        if (cursor != null) {
            try {
                if (cursor.moveToNext()) {
                    return CursorUtils.getEntity(table, cursor);
                }
            } catch (Throwable e) {
                return null;
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return null;
    }

    public T findLast() {
        if (!table.tableIsExist()) {
            return null;
        }
        this.limit(1);
        StringBuilder result = new StringBuilder();
        getTempSql(result, getColumns());
        if (limit > 0) {
            result.append(" LIMIT ").append(limit);
            StringBuffer offsetsb = new StringBuffer();
            getTempSql(result, "(count(1)-1)");
            result.append(" OFFSET ");
            result.append(MessageFormat.format("({0})", offsetsb.toString()));
        }
        Cursor cursor = table.getDb().execQuery(this.toString());
        if (cursor != null) {
            try {
                if (cursor.moveToNext()) {
                    return CursorUtils.getEntity(table, cursor);
                }
            } catch (Throwable e) {
                return null;
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return null;
    }

    public List<T> findAll() {
        if (!table.tableIsExist()) {
            return new ArrayList<T>(0);
        }
        List<T> result = null;
        Cursor cursor = table.getDb().execQuery(this.toString());
        if (cursor != null) {
            try {
                result = new ArrayList<T>();
                while (cursor.moveToNext()) {
                    T entity = CursorUtils.getEntity(table, cursor);
                    result.add(entity);
                }
            } catch (Throwable e) {
                return new ArrayList<T>(0);
            } finally {
                CursorUtils.closeQuietly(cursor);
            }
        }
        return result;
    }

    public long count() {
        if (table == null || !table.tableIsExist()) return 0;
        DbModelSelector dmSelector = this.select("count(\"" + table.getId().getName() + "\") as count");
        DbModel firstModel = dmSelector.findFirst();
        if (firstModel != null) {
            return firstModel.getLong("count");
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        getTempSql(result, getColumns());
        if (limit > 0) {
            result.append(" LIMIT ").append(limit);
            result.append(" OFFSET ").append(offset);
        }
        return result.toString();
    }

    private String getColumns() {
        if (table == null || ObjectJudge.isNullOrEmpty(table.getColumnMap())) {
            return "*";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, ColumnEntity> entry : table.getColumnMap().entrySet()) {
            sb.append(MessageFormat.format("[{0}],", entry.getKey()));
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return "*";
        }
    }

    private void getTempSql(StringBuilder result, String columns) {
        result.append("SELECT ");
        result.append(columns);
        result.append(" FROM ").append("\"").append(table.getName()).append("\"");
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            result.append(" WHERE ").append(whereBuilder.toString());
        }
        if (orderByList != null && orderByList.size() > 0) {
            result.append(" ORDER BY ");
            for (OrderBy orderBy : orderByList) {
                result.append(orderBy.toString()).append(',');
            }
            result.deleteCharAt(result.length() - 1);
        }
    }

    public static class OrderBy {
        private String columnName;
        private boolean desc;

        public OrderBy(String columnName) {
            this.columnName = columnName;
        }

        public OrderBy(String columnName, boolean desc) {
            this.columnName = columnName;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "\"" + columnName + "\"" + (desc ? " DESC" : " ASC");
        }
    }

    public T getEntity() {
        return JsonUtils.newNull(table.getEntityType());
    }
}
