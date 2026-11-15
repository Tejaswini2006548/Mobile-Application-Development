
package com.example.placementbroadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String ACTION_PLACEMENT_UPDATE =
            "com.example.placementbroadcastreceiver.PLACEMENT_UPDATE";

    private EditText etCompany, etRole, etPackage;
    private TextView tvReceiverStatus, tvCompany, tvRole, tvPackage;

    private boolean receiverRegistered = false;

    // Broadcast Receiver
    private final BroadcastReceiver placementReceiver =
            new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    if (ACTION_PLACEMENT_UPDATE.equals(intent.getAction())) {

                        String company = intent.getStringExtra("company");
                        String role = intent.getStringExtra("role");
                        String packageDetails =
                                intent.getStringExtra("package");

                        tvCompany.setText("Company: " + company);
                        tvRole.setText("Role: " + role);
                        tvPackage.setText("Package: " + packageDetails);

                        tvReceiverStatus.setText("Placement update received!");

                        Toast.makeText(MainActivity.this,
                                "Placement update received",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Placement Broadcast");

        etCompany = findViewById(R.id.etCompany);
        etRole = findViewById(R.id.etRole);
        etPackage = findViewById(R.id.etPackage);

        tvReceiverStatus = findViewById(R.id.tvReceiverStatus);
        tvCompany = findViewById(R.id.tvCompany);
        tvRole = findViewById(R.id.tvRole);
        tvPackage = findViewById(R.id.tvPackage);

        Button sendButton = findViewById(R.id.btnSend);

        sendButton.setOnClickListener(v -> sendPlacementUpdate());
    }

    // Register receiver while Activity is visible
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter =
                new IntentFilter(ACTION_PLACEMENT_UPDATE);

        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(
                    placementReceiver,
                    filter,
                    Context.RECEIVER_NOT_EXPORTED
            );
        } else {
            registerReceiver(placementReceiver, filter);
        }

        receiverRegistered = true;
    }

    // Unregister receiver when Activity is no longer visible
    @Override
    protected void onStop() {
        if (receiverRegistered) {
            unregisterReceiver(placementReceiver);
            receiverRegistered = false;
        }

        super.onStop();
    }

    // Send app-scoped broadcast
    private void sendPlacementUpdate() {

        String company = etCompany.getText().toString().trim();
        String role = etRole.getText().toString().trim();
        String packageDetails =
                etPackage.getText().toString().trim();

        if (company.isEmpty()) {
            etCompany.setError("Enter company name");
            Toast.makeText(this,
                    "Please enter company name",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (role.isEmpty()) {
            etRole.setError("Enter job role");
            Toast.makeText(this,
                    "Please enter job role",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (packageDetails.isEmpty()) {
            etPackage.setError("Enter package details");
            Toast.makeText(this,
                    "Please enter package details",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(ACTION_PLACEMENT_UPDATE);

        // Restrict broadcast to this application
        intent.setPackage(getPackageName());

        intent.putExtra("company", company);
        intent.putExtra("role", role);
        intent.putExtra("package", packageDetails);

        sendBroadcast(intent);

        Toast.makeText(this,
                "Placement update broadcast sent",
                Toast.LENGTH_SHORT).show();
    }
}
