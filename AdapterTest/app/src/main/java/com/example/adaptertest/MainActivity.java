package com.example.adaptertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView tvContact;
    String selectedLanguage=" ";
    String[] languages={"Java","Python","C","C++","Kotlin","JavaScript","PHP","C#"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.ListView);
        tvContact=findViewById(R.id.tvContact);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                languages
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(
                            AdapterView<?>parent,
                            View view,
                            int position,
                            long id){
                        String selectedLanguage =languages[position];
                        Toast.makeText(MainActivity.this, "selectedLanguage", Toast.LENGTH_SHORT).show();

                    }

                });
        tvContact.setText("Search: https://www.google.com" );
        Linkify.addLinks(
                tvContact,
                Linkify.WEB_URLS
        );
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedLanguage.isEmpty()){
                    String url="https://www.google.com/search?g="
                            + Uri.encode(selectedLanguage);
                    Intent intent=
                            new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(url)
                            );
                    startActivity(intent);

                }
                else{
                    Toast.makeText(MainActivity.this,"Select a language",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}