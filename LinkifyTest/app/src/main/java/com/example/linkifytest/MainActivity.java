package com.example.linkifytest;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvContact=findViewById(R.id.tvContact);
        Linkify.addLinks(tvContact,Linkify.PHONE_NUMBERS);
    }
}
