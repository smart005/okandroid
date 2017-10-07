package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class IntegerColumnConverter implements ColumnConverter<Integer, Integer> {
    @Override
    public Integer getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return 0;
        } else {
            return cursor.isNull(index) ? null : cursor.getInt(index);
        }
    }

    @Override
    public Integer fieldValue2DbValue(Integer fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
