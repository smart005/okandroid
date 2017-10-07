package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class BooleanColumnConverter implements ColumnConverter<Boolean, Integer> {
    @Override
    public Boolean getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return false;
        }
        return cursor.isNull(index) ? null : cursor.getInt(index) == 1;
    }

    @Override
    public Integer fieldValue2DbValue(Boolean fieldValue) {
        return fieldValue ? 1 : 0;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
