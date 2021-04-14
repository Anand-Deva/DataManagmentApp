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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.log.RealmLog;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class InsertBioActivity extends AppCompatActivity {

    Realm realm;
    ConnectMongoRealm cmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bio);

        EditText nameEditTest = findViewById(R.id.editText_Name);
        EditText ageEditText = findViewById(R.id.editText_age);
        Button submitBioButton = findViewById(R.id.Submit_bio_button);
        Spinner selectGender = findViewById(R.id.Select_Gender);
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(InsertBioActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.itemselect));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGender.setAdapter(myadapter);



        cmr = new ConnectMongoRealm();
        String _partition = "Bio";
        User user = RealmSingleton.getInstance().getRealm().currentUser();


        submitBioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = nameEditTest.getText().toString();
                String age = ageEditText.getText().toString();
                String gender = selectGender.getSelectedItem().toString();

                /*
                app = RealmSingleton.getInstance().getRealm();
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
                */

                if(user != null){
                    realm = cmr.establishRealm(user, _partition);
                    realm.executeTransaction(transactionRealm -> {
                        Log.v("MongoDB Realm", "Successfully opened a realm");
                        // Instantiate the class using the factory function.
                        BioInfo bi = transactionRealm.createObject(BioInfo.class, new ObjectId());
                        // Configure the instance.
                        bi.setName(name);
                        bi.setAge(age);
                        bi.setGender(gender);
                        bi.set_partitionKey(_partition);
                        Log.w("MongoDB", "Document inserted successfully");
                    });
                    nameEditTest.setText("");
                    ageEditText.setText("");
                    Intent intent1 = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cmr.closeRealm();
    }
}