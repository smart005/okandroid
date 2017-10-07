package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class ShortColumnConverter implements ColumnConverter<Short, Short> {
    @Override
    public Short getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return 0;
        } else {
            return cursor.isNull(index) ? null : cursor.getShort(index);
        }
    }

    @Override
    public Short fieldValue2DbValue(Short fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
