package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

import java.sql.Date;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class SqlDateColumnConverter implements ColumnConverter<Date, Long> {
    @Override
    public Date getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return new Date(System.currentTimeMillis());
        } else {
            return cursor.isNull(index) ? null : new Date(cursor.getLong(index));
        }
    }

    @Override
    public Long fieldValue2DbValue(Date fieldValue) {
        if (fieldValue == null) {
            return System.currentTimeMillis();
        } else {
            return fieldValue.getTime();
        }
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
