package com.tur11ng.androidsecurityutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Utility class for logging detailed information when a BroadcastReceiver receives an Intent,
 * including result data for ordered broadcasts.
 */
public class BroadcastReceiverLogger {

    private static final String TAG = "BroadcastReceiverLogger";

    /**
     * Logs all available information from a BroadcastReceiver event and its Intent.
     *
     * @param receiver the BroadcastReceiver instance
     * @param context  the Context in which the receiver is running
     * @param intent   the Intent received
     */
    public static void log(String tag, android.content.BroadcastReceiver receiver, Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Broadcast Receiver Triggered ----\n");

        // Receiver info
        sb.append("Receiver Class: ")
                .append(receiver != null ? receiver.getClass().getName() : "null")
                .append("\n");

        // Context info
        sb.append("Context: ")
                .append(context != null ? context.getClass().getName() : "null")
                .append("\n");

        // Intent info
        if (intent == null) {
            sb.append("Intent: null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        sb.append("---- Intent Details ----\n");
        sb.append("Action: ").append(intent.getAction()).append("\n");
        sb.append("Data: ").append(intent.getDataString()).append("\n");
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

        // Package
        sb.append("Package: ").append(intent.getPackage()).append("\n");

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

        // --- Ordered Broadcast Results ---
        if (receiver != null) {
            try {
                int resultCode = receiver.getResultCode();
                String resultData = receiver.getResultData();
                Bundle resultExtras = receiver.getResultExtras(false);

                sb.append("---- Broadcast Result ----\n");
                sb.append("Result Code: ").append(resultCode).append("\n");
                sb.append("Result Data: ").append(resultData).append("\n");

                if (resultExtras != null && !resultExtras.isEmpty()) {
                    sb.append("Result Extras:\n");
                    for (String key : resultExtras.keySet()) {
                        Object value = resultExtras.get(key);
                        sb.append("  ")
                                .append(key)
                                .append(" = ")
                                .append(value)
                                .append(" (")
                                .append(value != null ? value.getClass().getSimpleName() : "null")
                                .append(")\n");
                    }
                } else {
                    sb.append("Result Extras: none\n");
                }
            } catch (IllegalStateException e) {
                // Happens if this is not an ordered broadcast
                sb.append("---- Broadcast Result ----\n");
                sb.append("No ordered broadcast result available\n");
            }
        }

        sb.append("---- End of Broadcast ----");

        Log.i(TAG, sb.toString());
    }
}
