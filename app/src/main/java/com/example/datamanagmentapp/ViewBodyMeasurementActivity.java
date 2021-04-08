package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

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
    App app;
    User user;
    RealmResults<BodyMeasurement> queryResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_measurment);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        TextView selectedDateTextView= findViewById(R.id.textView_selected_date);
        DatePicker datePicker = findViewById(R.id.datePicker1);
        Button getDateButton = findViewById(R.id.get_date_Button);
        listView = findViewById(R.id.listView);

        app = RealmSingleton.getInstance().getRealm();
        String _partiton =  "Body Measurement";

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

                            queryResults = realm.where(BodyMeasurement.class).findAll();

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


        getDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CustomBodyMeasurementModel> arrayList = new ArrayList<>();
                listView.setAdapter(null);
                CustomAdapter customAdapter;
                //Date searchDatePattern = null, queryDatePattern = null;
                String searchDate = datePicker.getYear() +"-"+ (datePicker.getMonth() + 1) +"-"+ datePicker.getDayOfMonth() + " 00:00:00" ;

                /*
                try {
                    searchDatePattern =  sdf.parse(searchDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                 */

/*
                JSONArray arrayJson = null;
                try {
                    arrayJson = new JSONArray(queryResults.asJSON());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i=0; i < arrayJson.length();i++){
                }
*/

                for (int i = 0; i < queryResults.size(); i++) {
                    String queryDate = queryResults.get(i).getTimestamp();
                    Log.d("MongoDB query result(i)", queryResults.get(i).toString());

                    /*
                    try {
                        queryDatePattern = sdf.parse(queryDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                     */

                    try {
                        if(sdf.parse(queryDate).after(sdf.parse(searchDate))){
                            //Log.d("after if condition",queryDatePattern.toString());
                            arrayList.add(new CustomBodyMeasurementModel(queryResults.get(i).getGewicht_Kg(), queryResults.get(i).getGröße_cm(),queryResults.get(i).getTimestamp()));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("arrayList of values",arrayList.toString());
                customAdapter = new CustomAdapter(getApplicationContext(),arrayList);
                listView.setAdapter(customAdapter);
            }
        });
    }
}