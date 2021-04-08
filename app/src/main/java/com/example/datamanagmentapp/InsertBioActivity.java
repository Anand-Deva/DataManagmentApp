package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class InsertBioActivity extends AppCompatActivity {
    App app;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bio);

        app = RealmSingleton.getInstance().getRealm();
        String _partiton = "Bio";

        Spinner selectGender = findViewById(R.id.Select_Gender);
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(InsertBioActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.itemselect));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGender.setAdapter(myadapter);

        EditText nameEditTest = findViewById(R.id.editText_Name);
        EditText ageEditText = findViewById(R.id.editText_age);

        Button submitBioButton = findViewById(R.id.Submit_bio_button);


        submitBioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = nameEditTest.getText().toString();
                String age = ageEditText.getText().toString();
                String gender = selectGender.getSelectedItem().toString();

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
                                    BioInfo bi = r.createObject(BioInfo.class, new ObjectId() );
                                    // Configure the instance.
                                    bi.setName(name);
                                    bi.setAge(age);
                                    bi.setGender(gender);
                                    bi.set_partitionKey(_partiton);
                                    Log.w("MongoDB", "Document inserted successfully");
                                });
                            }
                        });
                        nameEditTest.setText("");
                        ageEditText.setText("");
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