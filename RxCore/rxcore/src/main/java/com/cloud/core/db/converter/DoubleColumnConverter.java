package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class DoubleColumnConverter implements ColumnConverter<Double, Double> {
    @Override
    public Double getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return (double) 0;
        } else {
            return cursor.isNull(index) ? null : cursor.getDouble(index);
        }
    }

    @Override
    public Double fieldValue2DbValue(Double fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
