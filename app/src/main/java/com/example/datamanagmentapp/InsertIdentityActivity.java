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
    Realm realm;
    ConnectMongoRealm cmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_identity);
        EditText idEditText = findViewById(R.id.id_editText);
        Button insertButton = findViewById(R.id.insert_button);

        //cmr = new ConnectMongoRealm();
        User user = RealmSingleton.getInstance().getRealm().currentUser();
        String _partition = "ids";


        insertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String insertedID = idEditText.getText().toString();

                /*
                app = RealmSingleton.getInstance().getRealm();
                Credentials credentials = Credentials.anonymous();
                app.loginAsync(credentials,it -> {

                    if (it.isSuccess()) {
                        Log.w("MongoDB Auth", "Success");
                        user =  RealmSingleton.getInstance().getRealm().currentUser();


                        idEditText.setText("");
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("MongoDB Auth", it.getError().toString());
                        Toast.makeText(getApplicationContext(),"Error while connecting with Realm",Toast.LENGTH_LONG).show();
                    }
                });

 */

                if(user != null){
                    /*
                    realm = cmr.establishRealm(user, _partition);
                    realm.executeTransaction(transactionRealm -> {
                        Log.v("MongoDB Realm", "Successfully opened a realm");
                        // Instantiate the class using the factory function.
                        PatientIdCollection pId = transactionRealm.createObject(PatientIdCollection.class, new ObjectId());
                        // Configure the instance.
                        pId.setuId(insertedID);
                        pId.set_partitionKey(_partition);
                        Log.w("MongoDB", "Document inserted successfully");
                    });
                     */

                    SyncConfiguration config = new SyncConfiguration.Builder(user,_partition)
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
                                pId.set_partitionKey(_partition);

                                Log.w("MongoDB", "Document inserted successfully");
                            });
                            realm.close();
                        }
                    });
                    idEditText.setText("");
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
    public void onBackPressed() {}
}