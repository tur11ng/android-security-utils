package com.tur11ng.androidsecurityutils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class IntentLogger {

    private static String TAG = "IntentLogger";

    /**
     * Logs all available information from an Intent in a single Log call.
     *
     * @param intent the Intent to log
     */
    public static void log(String tagSuffix, Intent intent) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();

        sb.append("---- Intent Information ----\n");

        if (intent == null) {
            sb.append("Intent is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        // Action
        sb.append("Action: ").append(intent.getAction()).append("\n");

        // Data (URI)
        sb.append("Data: ").append(intent.getDataString()).append("\n");

        // Type
        sb.append("Type: ").append(intent.getType()).append("\n");

        // Categories
        if (intent.getCategories() != null) {
            sb.append("Categories:\n");
            for (String category : intent.getCategories()) {
                sb.append("  - ").append(category).append("\n");
            }
        } else {
            sb.append("Categories: none\n");
        }

        // Component
        if (intent.getComponent() != null) {
            sb.append("Component: ").append(intent.getComponent().flattenToString()).append("\n");
        }

        // Flags
        sb.append("Flags: ").append(intent.getFlags()).append("\n");

        // Extras
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {
            sb.append("Extras:\n");
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                sb.append("  ")
                        .append(key)
                        .append(" = ")
                        .append(value)
                        .append(" (")
                        .append(value != null ? value.getClass().getSimpleName() : "null")
                        .append(")\n");
            }
        } else {
            sb.append("Extras: none\n");
        }

        sb.append("---- End of Intent ----");

        // Log all at once
        Log.i(TAG, sb.toString());
    }
}
