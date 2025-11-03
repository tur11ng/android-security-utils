package com.tur11ng.androidsecurityutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IntentBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "IntentBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastReceiverLogger.log("IntentBroadcastReceiver", this, context, intent);
    }
}
