
package com.example.placementalerts;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String CHANNEL_ID = "placement_alerts_channel";
    private static final int NOTIFICATION_PERMISSION_CODE = 101;

    private EditText etTitle, etMessage;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Placement Alerts");

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);

        Button sendButton = findViewById(R.id.btnSend);

        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();

        sendButton.setOnClickListener(v -> validateAndSend());
    }

    // Validate announcement input
    private void validateAndSend() {

        String title = etTitle.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Enter announcement title");
            Toast.makeText(this,
                    "Please enter a title",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.isEmpty()) {
            etMessage.setError("Enter announcement message");
            Toast.makeText(this,
                    "Please enter a message",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= 33) {

            if (checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        NOTIFICATION_PERMISSION_CODE
                );
                return;
            }
        }

        sendNotification(title, message);
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                validateAndSend();

            } else {
                Toast.makeText(this,
                        "Notification permission denied",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    // Create notification channel
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Placement Announcements",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setDescription(
                    "Notifications for classes and placement announcements"
            );

            notificationManager.createNotificationChannel(channel);
        }
    }

    // Display notification
    private void sendNotification(String title, String message) {

        Intent intent = new Intent(
                this, AnnouncementDetailsActivity.class);

        intent.putExtra("announcement_title", title);
        intent.putExtra("announcement_message", message);

        int notificationId = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        Notification notification = builder
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(message))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(notificationId, notification);

        Toast.makeText(this,
                "Notification sent successfully!",
                Toast.LENGTH_SHORT).show();

        etTitle.setText("");
        etMessage.setText("");
    }
}
