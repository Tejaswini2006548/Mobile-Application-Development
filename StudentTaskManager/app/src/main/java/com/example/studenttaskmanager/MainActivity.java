
package com.example.studenttaskmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private EditText etStudentId, etTask, etStatus, etSearch;
    private TextView tvSelected;
    private ListView listView;

    private DatabaseHelper databaseHelper;

    private ArrayList<Integer> recordIds = new ArrayList<>();
    private int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Student Task & Attendance");

        databaseHelper = new DatabaseHelper(this);

        etStudentId = findViewById(R.id.etStudentId);
        etTask = findViewById(R.id.etTask);
        etStatus = findViewById(R.id.etStatus);
        etSearch = findViewById(R.id.etSearch);

        tvSelected = findViewById(R.id.tvSelected);
        listView = findViewById(R.id.listRecords);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnViewAll = findViewById(R.id.btnViewAll);

        btnAdd.setOnClickListener(v -> addRecord());

        btnUpdate.setOnClickListener(v -> updateRecord());

        btnSearch.setOnClickListener(v -> searchRecords());

        btnDelete.setOnClickListener(v -> confirmDelete());

        btnViewAll.setOnClickListener(v -> loadRecords());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < recordIds.size()) {
                selectRecord(position);
            }
        });

        loadRecords();
    }

    // Validate input fields
    private boolean validateInputs() {

        String studentId = etStudentId.getText().toString().trim();
        String task = etTask.getText().toString().trim();
        String status = etStatus.getText().toString().trim();

        if (studentId.isEmpty()) {
            etStudentId.setError("Enter Student ID");
            etStudentId.requestFocus();
            return false;
        }

        if (task.isEmpty()) {
            etTask.setError("Enter Task Details");
            etTask.requestFocus();
            return false;
        }

        if (status.isEmpty()) {
            etStatus.setError("Enter Status");
            etStatus.requestFocus();
            return false;
        }

        return true;
    }

    // Add a new record
    private void addRecord() {

        if (!validateInputs()) {
            Toast.makeText(this,
                    "Please enter all details",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = databaseHelper.insertRecord(
                etStudentId.getText().toString().trim(),
                etTask.getText().toString().trim(),
                etStatus.getText().toString().trim()
        );

        if (result) {
            Toast.makeText(this,
                    "Record added successfully",
                    Toast.LENGTH_SHORT).show();

            clearFields();
            loadRecords();
        } else {
            Toast.makeText(this,
                    "Unable to add record",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Load and display all saved records
    private void loadRecords() {
        displayRecords(databaseHelper.getAllRecords());
    }

    // Search records
    private void searchRecords() {

        String keyword = etSearch.getText().toString().trim();

        if (keyword.isEmpty()) {
            Toast.makeText(this,
                    "Enter Student ID or Task to search",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        displayRecords(databaseHelper.searchRecords(keyword));
    }

    // Display records in ListView
    private void displayRecords(Cursor cursor) {

        ArrayList<String> recordList = new ArrayList<>();
        recordIds.clear();

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(
                            cursor.getColumnIndexOrThrow("id"));

                    String studentId = cursor.getString(
                            cursor.getColumnIndexOrThrow("student_id"));

                    String task = cursor.getString(
                            cursor.getColumnIndexOrThrow("task"));

                    String status = cursor.getString(
                            cursor.getColumnIndexOrThrow("status"));

                    recordIds.add(id);

                    recordList.add(
                            "ID: " + id +
                                    "\nStudent ID: " + studentId +
                                    "\nTask: " + task +
                                    "\nStatus: " + status
                    );
                }
            } finally {
                cursor.close();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recordList
        );

        listView.setAdapter(adapter);

        if (recordList.isEmpty()) {
            Toast.makeText(this,
                    "No records found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Select a record from the ListView
    private void selectRecord(int position) {

        int id = recordIds.get(position);

        Cursor cursor = databaseHelper.getAllRecords();

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {

                    int currentId = cursor.getInt(
                            cursor.getColumnIndexOrThrow("id"));

                    if (currentId == id) {

                        String studentId = cursor.getString(
                                cursor.getColumnIndexOrThrow("student_id"));

                        String task = cursor.getString(
                                cursor.getColumnIndexOrThrow("task"));

                        String status = cursor.getString(
                                cursor.getColumnIndexOrThrow("status"));

                        etStudentId.setText(studentId);
                        etTask.setText(task);
                        etStatus.setText(status);

                        selectedId = id;

                        tvSelected.setText("Selected Record ID: " + id);

                        Toast.makeText(this,
                                "Record selected",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    // Update selected record
    private void updateRecord() {

        if (selectedId == -1) {
            Toast.makeText(this,
                    "Select a record from the list first",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateInputs()) {
            return;
        }

        boolean result = databaseHelper.updateRecord(
                selectedId,
                etStudentId.getText().toString().trim(),
                etTask.getText().toString().trim(),
                etStatus.getText().toString().trim()
        );

        if (result) {
            Toast.makeText(this,
                    "Record updated successfully",
                    Toast.LENGTH_SHORT).show();

            clearFields();
            loadRecords();
        } else {
            Toast.makeText(this,
                    "Unable to update record",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Ask confirmation before deleting
    private void confirmDelete() {

        if (selectedId == -1) {
            Toast.makeText(this,
                    "Select a record to delete",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Delete Record")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    boolean result =
                            databaseHelper.deleteRecord(selectedId);

                    if (result) {
                        Toast.makeText(this,
                                "Record deleted successfully",
                                Toast.LENGTH_SHORT).show();

                        clearFields();
                        loadRecords();
                    } else {
                        Toast.makeText(this,
                                "Unable to delete record",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Clear input fields
    private void clearFields() {

        etStudentId.setText("");
        etTask.setText("");
        etStatus.setText("");

        selectedId = -1;
        tvSelected.setText("No record selected");
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        super.onDestroy();
    }
}
