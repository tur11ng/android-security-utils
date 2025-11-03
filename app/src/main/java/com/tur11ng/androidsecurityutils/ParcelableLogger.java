package com.tur11ng.androidsecurityutils;

import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

public class ParcelableLogger {

    private static String TAG = "ParcelableLogger";

    /**
     * Logs all available information from a Parcelable object in a single Log call.
     * Uses reflection to extract field values from the Parcelable.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param parcelable the Parcelable to log
     */
    public static void log(String tagSuffix, Parcelable parcelable) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- Parcelable Information ----\n");

        if (parcelable == null) {
            sb.append("Parcelable is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        Class<?> clazz = parcelable.getClass();
        sb.append("Class: ").append(clazz.getName()).append("\n");
        sb.append("toString(): ").append(parcelable.toString()).append("\n");

        // Log all fields using reflection
        sb.append("Fields:\n");
        logFieldsRecursively(sb, parcelable, clazz);

        sb.append("---- End of Parcelable ----");

        Log.i(TAG, sb.toString());
    }

    /**
     * Recursively logs fields from the class hierarchy
     */
    private static void logFieldsRecursively(StringBuilder sb, Object obj, Class<?> clazz) {
        if (clazz == null || clazz == Object.class) {
            return;
        }

        // First, log parent class fields
        logFieldsRecursively(sb, obj, clazz.getSuperclass());

        // Then log current class fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Skip static and synthetic fields (like CREATOR)
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                String valueStr = formatValue(value);
                sb.append("  ")
                        .append(field.getName())
                        .append(" = ")
                        .append(valueStr)
                        .append(" (")
                        .append(value != null ? value.getClass().getSimpleName() : "null")
                        .append(")\n");
            } catch (IllegalAccessException e) {
                sb.append("  ")
                        .append(field.getName())
                        .append(" = <inaccessible: ")
                        .append(e.getMessage())
                        .append(">\n");
            } catch (Exception e) {
                sb.append("  ")
                        .append(field.getName())
                        .append(" = <error: ")
                        .append(e.getMessage())
                        .append(">\n");
            }
        }
    }

    /**
     * Formats a value for logging, handling arrays and other special cases
     */
    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }

        Class<?> valueClass = value.getClass();
        if (valueClass.isArray()) {
            if (Objects.requireNonNull(valueClass.getComponentType()).isPrimitive()) {
                // Handle primitive arrays
                if (value instanceof int[]) {
                    return Arrays.toString((int[]) value);
                } else if (value instanceof long[]) {
                    return Arrays.toString((long[]) value);
                } else if (value instanceof byte[]) {
                    return Arrays.toString((byte[]) value);
                } else if (value instanceof boolean[]) {
                    return Arrays.toString((boolean[]) value);
                } else if (value instanceof float[]) {
                    return Arrays.toString((float[]) value);
                } else if (value instanceof double[]) {
                    return Arrays.toString((double[]) value);
                } else if (value instanceof char[]) {
                    return Arrays.toString((char[]) value);
                } else if (value instanceof short[]) {
                    return Arrays.toString((short[]) value);
                }
            } else {
                // Handle object arrays
                return Arrays.toString((Object[]) value);
            }
        }

        return String.valueOf(value);
    }
}
