
package com.example.studentdashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

        private TextView studentRecord;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                setTitle("Student Dashboard");

                studentRecord = findViewById(R.id.studentRecord);

                Button profileButton = findViewById(R.id.profileButton);
                Button settingsButton = findViewById(R.id.settingsButton);
                Button aboutButton = findViewById(R.id.aboutButton);
                Button popupButton = findViewById(R.id.popupButton);

                // Register Context Menu
                registerForContextMenu(studentRecord);

                // Add Fragment for Help menu
                if (savedInstanceState == null) {
                        DashboardFragment fragment = new DashboardFragment();

                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        transaction.add(fragment, "dashboard_fragment");
                        transaction.commit();
                }

                profileButton.setOnClickListener(v ->
                        showMessage("Student Profile"));

                settingsButton.setOnClickListener(v ->
                        showMessage("Settings"));

                aboutButton.setOnClickListener(v ->
                        showAbout());

                popupButton.setOnClickListener(v ->
                        showPopupMenu(popupButton));
        }

        // Options Menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.main_menu, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.menu_profile) {
                        showMessage("Student Profile");
                        return true;

                } else if (id == R.id.menu_settings) {
                        showMessage("Settings");
                        return true;

                } else if (id == R.id.menu_about) {
                        showAbout();
                        return true;

                } else if (id == R.id.menu_logout) {
                        new AlertDialog.Builder(this)
                                .setTitle("Logout")
                                .setMessage("Are you sure you want to logout?")
                                .setPositiveButton("Yes",
                                        (dialog, which) -> finish())
                                .setNegativeButton("Cancel", null)
                                .show();
                        return true;
                }

                return super.onOptionsItemSelected(item);
        }

        // Context Menu
        @Override
        public void onCreateContextMenu(ContextMenu menu,
                                        View view,
                                        ContextMenu.ContextMenuInfo menuInfo) {
                super.onCreateContextMenu(menu, view, menuInfo);

                menu.setHeaderTitle("Student Options");
                menu.add(0, 1, 0, "View Profile");
                menu.add(0, 2, 1, "Edit Student");
                menu.add(0, 3, 2, "Share Details");
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {

                if (item.getItemId() == 1) {
                        showMessage("Viewing Student Profile");
                        return true;

                } else if (item.getItemId() == 2) {
                        showEditDialog();
                        return true;

                } else if (item.getItemId() == 3) {
                        shareDetails();
                        return true;
                }

                return super.onContextItemSelected(item);
        }

        // Popup Menu
        private void showPopupMenu(View anchor) {

                PopupMenu popup = new PopupMenu(this, anchor);

                popup.getMenuInflater().inflate(
                        R.menu.popup_menu,
                        popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {

                        if (item.getItemId() == R.id.popup_edit) {
                                showEditDialog();
                                return true;

                        } else if (item.getItemId() == R.id.popup_share) {
                                shareDetails();
                                return true;
                        }

                        return false;
                });

                popup.show();
        }

        private void showEditDialog() {
                new AlertDialog.Builder(this)
                        .setTitle("Edit Student")
                        .setMessage("Student record selected for editing.")
                        .setPositiveButton("OK", null)
                        .show();
        }

        private void showAbout() {
                new AlertDialog.Builder(this)
                        .setTitle("About")
                        .setMessage("Student Dashboard Application\n"
                                + "Developed using Java and XML.")
                        .setPositiveButton("OK", null)
                        .show();
        }

        private void shareDetails() {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT,
                        "Student: Tejaswini\nRoll Number: 24EU04190"
                                + "\nAttendance: 92%");

                startActivity(Intent.createChooser(
                        intent, "Share Student Details"));
        }

        private void showMessage(String message) {
                Toast.makeText(this, message,
                        Toast.LENGTH_SHORT).show();
        }
}
