package com.example.notificationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    String  CHANNEL_ID="class_channel";
    Button btnNotify;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotify=findViewById(R.id.btnNotify);
        createNotificationChannel();
        btnNotify.setOnClickListener(v->showNotification());
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Class Remainder",
                            NotificationManager.IMPORTANCE_HIGH
                    );
            NotificationManager manager=
                    getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    private void showNotification(){
        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Mobile Application Development")
                        .setContentText("Today's class starts at 10.20AM")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);
        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS)
                    !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100
                );
                return;
            }
        }
        manager.notify(101, builder.build());

    }
}