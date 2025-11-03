package com.tur11ng.androidsecurityutils;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;

public class IntentReceiverActivity extends ComponentActivity {
    private static final String TAG = "IntentReceiverActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent receivedIntent = getIntent();
        IntentLogger.log(TAG, receivedIntent);
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        Intent receivedIntent = getIntent();
        IntentLogger.log(TAG, receivedIntent);
    }
}