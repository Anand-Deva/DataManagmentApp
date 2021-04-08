package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BioInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_info);

        Button InsertBioButton = findViewById(R.id.insertBio_button);
        Button ViewBioButton = findViewById(R.id.viewBio_button);

        InsertBioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), InsertBioActivity.class);
                startActivity(intent1);
            }
        });

        ViewBioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ViewBioActivity.class);
                startActivity(intent1);
            }
        });
    }
}