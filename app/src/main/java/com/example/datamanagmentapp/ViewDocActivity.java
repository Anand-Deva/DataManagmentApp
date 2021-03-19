package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class ViewDocActivity extends AppCompatActivity {
    App app;
    User user;
    TextView displayView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doc);

        displayView = findViewById(R.id.display_textView);
        backButton = findViewById(R.id.back_button);

        Realm.init(this);

        String appID = "patientidcollapp-wapwe";
        String _partiton = "ids";
        app = new App(new AppConfiguration.Builder(appID).build());

        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials,it -> {

            if (it.isSuccess()) {
                Log.w("MongoDB Auth", "Success");
                user = app.currentUser();
                try {
                    SyncConfiguration config = new SyncConfiguration.Builder(user, _partiton)
                            .allowQueriesOnUiThread(true)
                            .allowWritesOnUiThread(true)
                            .build();
                    Realm.getInstanceAsync(config, new Realm.Callback() {
                        @Override
                        public void onSuccess(Realm realm) {

                            Log.w("MongoDB Realm", "Successfully opened a realm");
                            // RealmQuery<PatientIdCollection> queryResults =  realm.where(PatientIdCollection.class);
                            // Log.i("MongoDB Query", queryResults.findAll().asJSON());

                            RealmResults<PatientIdCollection> results = realm.where(PatientIdCollection.class).findAll();
                            ArrayList<String> idsArrayList = new ArrayList<String>();

                            Log.w("MongoDB Query------->", String.valueOf(results.size()));

                            for (int i = 0; i < results.size(); i++) {
                                Log.w("MongoDB Query------->", results.get(i).getuId());
                                idsArrayList.add(results.get(i).getuId());
                            }

                            String listString = "";
                            for (String s : idsArrayList) {
                                listString += s + " ";
                            }
                            displayView.setText(listString);
                        }
                    });
                }catch (Exception e){
                    Log.e("Exception", e.toString());
                }


            } else {
                Log.e("MongoDB Auth", it.getError().toString());
                Toast.makeText(getApplicationContext(),"Error while connecting with Realm",Toast.LENGTH_LONG).show();
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        user.logOutAsync( result -> {
            if (result.isSuccess()) {
                Log.w("MongoDB Realm App Client", "Successfully logged out.");
            } else {
                Log.e("MongoDB Realm App Client", "Failed to log out. Error: " + result.getError().toString());
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