
package com.example.campusnavigator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnWebsite, btnMap, btnCall, btnShare, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWebsite = findViewById(R.id.btnWebsite);
        btnMap = findViewById(R.id.btnMap);
        btnCall = findViewById(R.id.btnCall);
        btnShare = findViewById(R.id.btnShare);
        btnAbout = findViewById(R.id.btnAbout);

        btnWebsite.setOnClickListener(v ->
                openLink("https://www.sahe.edu.in/"));

        btnMap.setOnClickListener(v ->
                openLink("https://www.google.com/maps/search/?api=1&query=Siddhartha+Academy+of+Higher+Education+Vijayawada"));

        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:1234567890"));
            openIntent(intent);
        });

        btnShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Campus Services Navigator\n" +
                            "Website: https://www.sahe.edu.in/\n" +
                            "Campus: Siddhartha Academy of Higher Education, Vijayawada");

            startActivity(Intent.createChooser(intent, "Share Campus Information"));
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AboutCampusActivity.class);
            startActivity(intent);
        });
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        openIntent(intent);
    }

    private void openIntent(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(
                    MainActivity.this,
                    "No compatible application is available.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
