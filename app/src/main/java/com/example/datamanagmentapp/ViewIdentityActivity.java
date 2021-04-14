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
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class ViewIdentityActivity extends AppCompatActivity {

    TextView displayView;
    Button backButton;

    ConnectMongoRealm cmr;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_identity);

        displayView = findViewById(R.id.display_textView);
        backButton = findViewById(R.id.back_button);

        cmr = new ConnectMongoRealm();
        String _partition = "ids";
        User user = RealmSingleton.getInstance().getRealm().currentUser();

        /*
        app = RealmSingleton.getInstance().getRealm();
        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials,it -> {

            if (it.isSuccess()) {
                Log.w("MongoDB Auth", "Success");
                user = RealmSingleton.getInstance().getRealm().currentUser();
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
        */

        if(user != null){
            realm = cmr.establishRealm(user, _partition);
            realm.executeTransaction(transactionRealm -> {
                Log.v("MongoDB Realm", "Successfully opened a realm");
                RealmQuery<PatientIdCollection> tasksQuery = transactionRealm.where(PatientIdCollection.class);
                ArrayList<PatientIdCollection> queryResultsArrayList = new ArrayList<>(tasksQuery.findAll());
                ArrayList<String> idArrayList = new ArrayList<String>();
                for (int i = 0; i < queryResultsArrayList.size(); i++) {
                    idArrayList.add(queryResultsArrayList.get(i).getuId());
                }
                String listString = "";
                for (String s : idArrayList) {
                    listString += s + " ";
                }
                displayView.setText(listString);
            });
        } else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        cmr.closeRealm();

    }


    @Override
    public void onBackPressed() {}
}