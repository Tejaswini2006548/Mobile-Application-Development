
package com.example.studentprofileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private TextView studentName;
    private TextView rollNumber;
    private TextView branch;
    private TextView section;
    private TextView cgpa;
    private TextView language;
    private TextView careerGoal;

    private ImageView profileImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the student profile layout
        setContentView(R.layout.activity_main);

        // Initialize ImageView
        profileImage = findViewById(R.id.profile);

        // Initialize all TextViews
        studentName = findViewById(R.id.studentname);
        rollNumber = findViewById(R.id.rollnumber);
        branch = findViewById(R.id.Branch);
        section = findViewById(R.id.Section);
        cgpa = findViewById(R.id.Cgpa);
        language = findViewById(R.id.Language);
        careerGoal = findViewById(R.id.careergoal);

        // Display student information
        studentName.setText(R.string.student_name);
        rollNumber.setText(R.string.roll_number);
        branch.setText(R.string.branch);
        section.setText(R.string.section);
        cgpa.setText(R.string.cgpa);
        language.setText(R.string.programming_language);
        careerGoal.setText(R.string.career_goal);

        // Display profile photograph
        profileImage.setImageResource(R.drawable.profile);
    }
}
