package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BodyMeasurmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_measurment);
        Button InsertMeasurementButton = findViewById(R.id.insertMeasurment_button);
        Button ViewMeasurementButton = findViewById(R.id.viewMeasurment_button);

        InsertMeasurementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), InsertBodyMeasurementActivity.class);
                startActivity(intent1);
            }
        });

        ViewMeasurementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ViewBodyMeasurementActivity.class);
                startActivity(intent1);
            }
        });
    }
}