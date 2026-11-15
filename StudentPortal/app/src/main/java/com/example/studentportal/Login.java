
package com.example.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin;
    TextView btnSignUp;

    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            openProfile();
            return;
        }

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(v -> loginUser());

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this,
                    SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {

        String email = etUserName.getText().toString()
                .trim().toLowerCase();

        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etUserName.setError("Enter a valid email");
            return;
        }

        if (databaseHelper.checkLogin(email, password)) {

            sessionManager.createSession(email);

            Toast.makeText(this,
                    "Login successful",
                    Toast.LENGTH_SHORT).show();

            openProfile();

        } else {
            Toast.makeText(this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void openProfile() {
        Intent intent = new Intent(Login.this,
                ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}

