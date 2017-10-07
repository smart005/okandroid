package com.cloud.core.db.converter;

import android.text.TextUtils;

import com.cloud.core.db.sqlite.ColumnDbType;
import com.cloud.core.logger.Logger;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:27
 */
public final class ColumnConverterFactory {

    private ColumnConverterFactory() {
    }

    public static ColumnConverter getColumnConverter(Class columnType) {
        ColumnConverter result = null;
        if (columnType == null) {
            return null;
        }
        if (TextUtils.isEmpty(columnType.getName())) {
            return null;
        }
        if (columnType_columnConverter_map == null) {
            columnType_columnConverter_map = new ConcurrentHashMap<String, ColumnConverter>();
        }
        if (columnType_columnConverter_map.containsKey(columnType.getName())) {
            result = columnType_columnConverter_map.get(columnType.getName());
        } else if (ColumnConverter.class.isAssignableFrom(columnType)) {
            try {
                ColumnConverter columnConverter = (ColumnConverter) columnType.newInstance();
                if (columnConverter != null) {
                    columnType_columnConverter_map.put(columnType.getName(), columnConverter);
                }
                result = columnConverter;
            } catch (Throwable ex) {
                Logger.L.error(ex.getMessage(), ex);
            }
        }
        if (result == null) {
            return null;
        }
        return result;
    }

    public static ColumnDbType getDbColumnType(Class columnType) {
        ColumnConverter converter = getColumnConverter(columnType);
        if (converter == null) {
            return null;
        } else {
            return converter.getColumnDbType();
        }
    }

    public static void registerColumnConverter(Class columnType, ColumnConverter columnConverter) {
        if (columnType == null ||
                columnConverter == null ||
                TextUtils.isEmpty(columnType.getName())) {
            return;
        }
        if (columnType_columnConverter_map == null) {
            columnType_columnConverter_map = new ConcurrentHashMap<String, ColumnConverter>();
        }
        columnType_columnConverter_map.put(columnType.getName(), columnConverter);
    }

    public static boolean isSupportColumnConverter(Class columnType) {
        if (columnType == null || TextUtils.isEmpty(columnType.getName())) {
            return false;
        }
        if (columnType_columnConverter_map == null) {
            columnType_columnConverter_map = new ConcurrentHashMap<String, ColumnConverter>();
        }
        if (columnType_columnConverter_map.containsKey(columnType.getName())) {
            return true;
        } else if (ColumnConverter.class.isAssignableFrom(columnType)) {
            try {
                ColumnConverter columnConverter = (ColumnConverter) columnType.newInstance();
                if (columnConverter != null) {
                    columnType_columnConverter_map.put(columnType.getName(), columnConverter);
                }
                return columnConverter == null;
            } catch (Throwable e) {
                Logger.L.error(e);
            }
        }
        return false;
    }

    private static ConcurrentHashMap<String, ColumnConverter> columnType_columnConverter_map;

    static {
        columnType_columnConverter_map = new ConcurrentHashMap<String, ColumnConverter>();

        BooleanColumnConverter booleanColumnConverter = new BooleanColumnConverter();
        columnType_columnConverter_map.put(boolean.class.getName(), booleanColumnConverter);
        columnType_columnConverter_map.put(Boolean.class.getName(), booleanColumnConverter);

        ByteArrayColumnConverter byteArrayColumnConverter = new ByteArrayColumnConverter();
        columnType_columnConverter_map.put(byte[].class.getName(), byteArrayColumnConverter);

        ByteColumnConverter byteColumnConverter = new ByteColumnConverter();
        columnType_columnConverter_map.put(byte.class.getName(), byteColumnConverter);
        columnType_columnConverter_map.put(Byte.class.getName(), byteColumnConverter);

        CharColumnConverter charColumnConverter = new CharColumnConverter();
        columnType_columnConverter_map.put(char.class.getName(), charColumnConverter);
        columnType_columnConverter_map.put(Character.class.getName(), charColumnConverter);

        DateColumnConverter dateColumnConverter = new DateColumnConverter();
        columnType_columnConverter_map.put(Date.class.getName(), dateColumnConverter);

        DoubleColumnConverter doubleColumnConverter = new DoubleColumnConverter();
        columnType_columnConverter_map.put(double.class.getName(), doubleColumnConverter);
        columnType_columnConverter_map.put(Double.class.getName(), doubleColumnConverter);

        FloatColumnConverter floatColumnConverter = new FloatColumnConverter();
        columnType_columnConverter_map.put(float.class.getName(), floatColumnConverter);
        columnType_columnConverter_map.put(Float.class.getName(), floatColumnConverter);

        IntegerColumnConverter integerColumnConverter = new IntegerColumnConverter();
        columnType_columnConverter_map.put(int.class.getName(), integerColumnConverter);
        columnType_columnConverter_map.put(Integer.class.getName(), integerColumnConverter);

        LongColumnConverter longColumnConverter = new LongColumnConverter();
        columnType_columnConverter_map.put(long.class.getName(), longColumnConverter);
        columnType_columnConverter_map.put(Long.class.getName(), longColumnConverter);

        ShortColumnConverter shortColumnConverter = new ShortColumnConverter();
        columnType_columnConverter_map.put(short.class.getName(), shortColumnConverter);
        columnType_columnConverter_map.put(Short.class.getName(), shortColumnConverter);

        SqlDateColumnConverter sqlDateColumnConverter = new SqlDateColumnConverter();
        columnType_columnConverter_map.put(java.sql.Date.class.getName(), sqlDateColumnConverter);

        StringColumnConverter stringColumnConverter = new StringColumnConverter();
        columnType_columnConverter_map.put(String.class.getName(), stringColumnConverter);
    }
}
