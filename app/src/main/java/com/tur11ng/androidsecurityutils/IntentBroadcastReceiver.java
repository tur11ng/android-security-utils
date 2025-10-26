package com.tur11ng.androidsecurityutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IntentBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        IntentLogger.log("IntentBroadcastReceiver", intent);
    }
}
