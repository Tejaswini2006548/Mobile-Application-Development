
package com.example.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText etFullName, etEmail, etMobile;
    EditText etDesignation, etPassword, etConfirmPassword;

    Button btnRegister;
    TextView tvSignIn;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseHelper = new DatabaseHelper(this);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etDesignation = findViewById(R.id.etDesignation);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnRegister.setOnClickListener(v -> registerUser());

        tvSignIn.setOnClickListener(v -> finish());
    }

    private void registerUser() {

        String name = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString()
                .trim().toLowerCase();
        String mobile = etMobile.getText().toString().trim();
        String designation = etDesignation.getText()
                .toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword =
                etConfirmPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() ||
                mobile.isEmpty() || designation.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {

            Toast.makeText(this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            etFullName.setError("Enter a valid name");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            return;
        }

        if (!mobile.matches("[6-9][0-9]{9}")) {
            etMobile.setError("Enter a valid 10-digit mobile number");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(
                    "Password must contain at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        boolean registered = databaseHelper.registerUser(
                name, email, mobile, designation, password);

        if (registered) {

            Toast.makeText(this,
                    "Registration successful. Please login.",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(SignUpActivity.this,
                    Login.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this,
                    "Registration failed. Email may already exist.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
