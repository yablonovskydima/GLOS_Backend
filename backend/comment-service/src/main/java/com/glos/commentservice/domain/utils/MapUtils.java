package com.glos.commentservice.domain.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtils
{
    public static Map<String, Object> map(Object obj) {
        if (obj == null) {
            throw new NullPointerException("obj is null");
        }
        return map(obj, new StringBuilder(), null, new HashMap<>(), new HashMap<>());
    }

    private static Map<String, Object> map(Object obj, StringBuilder prefix, String name, Map<String, Object> map, Map<Object, Boolean> visited) {
        if (obj == null) {
            return map;
        }

        if (visited.containsKey(obj) && visited.get(obj)) {
            return map;
        }

        int start = prefix.length();


        if (!prefix.isEmpty() && name != null) {
            prefix.append('.');
        }

        if (name != null) {
            prefix.append(name);
        }

        if (!isPrimitiveOrStringObject(obj.getClass(), obj)) {

            visited.put(obj, true);
        }

        if (isPrimitiveOrStringObject(obj.getClass(), obj)) {
            include(map, prefix.toString(), obj);
        } else if (isMap(obj.getClass(), obj)) {
            Map<?, ?> fieldMap = (Map<?, ?>) obj;
            final StringBuilder prefixBuilder = new StringBuilder(prefix);

            fieldMap.forEach((k, v) -> map(v, new StringBuilder(prefixBuilder).append('.').append(k), null, map, visited));
        } else if (isInlineIterableObject(obj.getClass(), obj)) {
            final Iterable<?> iterable = (Iterable<?>) obj;
            int index = 0;
            for (Object item : iterable) {
                map(item, new StringBuilder(prefix).append('[').append(index++).append(']'), null, map, visited);
            }
        } else if (isArray(obj.getClass(), obj)) {
            final int length = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                map(java.lang.reflect.Array.get(obj, i), new StringBuilder(prefix).append('[').append(i).append(']'), null, map, visited);
            }
        } else {
            mapFields(obj, prefix, map, visited);
        }

        prefix.delete(start, prefix.length());
        return map;
    }

    private static void mapFields(final Object obj, StringBuilder prefix, final Map<String, Object> map, final Map<Object, Boolean> visited) {
        try {
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                final String nameField = field.getName();
                final Object value = field.get(obj);
                map(value, prefix, nameField, map, visited);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isPrimitiveOrStringObject(Class<?> type, Object value) {
        if (value == null) {
            return false;
        }
        return value.getClass().isPrimitive()
                || value instanceof Byte
                || value instanceof Boolean
                || value instanceof Integer
                || value instanceof Short
                || value instanceof Long
                || value instanceof Float
                || value instanceof Double
                || value instanceof Character
                || value instanceof String;
    }

    private static boolean isInlineIterableObject(Class<?> type, Object value) {
        return value instanceof Iterable<?>;
    }

    private static boolean isArray(Class<?> type, Object value) {
        return type.isArray();
    }

    private static boolean isMap(Class<?> type, Object value) {
        return value instanceof Map<?, ?>;
    }

    private static Map<String, Object> include(Map<String, Object> target, String key, Object value) {
        target.put(key, value);
        return target;
    }
}


