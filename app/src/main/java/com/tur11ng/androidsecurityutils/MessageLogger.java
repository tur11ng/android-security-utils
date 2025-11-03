package com.tur11ng.androidsecurityutils;

import android.os.Message;
import android.os.Bundle;
import android.util.Log;

public class MessageLogger {

    private static String TAG = "MessageLogger";

    /**
     * Logs all available information from an android.os.Message in a single Log call.
     *
     * @param tagSuffix suffix appended to the base TAG to help distinguish callers
     * @param message the Message to log
     */
    public static void log(String tagSuffix, Message message) {
        TAG = TAG + ":" + tagSuffix;

        StringBuilder sb = new StringBuilder();
        sb.append("---- Message Information ----\n");

        if (message == null) {
            sb.append("Message is null\n");
            Log.i(TAG, sb.toString());
            return;
        }

        sb.append("what: ").append(message.what).append("\n");
        sb.append("arg1: ").append(message.arg1).append("\n");
        sb.append("arg2: ").append(message.arg2).append("\n");

        Object obj = message.obj;
        sb.append("obj: ").append(obj).append(" (")
                .append(obj != null ? obj.getClass().getSimpleName() : "null")
                .append(")\n");

        sb.append("when: ").append(message.getWhen()).append("\n");

        if (message.getTarget() != null) {
            sb.append("target: ").append(message.getTarget().getClass().getName()).append("\n");
        } else {
            sb.append("target: null\n");
        }

        if (message.getCallback() != null) {
            sb.append("callback: ").append(message.getCallback().getClass().getName()).append("\n");
        } else {
            sb.append("callback: null\n");
        }

        Bundle data = message.getData();
        if (data != null && !data.isEmpty()) {
            sb.append("data:\n");
            for (String key : data.keySet()) {
                Object value = data.get(key);
                sb.append("  ")
                        .append(key)
                        .append(" = ")
                        .append(value)
                        .append(" (")
                        .append(value != null ? value.getClass().getSimpleName() : "null")
                        .append(")\n");
            }
        } else {
            sb.append("data: none\n");
        }

        sb.append("---- End of Message ----");

        Log.i(TAG, sb.toString());
    }
}

