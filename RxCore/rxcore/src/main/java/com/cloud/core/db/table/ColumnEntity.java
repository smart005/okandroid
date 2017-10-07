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

import com.cloud.core.db.annotation.Column;
import com.cloud.core.db.converter.ColumnConverter;
import com.cloud.core.db.converter.ColumnConverterFactory;
import com.cloud.core.db.sqlite.ColumnDbType;
import com.cloud.core.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ColumnEntity {

    protected String name;
    private String property;
    private boolean isId;
    private boolean isAutoId;
    private boolean isIndex;

    protected Method getMethod;
    protected Method setMethod;

    protected Field columnField;
    protected ColumnConverter columnConverter;

    ColumnEntity(Class<?> entityType, Field field, Column column) {
        if (entityType != null && field != null && column != null) {
            field.setAccessible(true);
            this.columnField = field;
            this.name = column.name();
            this.property = column.property();
            this.isId = column.isId();
            this.isIndex = column.isIndex();
            Class<?> fieldType = field.getType();
            this.isAutoId = this.isId && column.autoGen() && ColumnUtils.isAutoIdType(fieldType);
            this.columnConverter = ColumnConverterFactory.getColumnConverter(fieldType);
            this.getMethod = ColumnUtils.findGetMethod(entityType, field);
            if (this.getMethod != null && !this.getMethod.isAccessible()) {
                this.getMethod.setAccessible(true);
            }
            this.setMethod = ColumnUtils.findSetMethod(entityType, field);
            if (this.setMethod != null && !this.setMethod.isAccessible()) {
                this.setMethod.setAccessible(true);
            }
        }
    }

    public void setValueFromCursor(Object entity, Cursor cursor, int index) {
        try {
            if (entity == null || cursor == null || index < 0) {
                return;
            }
            Object value = columnConverter.getFieldValue(cursor, index);
            if (value == null) return;
            if (setMethod != null) {
                try {
                    setMethod.invoke(entity, value);
                } catch (Throwable e) {
                    Logger.L.error(e.getMessage(), e);
                }
            } else {
                try {
                    this.columnField.set(entity, value);
                } catch (Throwable e) {
                    Logger.L.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public Object getColumnValue(Object entity) {
        Object fieldValue = getFieldValue(entity);
        if (fieldValue == null) {
            return null;
        }
        if (this.isAutoId && (fieldValue.equals(0L) || fieldValue.equals(0))) {
            return null;
        }
        return columnConverter.fieldValue2DbValue(fieldValue);
    }

    public void setAutoIdValue(Object entity, long value) {
        try {
            if (entity == null) {
                return;
            }
            Object idValue = value;
            if (ColumnUtils.isInteger(columnField.getType())) {
                idValue = (int) value;
            }
            if (setMethod != null) {
                try {
                    setMethod.invoke(entity, idValue);
                } catch (Throwable e) {
                    Logger.L.error(e.getMessage(), e);
                }
            } else {
                try {
                    this.columnField.set(entity, idValue);
                } catch (Throwable e) {
                    Logger.L.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public Object getFieldValue(Object entity) {
        Object fieldValue = null;
        if (entity != null) {
            if (getMethod != null) {
                try {
                    fieldValue = getMethod.invoke(entity);
                } catch (Throwable e) {
                    Logger.L.error(e.getMessage(), e);
                }
            } else {
                try {
                    fieldValue = this.columnField.get(entity);
                } catch (Throwable e) {
                    Logger.L.error(e.getMessage(), e);
                }
            }
        }
        return fieldValue;
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public boolean isId() {
        return isId;
    }

    public boolean isAutoId() {
        return isAutoId;
    }

    public boolean isIndex() {
        return isIndex;
    }

    public Field getColumnField() {
        return columnField;
    }

    public ColumnConverter getColumnConverter() {
        return columnConverter;
    }

    public ColumnDbType getColumnDbType() {
        if (columnConverter == null) {
            return null;
        } else {
            return columnConverter.getColumnDbType();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
