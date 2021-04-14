package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewBioActivity extends AppCompatActivity {

    TextView nameTextView, ageTextView, genderTextView;

    Realm realm;
    ConnectMongoRealm cmr;
    ArrayList<BioInfo> queryResultsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bio);


        EditText searchEditText = findViewById(R.id.editText_searchName);
        Button submit_button = findViewById(R.id.search_button);
        Button backButton = findViewById(R.id.back_button);

        nameTextView = findViewById(R.id.setName_TextView);
        ageTextView =  findViewById(R.id.setAge_textView);
        genderTextView= findViewById(R.id.setGender_textView);

        cmr = new ConnectMongoRealm();
        String _partition = "Bio";
        User user = RealmSingleton.getInstance().getRealm().currentUser();

        /*
             SyncConfiguration config = new SyncConfiguration.Builder(user, partitionKey)
                    .allowQueriesOnUiThread(true)
                    .allowWritesOnUiThread(true)
                    .build();
            Realm.getInstanceAsync(config, new Realm.Callback() {
                @Override
                public void onSuccess(Realm realm) {
                    Log.w("MongoDB Realm", "Successfully opened a realm");
                    RealmQuery<BioInfo> tasksQuery = realm.where(BioInfo.class);
                    queryResultsArrayList = new ArrayList<>(tasksQuery.findAll());
                    for(int i = 0; i< queryResultsArrayList.size();i++) {
                        Log.w("MongoDB Query "+ i , queryResultsArrayList.get(i).getName());
                    }
                }
            });
             */

        if(user != null){
            realm = cmr.establishRealm(user, _partition);
            realm.executeTransaction(transactionRealm -> {
                Log.v("MongoDB Realm", "Successfully opened a realm");
                //BioInfo info =  realm.where(BioInfo.class).equalTo("name","Anand").findFirst();
                RealmQuery<BioInfo> tasksQuery = transactionRealm.where(BioInfo.class);
                queryResultsArrayList = new ArrayList<>(tasksQuery.findAll());
                for(int i = 0; i< queryResultsArrayList.size();i++) {
                    Log.w("MongoDB Query "+ i , queryResultsArrayList.get(i).getName());
                }
            });
        } else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        submit_button.setOnClickListener(new View.OnClickListener() {
            String name, age, gender;
            public void onClick(View v) {
                String searchName = searchEditText.getText().toString().toLowerCase();

                for (int i = 0; i < queryResultsArrayList.size(); i++) {
                    String queryName = queryResultsArrayList.get(i).getName().toLowerCase();
                    if( queryName.equals(searchName)){
                        name = queryResultsArrayList.get(i).getName();
                        age = queryResultsArrayList.get(i).getAge();
                        gender = queryResultsArrayList.get(i).getGender();
                    }else{
                        //Toast.makeText(getApplicationContext(),"Name does not exist",Toast.LENGTH_SHORT).show();
                    }
                }
                Log.w("MongoDB Realm", name + " : "+ age + " : "+ gender);
                searchEditText.setText("");
                nameTextView.setText(name);
                ageTextView.setText(age);
                genderTextView.setText(gender);
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cmr.closeRealm();
    }
}