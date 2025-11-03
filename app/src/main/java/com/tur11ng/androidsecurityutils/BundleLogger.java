package com.tur11ng.androidsecurityutils;

import android.os.Bundle;
import android.util.Log;

import java.util.Set;

public class BundleLogger {

    private static String TAG = "BundleLogger";

    /**
     * Logs all available information from a Bundle in a single Log call.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param bundle the Bundle to log
     */
    public static void log(String tagSuffix, Bundle bundle) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- Bundle Information ----\n");

        if (bundle == null) {
            sb.append("Bundle is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        if (bundle.isEmpty()) {
            sb.append("Bundle is empty\n");
            Log.i(TAG, sb.toString());
            return;
        }

        sb.append("Size: ").append(bundle.size()).append("\n");

        Set<String> keys = bundle.keySet();
        if (keys != null && !keys.isEmpty()) {
            sb.append("Contents:\n");
            for (String key : keys) {
                Object value = bundle.get(key);
                sb.append("  ")
                        .append(key)
                        .append(" = ")
                        .append(value)
                        .append(" (")
                        .append(value != null ? value.getClass().getSimpleName() : "null")
                        .append(")\n");
            }
        } else {
            sb.append("Contents: none\n");
        }

        sb.append("---- End of Bundle ----");

        Log.i(TAG, sb.toString());
    }
}

