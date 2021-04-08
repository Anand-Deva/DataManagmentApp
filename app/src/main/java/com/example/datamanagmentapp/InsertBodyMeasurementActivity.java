package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class InsertBodyMeasurementActivity extends AppCompatActivity {
    App app;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_body_measurment);

        app = RealmSingleton.getInstance().getRealm();
        String _partiton = "Body Measurement";

        EditText weightEditText = findViewById(R.id.editText_weight);
        EditText heightEditText = findViewById(R.id.editText_height);
        Button insertButton = findViewById(R.id.insert_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String weight = weightEditText.getText().toString();
                String height = heightEditText.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeStamp = simpleDateFormat.format(new Date());

                Credentials credentials = Credentials.anonymous();
                app.loginAsync(credentials, it -> {

                    if (it.isSuccess()) {
                        Log.w("MongoDB Auth", "Success");
                        user = RealmSingleton.getInstance().getRealm().currentUser();
                        SyncConfiguration config = new SyncConfiguration.Builder(user, _partiton)
                                .allowQueriesOnUiThread(true)
                                .allowWritesOnUiThread(true)
                                .build();

                        Realm.getInstanceAsync(config, new Realm.Callback() {
                            @Override
                            public void onSuccess(Realm realm) {
                                realm.executeTransaction(r -> {
                                    // Instantiate the class using the factory function.
                                    BodyMeasurement bm = r.createObject(BodyMeasurement.class, new ObjectId() );
                                    // Configure the instance.
                                    bm.setGewicht_Kg(weight);
                                    bm.setGröße_cm(height);
                                    bm.setTimestamp(timeStamp);
                                    bm.set_partitionKey(_partiton);
                                    Log.w("MongoDB", "Document inserted successfully");
                                });
                            }
                        });
                        weightEditText.setText("");
                        heightEditText.setText("");
                        Intent intent1 = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent1);
                    } else {
                        Log.e("MongoDB Auth", it.getError().toString());
                        Toast.makeText(getApplicationContext(), "Error while connecting with Realm", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}