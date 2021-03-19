package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Button insertDocButton = findViewById(R.id.insertDoc_button);
        Button viewDocButton = findViewById(R.id.viewDoc_button);

        insertDocButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), InsertDocActivity.class);
                startActivity(intent1);
            }
        });

        viewDocButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), ViewDocActivity.class);
                startActivity(intent2);
            }
        });
    }
}