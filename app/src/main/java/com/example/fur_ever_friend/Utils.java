package com.example.fur_ever_friend;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class Utils {
    public static final String TAG= "FUREVER";
    public static void showNotification(Context context, String title, String body){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"Channel1");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationCompat.BigTextStyle bigTextStyle=new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(title);
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.setSummaryText(title);

        builder.setStyle(bigTextStyle);

        NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId="channel1";
            NotificationChannel channel=new NotificationChannel(channelId,"fur_ever_friend_channel",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);


        }
        manager.notify(new Random().nextInt(),builder.build());
    }

}
