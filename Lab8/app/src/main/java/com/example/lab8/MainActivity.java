package com.example.lab8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendNotification = findViewById(R.id.btn_sendNotif);
        btnSendNotification.setOnClickListener(view -> sendNotification());

    }

    private void sendNotification() {

        // API >= 26 (Oreo) must have notif channels.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "lab_8";
            CharSequence channelName = getString(R.string.channel_name);
            String channelDescription = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        @SuppressWarnings("deprecation")
        Notification notification = new Notification.Builder(this, "lab_8")
                .setContentTitle("This is a notification")
                .setContentText("You can now read this notification text")
                .setSmallIcon(R.drawable.ic_notification_bell_1)
                .setColor(getResources().getColor(R.color.killer_red))
//                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null){
            notificationManager.notify(getNotifcationID(),notification);
        }
    }

    private int getNotifcationID() { return (int) new Date().getTime(); }
}