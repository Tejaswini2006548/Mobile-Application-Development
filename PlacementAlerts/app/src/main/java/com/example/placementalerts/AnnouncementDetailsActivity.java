
package com.example.placementalerts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AnnouncementDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle("Announcement Details");

        TextView tvTitle = findViewById(R.id.tvDetailsTitle);
        TextView tvMessage = findViewById(R.id.tvDetailsMessage);

        String title = getIntent().getStringExtra(
                "announcement_title");

        String message = getIntent().getStringExtra(
                "announcement_message");

        tvTitle.setText(title != null ? title : "No Title");
        tvMessage.setText(message != null ? message : "No Message");
    }
}
