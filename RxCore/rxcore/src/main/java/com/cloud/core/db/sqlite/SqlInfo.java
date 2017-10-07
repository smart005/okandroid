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

package com.cloud.core.db.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.cloud.core.db.KeyValue;
import com.cloud.core.db.converter.ColumnConverter;
import com.cloud.core.db.converter.ColumnConverterFactory;
import com.cloud.core.db.table.ColumnUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;

public final class SqlInfo {

    private String sql;
    private List<KeyValue> bindArgs;
    private boolean flag = true;

    public SqlInfo() {
    }

    public SqlInfo(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addBindArg(KeyValue kv) {
        if (bindArgs == null) {
            bindArgs = new ArrayList<KeyValue>();
        }
        bindArgs.add(kv);
    }

    public void addBindArgs(List<KeyValue> bindArgs) {
        if (this.bindArgs == null) {
            this.bindArgs = bindArgs;
        } else {
            this.bindArgs.addAll(bindArgs);
        }
    }

    public SQLiteStatement buildStatement(SQLiteDatabase database) {
        SQLiteStatement result = database.compileStatement(sql);
        if (result == null) {
            return null;
        }
        if (bindArgs != null) {
            for (int i = 1; i < bindArgs.size() + 1; i++) {
                KeyValue kv = bindArgs.get(i - 1);
                Object value = ColumnUtils.convert2DbValueIfNeeded(kv.value);
                if (value == null) {
                    result.bindNull(i);
                } else {
                    if (value.getClass() == null) {
                        return null;
                    }
                    ColumnConverter converter = ColumnConverterFactory.getColumnConverter(value.getClass());
                    if (converter == null) {
                        return null;
                    }
                    ColumnDbType type = converter.getColumnDbType();
                    if (type == null) {
                        return null;
                    }
                    switch (type) {
                        case INTEGER:
                            result.bindLong(i, ((Number) value).longValue());
                            break;
                        case REAL:
                            result.bindDouble(i, ((Number) value).doubleValue());
                            break;
                        case TEXT:
                            result.bindString(i, value.toString());
                            break;
                        case BLOB:
                            result.bindBlob(i, (byte[]) value);
                            break;
                        default:
                            result.bindNull(i);
                            break;
                    }
                }
            }
        }
        return result;
    }

    public Object[] getBindArgs() {
        Object[] result = null;
        if (bindArgs != null) {
            result = new Object[bindArgs.size()];
            for (int i = 0; i < bindArgs.size(); i++) {
                result[i] = ColumnUtils.convert2DbValueIfNeeded(bindArgs.get(i).value);
            }
        }
        return result;
    }

    public String[] getBindArgsAsStrArray() {
        String[] result = null;
        if (bindArgs != null) {
            result = new String[bindArgs.size()];
            for (int i = 0; i < bindArgs.size(); i++) {
                Object value = ColumnUtils.convert2DbValueIfNeeded(bindArgs.get(i).value);
                result[i] = value == null ? null : value.toString();
            }
        }
        return result;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
