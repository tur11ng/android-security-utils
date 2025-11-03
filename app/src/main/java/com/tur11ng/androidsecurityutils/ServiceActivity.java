package com.tur11ng.androidsecurityutils;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ServiceActivity demonstrates various ways to interact with Android Services:
 * - Starting a Service with Intent extras
 * - Binding to a Service
 * - Communicating with a Service using Messenger
 * - Receiving replies from a Service
 * - Sending data to external/hypothetical services
 */
public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ServiceActivity";

    // Message types for service communication
    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_UNREGISTER_CLIENT = 2;
    public static final int MSG_SET_VALUE = 3;
    public static final int MSG_GET_VALUE = 4;
    public static final int MSG_REPLY_VALUE = 5;

    // Messenger for communicating with the service
    private Messenger mServiceMessenger = null;
    private boolean mBound = false;

    // Messenger for receiving replies from the service
    private final Messenger mReplyMessenger = new Messenger(new IncomingHandler());

    /**
     * Handler for incoming messages from the service
     */
    private class IncomingHandler extends Handler {
        public IncomingHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_REPLY_VALUE:
                    Log.i(TAG, "Received reply from service: " + msg.arg1);
                    MessageLogger.log("ServiceReply", msg);

                    // Log any bundle data
                    if (msg.getData() != null) {
                        BundleLogger.log("ServiceReplyBundle", msg.getData());
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * ServiceConnection for managing the connection to the bound service
     */
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i(TAG, "Service connected: " + className.flattenToString());

            // We've bound to the service
            mServiceMessenger = new Messenger(service);
            mBound = true;

            // Register this client with the service
            sendMessageToService(MSG_REGISTER_CLIENT, 0, 0);

            // Example: Send a value to the service
            sendMessageToService(MSG_SET_VALUE, 42, 0);

            // Example: Request a value from the service
            sendMessageToService(MSG_GET_VALUE, 0, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "Service disconnected: " + className.flattenToString());
            mServiceMessenger = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "ServiceActivity created");

        // Example 1: Start a simple service with data
        startServiceExample();

        // Example 2: Bind to a service for two-way communication
        bindServiceExample();

        // Example 3: Start a foreground service
        startForegroundServiceExample();

        // Example 4: Send intent to external/hypothetical service
        sendToExternalServiceExample();

        // Example 5: Send ordered broadcast to service
        sendOrderedBroadcastToServiceExample();
    }

    /**
     * Example 1: Start a service with Intent extras
     */
    private void startServiceExample() {
//        Intent serviceIntent = new Intent(this, HypotheticalService.class);
//        serviceIntent.setAction("com.tur11ng.ACTION_PROCESS_DATA");
//        serviceIntent.putExtra("data_key", "Important Data");
//        serviceIntent.putExtra("operation", "PROCESS");
//        serviceIntent.putExtra("timeout", 30000);
//
//        Bundle extraData = new Bundle();
//        extraData.putString("user_id", "user123");
//        extraData.putStringArray("tags", new String[]{"tag1", "tag2", "tag3"});
//        serviceIntent.putExtra("extra_bundle", extraData);
//
//        IntentLogger.log("StartService", serviceIntent);
//        startService(serviceIntent);

        // Example with external service (hypothetical)
//        Intent externalServiceIntent = new Intent();
//        externalServiceIntent.setComponent(new ComponentName(
//                "com.example.target.app",
//                "com.example.target.app.services.DataProcessorService"
//        ));
//        externalServiceIntent.setAction("com.example.ACTION_SYNC");
//        externalServiceIntent.putExtra("sync_type", "full");
//        externalServiceIntent.putExtra("priority", "high");
//
//        IntentLogger.log("ExternalService", externalServiceIntent);
//        try {
//            startService(externalServiceIntent);
//        } catch (SecurityException e) {
//            Log.e(TAG, "SecurityException starting external service: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception starting external service: " + e.getMessage());
//        }
    }

    /**
     * Example 2: Bind to a service for two-way communication using Messenger
     */
    private void bindServiceExample() {
//        Intent bindIntent = new Intent(this, HypotheticalService.class);
//        bindIntent.setAction("com.tur11ng.ACTION_BIND");
//
//        IntentLogger.log("BindService", bindIntent);
//        boolean bound = bindService(bindIntent, mConnection, Context.BIND_AUTO_CREATE);
//        Log.i(TAG, "Service bind initiated: " + bound);

        // Example with external service
//        Intent externalBindIntent = new Intent();
//        externalBindIntent.setComponent(new ComponentName(
//                "com.example.target.app",
//                "com.example.target.app.services.MessengerService"
//        ));
//
//        IntentLogger.log("BindExternalService", externalBindIntent);
//        try {
//            boolean bound = bindService(externalBindIntent, mConnection, Context.BIND_AUTO_CREATE);
//            Log.i(TAG, "External service bind initiated: " + bound);
//        } catch (SecurityException e) {
//            Log.e(TAG, "SecurityException binding external service: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception binding external service: " + e.getMessage());
//        }
    }

    /**
     * Example 3: Start a foreground service (Android 8.0+)
     */
    private void startForegroundServiceExample() {
//        Intent foregroundIntent = new Intent(this, HypotheticalForegroundService.class);
//        foregroundIntent.setAction("com.tur11ng.ACTION_START_FOREGROUND");
//        foregroundIntent.putExtra("notification_title", "Security Utils Active");
//        foregroundIntent.putExtra("notification_text", "Monitoring system...");
//
//        IntentLogger.log("StartForegroundService", foregroundIntent);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            startForegroundService(foregroundIntent);
//        } else {
//            startService(foregroundIntent);
//        }
    }

    /**
     * Example 4: Send intent to external/hypothetical service with comprehensive data
     */
    private void sendToExternalServiceExample() {
//        Intent externalIntent = new Intent();
//        externalIntent.setComponent(new ComponentName(
//                "TARGET_PACKAGE_NAME",
//                "TARGET_SERVICE_CLASS"
//        ));
//        externalIntent.setAction("com.example.ACTION_EXECUTE");
//
//        // Add various types of data
//        externalIntent.putExtra("string_data", "Hello from SecurityUtils");
//        externalIntent.putExtra("int_data", 12345);
//        externalIntent.putExtra("boolean_data", true);
//        externalIntent.putExtra("long_data", System.currentTimeMillis());
//
//        // Add array data
//        externalIntent.putExtra("string_array", new String[]{"item1", "item2", "item3"});
//        externalIntent.putExtra("int_array", new int[]{1, 2, 3, 4, 5});
//
//        // Add nested bundle
//        Bundle nestedBundle = new Bundle();
//        nestedBundle.putString("nested_key1", "nested_value1");
//        nestedBundle.putDouble("nested_key2", 3.14159);
//        externalIntent.putExtra("nested_bundle", nestedBundle);
//
//        // Add parcelable (Intent is Parcelable)
//        Intent innerIntent = new Intent("INNER_ACTION");
//        innerIntent.putExtra("inner_data", "data from inner intent");
//        externalIntent.putExtra("parcelable_intent", innerIntent);
//
//        IntentLogger.log("ExternalServiceWithData", externalIntent);
//
//        try {
//            startService(externalIntent);
//            Log.i(TAG, "Successfully sent intent to external service");
//        } catch (SecurityException e) {
//            Log.e(TAG, "SecurityException: " + e.getMessage());
//        } catch (IllegalStateException e) {
//            Log.e(TAG, "IllegalStateException: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
    }

    /**
     * Example 5: Send ordered broadcast that a service might receive
     */
    private void sendOrderedBroadcastToServiceExample() {
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("com.tur11ng.ACTION_SERVICE_BROADCAST");
//        broadcastIntent.putExtra("broadcast_data", "Data for service");
//        broadcastIntent.putExtra("request_id", 9876);
//
//        // Optionally set component for explicit broadcast
//        broadcastIntent.setComponent(new ComponentName(
//                "TARGET_PACKAGE_NAME",
//                "TARGET_SERVICE_OR_RECEIVER_CLASS"
//        ));
//
//        IntentLogger.log("BroadcastToService", broadcastIntent);
//
//        sendOrderedBroadcast(
//                broadcastIntent,
//                null,  // receiverPermission
//                null,  // resultReceiver
//                null,  // scheduler
//                android.app.Activity.RESULT_OK,  // initialCode
//                null,  // initialData
//                null   // initialExtras
//        );
    }

    /**
     * Send a message to the bound service
     */
    private void sendMessageToService(int what, int arg1, int arg2) {
        if (!mBound) {
            Log.w(TAG, "Service not bound, cannot send message");
            return;
        }

        try {
            Message msg = Message.obtain(null, what, arg1, arg2);
            msg.replyTo = mReplyMessenger;  // Set reply messenger to receive responses

            // Optionally add Bundle data
            Bundle data = new Bundle();
            data.putString("sender", "ServiceActivity");
            data.putLong("timestamp", System.currentTimeMillis());
            msg.setData(data);

            MessageLogger.log("SendToService", msg);

            mServiceMessenger.send(msg);
            Log.i(TAG, "Message sent to service: what=" + what);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Advanced example: Send complex data through Messenger
     */
    private void sendComplexDataToService() {
        if (!mBound) {
            Log.w(TAG, "Service not bound, cannot send complex data");
            return;
        }

        try {
            Message msg = Message.obtain(null, MSG_SET_VALUE, 0, 0);
            msg.replyTo = mReplyMessenger;

            Bundle complexData = new Bundle();
            complexData.putString("operation", "complex_operation");
            complexData.putInt("priority", 5);
            complexData.putBoolean("async", true);
            complexData.putStringArray("parameters", new String[]{"param1", "param2", "param3"});

            // Add nested bundle
            Bundle metadata = new Bundle();
            metadata.putString("user", "admin");
            metadata.putLong("session_id", 123456789L);
            complexData.putBundle("metadata", metadata);

            msg.setData(complexData);

            MessageLogger.log("ComplexDataToService", msg);
            BundleLogger.log("ComplexDataBundle", complexData);

            mServiceMessenger.send(msg);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException sending complex data: " + e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "ServiceActivity started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "ServiceActivity stopped");

        // Unbind from the service
        if (mBound) {
            // Unregister client
            sendMessageToService(MSG_UNREGISTER_CLIENT, 0, 0);

            unbindService(mConnection);
            mBound = false;
            Log.i(TAG, "Service unbound");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ServiceActivity destroyed");
    }

    /**
     * Example hypothetical service that this activity could communicate with.
     * This is for demonstration purposes and shows what the service implementation might look like.
     */
//    public static class HypotheticalService extends Service {
//
//        private final Messenger mMessenger = new Messenger(new ServiceHandler());
//        private int mValue = 0;
//
//        class ServiceHandler extends Handler {
//            public ServiceHandler() {
//                super(Looper.getMainLooper());
//            }
//
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                MessageLogger.log("ServiceReceived", msg);
//
//                switch (msg.what) {
//                    case MSG_REGISTER_CLIENT:
//                        Log.i(TAG, "Client registered");
//                        break;
//                    case MSG_UNREGISTER_CLIENT:
//                        Log.i(TAG, "Client unregistered");
//                        break;
//                    case MSG_SET_VALUE:
//                        mValue = msg.arg1;
//                        Log.i(TAG, "Value set to: " + mValue);
//                        break;
//                    case MSG_GET_VALUE:
//                        // Reply to the client
//                        Message reply = Message.obtain(null, MSG_REPLY_VALUE, mValue, 0);
//                        Bundle replyData = new Bundle();
//                        replyData.putString("status", "success");
//                        replyData.putLong("server_time", System.currentTimeMillis());
//                        reply.setData(replyData);
//
//                        try {
//                            msg.replyTo.send(reply);
//                            Log.i(TAG, "Reply sent to client with value: " + mValue);
//                        } catch (RemoteException e) {
//                            Log.e(TAG, "Error sending reply: " + e.getMessage());
//                        }
//                        break;
//                    default:
//                        super.handleMessage(msg);
//                }
//            }
//        }
//
//        @Override
//        public IBinder onBind(Intent intent) {
//            IntentLogger.log("ServiceBind", intent);
//            return mMessenger.getBinder();
//        }
//
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            IntentLogger.log("ServiceStart", intent);
//
//            if (intent != null && intent.getExtras() != null) {
//                BundleLogger.log("ServiceExtras", intent.getExtras());
//            }
//
//            return START_STICKY;
//        }
//
//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//            Log.i(TAG, "HypotheticalService destroyed");
//        }
//    }
}

