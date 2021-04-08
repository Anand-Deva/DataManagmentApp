package com.example.datamanagmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class InsertIdentityActivity extends AppCompatActivity {
    App app;
    User user;
    EditText idEditText;
    Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_identity);
        idEditText = findViewById(R.id.id_editText);
        insertButton = findViewById(R.id.insert_button);

        app = RealmSingleton.getInstance().getRealm();
        String _partiton = "ids";

        /*Realm.init(this);
        String appID = "patientidcollapp-wapwe";
        app = new App(new AppConfiguration.Builder(appID).build());
         */

        insertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String insertedID = idEditText.getText().toString();

                Credentials credentials = Credentials.anonymous();
                app.loginAsync(credentials,it -> {

                    if (it.isSuccess()) {
                        Log.w("MongoDB Auth", "Success");
                        user =  RealmSingleton.getInstance().getRealm().currentUser();
                        SyncConfiguration config = new SyncConfiguration.Builder(user,_partiton)
                                .allowQueriesOnUiThread(true)
                                .allowWritesOnUiThread(true)
                                .build();

                        Realm.getInstanceAsync(config, new Realm.Callback() {
                            @Override
                            public void onSuccess(Realm realm) {
                                realm.executeTransaction(r -> {
                                    // Instantiate the class using the factory function.
                                    PatientIdCollection pId = r.createObject(PatientIdCollection.class, new ObjectId());
                                    // Configure the instance.
                                    pId.setuId(insertedID);
                                    pId.set_partitionKey(_partiton);

                                    Log.w("MongoDB", "Document inserted successfully");
                                });
                            }
                        });

                        idEditText.setText("");
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("MongoDB Auth", it.getError().toString());
                        Toast.makeText(getApplicationContext(),"Error while connecting with Realm",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        user.logOutAsync( result -> {
            if (result.isSuccess()) {
                Log.w("MongoDB Realm App Client", "Successfully logged out.");
            } else {
                Log.e("MongoDB Realm App Client", "Failed to log out. Error: " + result.getError().toString());
            }
        });
    }


    @Override
    public void onBackPressed() {}
}