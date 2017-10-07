package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class LongColumnConverter implements ColumnConverter<Long, Long> {
    @Override
    public Long getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return (long) 0;
        } else {
            return cursor.isNull(index) ? null : cursor.getLong(index);
        }
    }

    @Override
    public Long fieldValue2DbValue(Long fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
