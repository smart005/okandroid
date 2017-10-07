package com.cloud.core.db.converter;

import android.database.Cursor;

import com.cloud.core.db.sqlite.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午8:57
 */
public interface ColumnConverter<T, R> {

    T getFieldValue(final Cursor cursor, int index);

    R fieldValue2DbValue(T fieldValue);

    ColumnDbType getColumnDbType();
}
