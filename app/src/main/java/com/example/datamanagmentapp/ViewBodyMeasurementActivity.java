package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class ViewBodyMeasurementActivity extends AppCompatActivity {
    ListView listView;

    Realm realmObj;
    ConnectMongoRealm cmr;
    ArrayList<BodyMeasurement> queryResultsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_measurment);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        TextView selectedDateTextView= findViewById(R.id.textView_selected_date);
        DatePicker datePicker = findViewById(R.id.datePicker1);
        Button getDateButton = findViewById(R.id.get_date_Button);
        listView = findViewById(R.id.listView);


        //cmr = new ConnectMongoRealm();
        User user = RealmSingleton.getInstance().getRealm().currentUser();
        String _partition =  "Body Measurement";

/*
        app = RealmSingleton.getInstance().getRealm();
        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, it -> {

            if (it.isSuccess()) {
                Log.w("MongoDB Auth", "Success");
                user = RealmSingleton.getInstance().getRealm().currentUser();
                try {

                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            } else {
                Log.e("MongoDB Auth", it.getError().toString());
                Toast.makeText(getApplicationContext(), "Error while connecting with Realm", Toast.LENGTH_LONG).show();
            }
        });
 */
        if(user != null){

            /*
            realm = cmr.establishRealm(user, _partition);
            realm.executeTransaction(transactionRealm -> {
                Log.v("MongoDB Realm", "Successfully opened a realm");
                RealmQuery<BodyMeasurement> tasksQuery = transactionRealm.where(BodyMeasurement.class);
                queryResultsArrayList = new ArrayList<>(tasksQuery.findAll());
            });
             */

            SyncConfiguration config = new SyncConfiguration.Builder(user, _partition)
                    .allowQueriesOnUiThread(true)
                    .allowWritesOnUiThread(true)
                    .build();

            Realm.getInstanceAsync(config, new Realm.Callback() {
                @Override
                public void onSuccess(Realm realm) {
                    Log.w("MongoDB Realm", "Successfully opened a realm");
                    RealmQuery<BodyMeasurement> tasksQuery = realm.where(BodyMeasurement.class);
                    queryResultsArrayList = new ArrayList<>(tasksQuery.findAll());
                    realmObj = realm;
                }
            });
        } else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }


        getDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BodyMeasurement> resultArrayList = new ArrayList<>();
                listView.setAdapter(null);
                CustomAdapter customAdapter;

                String searchDate = datePicker.getYear() +"-"+ (datePicker.getMonth() + 1) +"-"+ datePicker.getDayOfMonth() + " 00:00:00" ;

                for (int i = 0; i < queryResultsArrayList.size(); i++) {
                    String queryDate = queryResultsArrayList.get(i).getTimestamp();
                    Log.d("MongoDB query result(i)", queryResultsArrayList.get(i).toString());

                    try {
                        if(sdf.parse(queryDate).after(sdf.parse(searchDate))){
                            resultArrayList.add(queryResultsArrayList.get(i));
                            }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("arrayList of values",resultArrayList.toString());
                customAdapter = new CustomAdapter(getApplicationContext(),resultArrayList);
                listView.setAdapter(customAdapter);
                realmObj.close();
            }
        });
    }
}