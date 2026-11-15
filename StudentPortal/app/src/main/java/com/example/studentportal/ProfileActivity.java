
package com.example.studentportal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone;
    TextView tvCompany, tvDesignation;

    Button btnLogout;

    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvCompany = findViewById(R.id.tvCompany);
        tvDesignation = findViewById(R.id.tvDesignation);
        btnLogout = findViewById(R.id.btnLogout);

        if (!sessionManager.isLoggedIn()) {
            goToLogin();
            return;
        }

        displayProfile();

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();

            Toast.makeText(this,
                    "Logged out successfully",
                    Toast.LENGTH_SHORT).show();

            goToLogin();
        });
    }

    private void displayProfile() {

        String email = sessionManager.getEmail();

        Cursor cursor = databaseHelper.getUser(email);

        try {
            if (cursor.moveToFirst()) {

                tvName.setText(cursor.getString(0));
                tvEmail.setText(cursor.getString(1));
                tvPhone.setText(cursor.getString(2));

                tvCompany.setText(
                        "Computer Science and Engineering");

                tvDesignation.setText(cursor.getString(3));

            } else {
                sessionManager.logout();
                goToLogin();
            }
        } finally {
            cursor.close();
        }
    }

    private void goToLogin() {

        Intent intent = new Intent(ProfileActivity.this,
                Login.class);

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
