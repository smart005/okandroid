package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class StringColumnConverter implements ColumnConverter<String, String> {
    @Override
    public String getFieldValue(final Cursor cursor, int index) {
        if (cursor == null || index < 0) {
            return "";
        } else {
            return cursor.isNull(index) ? null : cursor.getString(index);
        }
    }

    @Override
    public String fieldValue2DbValue(String fieldValue) {
        return fieldValue == null ? "" : fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
