/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.core.db.table;


import android.text.TextUtils;

import com.cloud.core.db.converter.ColumnConverter;
import com.cloud.core.db.converter.ColumnConverterFactory;
import com.cloud.core.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;

public final class ColumnUtils {

    private ColumnUtils() {
    }

    private static final HashSet<Class<?>> BOOLEAN_TYPES = new HashSet<Class<?>>(2);
    private static final HashSet<Class<?>> INTEGER_TYPES = new HashSet<Class<?>>(2);
    private static final HashSet<Class<?>> AUTO_INCREMENT_TYPES = new HashSet<Class<?>>(4);

    static {
        BOOLEAN_TYPES.add(boolean.class);
        BOOLEAN_TYPES.add(Boolean.class);

        INTEGER_TYPES.add(int.class);
        INTEGER_TYPES.add(Integer.class);

        AUTO_INCREMENT_TYPES.addAll(INTEGER_TYPES);
        AUTO_INCREMENT_TYPES.add(long.class);
        AUTO_INCREMENT_TYPES.add(Long.class);
    }

    public static boolean isAutoIdType(Class<?> fieldType) {
        return AUTO_INCREMENT_TYPES.contains(fieldType);
    }

    public static boolean isInteger(Class<?> fieldType) {
        return INTEGER_TYPES.contains(fieldType);
    }

    public static boolean isBoolean(Class<?> fieldType) {
        return BOOLEAN_TYPES.contains(fieldType);
    }

    @SuppressWarnings("unchecked")
    public static Object convert2DbValueIfNeeded(Object value) {
        if (value != null) {
            Class<?> valueType = value.getClass();
            if (valueType == null) {
                return null;
            }
            ColumnConverter converter = ColumnConverterFactory.getColumnConverter(valueType);
            if (converter == null) {
                return null;
            }
            return converter.fieldValue2DbValue(value);
        }
        return null;
    }

    /* package */
    static Method findGetMethod(Class<?> entityType, Field field) {
        if (entityType == null || field == null) {
            return null;
        }
        String fieldName = field.getName();
        if (TextUtils.isEmpty(fieldName)) {
            return null;
        }
        Method getMethod = null;
        if (isBoolean(field.getType())) {
            getMethod = findBooleanGetMethod(entityType, fieldName);
        }
        if (getMethod == null) {
            String methodName = "get";
            if (fieldName.length() > 1) {
                methodName += fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            } else {
                methodName += fieldName.toUpperCase();
            }
            try {
                getMethod = entityType.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Logger.L.warn(entityType.getName() + "#" + methodName + " not exist");
            }
        }
        if (getMethod == null) {
            return findGetMethod(entityType.getSuperclass(), field);
        }
        return getMethod;
    }

    /* package */
    static Method findSetMethod(Class<?> entityType, Field field) {
        if (entityType == null || field == null) {
            return null;
        }
        String fieldName = field.getName();
        if (TextUtils.isEmpty(fieldName)) {
            return null;
        }
        Class<?> fieldType = field.getType();
        if (fieldType == null) {
            return null;
        }
        Method setMethod = null;
        if (isBoolean(fieldType)) {
            setMethod = findBooleanSetMethod(entityType, fieldName, fieldType);
        }
        if (setMethod == null) {
            String methodName = "set";
            if (fieldName.length() > 1) {
                methodName += fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            } else {
                methodName += fieldName.toUpperCase();
            }
            try {
                setMethod = entityType.getDeclaredMethod(methodName, fieldType);
            } catch (NoSuchMethodException e) {
                Logger.L.warn(entityType.getName() + "#" + methodName + " not exist");
            }
        }
        if (setMethod == null) {
            return findSetMethod(entityType.getSuperclass(), field);
        }
        return setMethod;
    }

    private static Method findBooleanGetMethod(Class<?> entityType, final String fieldName) {
        if (entityType == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }
        String methodName = null;
        if (fieldName.startsWith("is")) {
            methodName = fieldName;
        } else {
            if (fieldName.length() > 1) {
                methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            } else {
                methodName = "is" + fieldName.toUpperCase();
            }
        }
        try {
            return entityType.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            Logger.L.warn(entityType.getName() + "#" + methodName + " not exist");
        }
        return null;
    }

    private static Method findBooleanSetMethod(Class<?> entityType, final String fieldName, Class<?> fieldType) {
        if (entityType == null || TextUtils.isEmpty(fieldName) || fieldType == null) {
            return null;
        }
        String methodName = null;
        if (fieldName.startsWith("is")) {
            if (fieldName.length() > 3) {
                methodName = "set" + fieldName.substring(2, 3).toUpperCase() + fieldName.substring(3);
            } else {
                methodName = "set" + fieldName.toUpperCase();
            }
        } else {
            if (fieldName.length() > 1) {
                methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            } else {
                methodName = "set" + fieldName.toUpperCase();
            }
        }
        try {
            return entityType.getDeclaredMethod(methodName, fieldType);
        } catch (NoSuchMethodException e) {
            Logger.L.warn(entityType.getName() + "#" + methodName + " not exist");
        }
        return null;
    }

}