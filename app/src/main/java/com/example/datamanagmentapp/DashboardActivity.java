package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Button IdentityButton = findViewById(R.id.identity_button);
        Button BioInfoButton = findViewById(R.id.bioinfo_button);
        Button BodyMeasurmentButton = findViewById(R.id.bodyMeasurments_button);

        IdentityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), IdentityActivity.class);
                startActivity(intent1);
            }
        });

        BioInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), BioInfoActivity.class);
                startActivity(intent1);
            }
        });

        BodyMeasurmentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent1 = new Intent(getApplicationContext(), BodyMeasurmentActivity.class);
                startActivity(intent1);
                

                /*

                Intent intent = new Intent().setClassName("com.example.testapp01",
                                                            "com.example.testapp01.SecondActivity");
                intent.putExtra("EXTRA_INTENT_INFO", "xyz001");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                 */
            }
        });
    }
}