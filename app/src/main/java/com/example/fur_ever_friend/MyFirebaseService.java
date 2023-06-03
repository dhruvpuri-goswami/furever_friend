package com.example.fur_ever_friend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {

        super.onNewToken(token);
        Log.d(Utils.TAG,token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        String title=message.getNotification().getTitle();
        String content=message.getNotification().getBody();
        String data=new Gson().toJson(message.getData());
        Utils.showNotification(this,title,content);
        Log.d(Utils.TAG,data);

    }
}
