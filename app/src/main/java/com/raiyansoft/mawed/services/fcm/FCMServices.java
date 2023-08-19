package com.raiyansoft.mawed.services.fcm;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.raiyansoft.mawed.HomeActivity;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.Map;
import java.util.Random;

public class FCMServices extends FirebaseMessagingService {
    private static final String TAG = FCMServices.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "NewToken onCreate: " + "onCreate");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: " + "onMessageReceived");
        Log.d(TAG, "onMessageReceived: " + remoteMessage);
//        Log.d(TAG, "onMessageReceived: " + remoteMessage.getNotification().getTitle());
//        Log.d(TAG, "onMessageReceived: " + remoteMessage.getNotification().getBody());
        Map<String, String> dataRecv = remoteMessage.getData();
        Log.d(TAG, "onMessageReceived: " + new Gson().toJson(dataRecv));
        Intent intent = new Intent(this, HomeActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            HelperMethods.showNotification(this,
                    new Random().nextInt(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
    //                dataRecv.get("title"),
    //                dataRecv.get("content"),
                    intent);
        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "NewToken onNewToken: " + "onNewToken");
        Log.d(TAG, "NewToken onNewToken: " + token);
        super.onNewToken(token);
    }
}