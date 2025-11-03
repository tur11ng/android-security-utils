package com.tur11ng.androidsecurityutils.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tur11ng.androidsecurityutils.BundleLogger;
import com.tur11ng.androidsecurityutils.IntentLogger;
import com.tur11ng.androidsecurityutils.MainActivity;
import com.tur11ng.androidsecurityutils.MessageLogger;

/**
 * HypotheticalService is an example service that demonstrates:
 * - Handling bound service connections
 * - Two-way communication using Messenger
 * - Processing different types of messages
 * - Replying to client requests
 * - Logging all received data for security analysis
 *
 * This service can be used with ServiceActivity to test service communication patterns.
 */
public class HypotheticalService extends Service {

    private static final String TAG = "HypotheticalService";
    private static final String CHANNEL_ID = "HypotheticalServiceChannel";

    // Message types - must match those in ServiceActivity
    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_UNREGISTER_CLIENT = 2;
    public static final int MSG_SET_VALUE = 3;
    public static final int MSG_GET_VALUE = 4;
    public static final int MSG_REPLY_VALUE = 5;

    // Service state
    private int mValue = 0;
    private int mClientCount = 0;

    // Messenger for receiving messages from clients
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Handler for incoming messages from clients
     */
    private class IncomingHandler extends Handler {
        public IncomingHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i(TAG, "Received message: what=" + msg.what);
            MessageLogger.log("ServiceReceived", msg);

            // Log bundle data if present
            if (msg.getData() != null && !msg.getData().isEmpty()) {
                BundleLogger.log("ServiceMessageBundle", msg.getData());
            }

            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    handleRegisterClient(msg);
                    break;

                case MSG_UNREGISTER_CLIENT:
                    handleUnregisterClient(msg);
                    break;

                case MSG_SET_VALUE:
                    handleSetValue(msg);
                    break;

                case MSG_GET_VALUE:
                    handleGetValue(msg);
                    break;

                default:
                    Log.w(TAG, "Unknown message type: " + msg.what);
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Handle client registration
     */
    private void handleRegisterClient(Message msg) {
        mClientCount++;
        Log.i(TAG, "Client registered. Total clients: " + mClientCount);

        // Send acknowledgment back to client
        sendReply(msg, MSG_REPLY_VALUE, 0, "Client registered successfully");
    }

    /**
     * Handle client unregistration
     */
    private void handleUnregisterClient(Message msg) {
        mClientCount--;
        Log.i(TAG, "Client unregistered. Remaining clients: " + mClientCount);

        if (mClientCount <= 0) {
            Log.i(TAG, "No more clients, service may stop soon");
        }
    }

    /**
     * Handle setting a value
     */
    private void handleSetValue(Message msg) {
        mValue = msg.arg1;
        Log.i(TAG, "Value set to: " + mValue);

        // Process bundle data if present
        Bundle data = msg.getData();
        if (data != null) {
            String operation = data.getString("operation", "unknown");
            Log.i(TAG, "Operation: " + operation);
        }

        // Send confirmation back to client
        sendReply(msg, MSG_REPLY_VALUE, mValue, "Value set successfully");
    }

    /**
     * Handle getting the current value
     */
    private void handleGetValue(Message msg) {
        Log.i(TAG, "Value requested: " + mValue);

        // Send the current value back to client
        sendReply(msg, MSG_REPLY_VALUE, mValue, "Current value retrieved");
    }

    /**
     * Send a reply message back to the client
     */
    private void sendReply(Message originalMsg, int what, int value, String status) {
        if (originalMsg.replyTo == null) {
            Log.w(TAG, "No reply messenger provided, cannot send reply");
            return;
        }

        try {
            Message reply = Message.obtain(null, what, value, 0);

            Bundle replyData = new Bundle();
            replyData.putString("status", status);
            replyData.putLong("server_time", System.currentTimeMillis());
            replyData.putInt("client_count", mClientCount);
            replyData.putInt("current_value", mValue);
            reply.setData(replyData);

            MessageLogger.log("ServiceReply", reply);

            originalMsg.replyTo.send(reply);
            Log.i(TAG, "Reply sent: " + status);
        } catch (RemoteException e) {
            Log.e(TAG, "Error sending reply: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "HypotheticalService created");

        // Create notification channel for foreground service (Android O+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Hypothetical Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Channel for HypotheticalService");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service bound");
        IntentLogger.log("ServiceBind", intent);

        // Log any extras in the bind intent
        if (intent != null && intent.getExtras() != null) {
            BundleLogger.log("BindExtras", intent.getExtras());
        }

        // Return the messenger's binder for client communication
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service started with startId: " + startId);
        IntentLogger.log("ServiceStart", intent);

        if (intent != null) {
            // Log all extras
            if (intent.getExtras() != null) {
                BundleLogger.log("StartExtras", intent.getExtras());
            }

            // Process the intent action
            String action = intent.getAction();
            if (action != null) {
                Log.i(TAG, "Processing action: " + action);

                switch (action) {
                    case "com.tur11ng.ACTION_PROCESS_DATA":
                        processData(intent);
                        break;

                    case "com.tur11ng.ACTION_START_FOREGROUND":
                        startForeground(intent);
                        break;

                    case "com.example.ACTION_SYNC":
                        performSync(intent);
                        break;

                    default:
                        Log.i(TAG, "Unknown action: " + action);
                }
            }
        }

        // Return START_STICKY to restart service if killed
        return START_STICKY;
    }

    /**
     * Process data from intent
     */
    private void processData(Intent intent) {
        String data = intent.getStringExtra("data_key");
        String operation = intent.getStringExtra("operation");
        int timeout = intent.getIntExtra("timeout", 0);

        Log.i(TAG, "Processing data: " + data + ", operation: " + operation + ", timeout: " + timeout);

        Bundle extraBundle = intent.getBundleExtra("extra_bundle");
        if (extraBundle != null) {
            BundleLogger.log("ExtraBundle", extraBundle);
        }
    }

    /**
     * Start service in foreground mode
     */
    private void startForeground(Intent intent) {
        String title = intent.getStringExtra("notification_title");
        String text = intent.getStringExtra("notification_text");

        if (title == null) title = "Hypothetical Service";
        if (text == null) text = "Service is running";

        Log.i(TAG, "Starting foreground service with notification");

        // Create intent for when notification is tapped
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    ? PendingIntent.FLAG_IMMUTABLE
                    : 0
        );

        // Build notification
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        Notification notification = builder
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .build();

        // Start as foreground service
        startForeground(1, notification);
    }

    /**
     * Perform synchronization
     */
    private void performSync(Intent intent) {
        String syncType = intent.getStringExtra("sync_type");
        String priority = intent.getStringExtra("priority");

        Log.i(TAG, "Performing sync: type=" + syncType + ", priority=" + priority);

        // Simulate sync operation
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Log.i(TAG, "Sync completed");
        }, 2000);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service unbound");
        IntentLogger.log("ServiceUnbind", intent);

        // Return true to receive onRebind() calls
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "Service rebound");
        IntentLogger.log("ServiceRebind", intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "HypotheticalService destroyed. Clients served: " + mClientCount);
    }
}

