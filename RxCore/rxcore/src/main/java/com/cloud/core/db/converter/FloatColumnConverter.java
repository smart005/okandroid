package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class FloatColumnConverter implements ColumnConverter<Float, Float> {
    @Override
    public Float getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return (float) 0;
        } else {
            return cursor.isNull(index) ? null : cursor.getFloat(index);
        }
    }

    @Override
    public Float fieldValue2DbValue(Float fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
