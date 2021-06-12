package com.hust.medtech.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hust.medtech.MedTechApplication;
import com.hust.medtech.R;
import com.hust.medtech.screen.home.HomeActivity;

import java.util.Map;

public class MyFireBaseMessagingService extends FirebaseMessagingService {
    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        Log.d("FROM", remoteMessage.getFrom());
        controller(notification, data);
    }

    private void controller(RemoteMessage.Notification notification, Map<String, String> data) {
        try {
            PowerManager powerManager = (PowerManager) MedTechApplication.getCurrentAct().getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = powerManager.isScreenOn();
            if (isScreenOn) {
                MedTechApplication.getCurrentAct().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String tile = notification.getTitle();
                        String content = notification.getBody();
                        // Create Alert using Builder
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder( MedTechApplication.getCurrentAct())
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                                .setTitle(tile)
                                .setMessage(content);

// Show the alert
                        builder.show();

                    }
                });

            } else {
                sendNotification(notification, data);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void sendNotification(RemoteMessage.Notification
                                          notification, Map<String, String> data) {
        Bundle bundle = new Bundle();
        bundle.putString(FCM_PARAM, data.get(FCM_PARAM));
        bundle.putString("pushnotification", "yes");
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                "a")
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(this.getResources().getColor(R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.drawable.ic_success);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("a", CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }


}