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
    App app;
    User user;
    RealmResults<BioInfo> queryResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bio);
        app = RealmSingleton.getInstance().getRealm();
        String _partiton = "Bio";

        EditText searchEditText = findViewById(R.id.editText_searchName);
        Button submit_button = findViewById(R.id.search_button);
        Button backButton = findViewById(R.id.back_button);

        nameTextView = findViewById(R.id.setName_TextView);
        ageTextView =  findViewById(R.id.setAge_textView);
        genderTextView= findViewById(R.id.setGender_textView);

        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, it -> {

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
                        /*
                        RealmQuery<BioInfo> queryResults = realm.where(BioInfo.class);
                        Log.w("MongoDB Realm", String.valueOf(queryResults.contains("name",searchName)));
                        */
                            queryResults = realm.where(BioInfo.class).findAll();
                        }
                    });
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            } else {
                Log.e("MongoDB Auth", it.getError().toString());
                Toast.makeText(getApplicationContext(), "Error while connecting with Realm", Toast.LENGTH_LONG).show();
            }
        });


        submit_button.setOnClickListener(new View.OnClickListener() {
            String name, age, gender;
            public void onClick(View v) {
                String searchName = searchEditText.getText().toString().toLowerCase();
                Log.w("MongoDB Query------->",searchName);
                for (int i = 0; i < queryResults.size(); i++) {
                    String queryName = queryResults.get(i).getName().toLowerCase();
                    if( queryName.equals(searchName)){
                        name = queryResults.get(i).getName();
                        age = queryResults.get(i).getAge();
                        gender = queryResults.get(i).getGender();
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

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}