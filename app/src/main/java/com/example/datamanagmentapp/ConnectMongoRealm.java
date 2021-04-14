package com.example.datamanagmentapp;

import android.util.Log;

import io.realm.Realm;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class ConnectMongoRealm {

    private  Realm  realm;

    public ConnectMongoRealm(){}

    public Realm establishRealm(User user, String partitionKey){
        try {
            SyncConfiguration config = new SyncConfiguration.Builder(user, partitionKey)
                    .allowQueriesOnUiThread(true)
                    .allowWritesOnUiThread(true)
                    .build();

            //Realm.setDefaultConfiguration(config);
            //realm = Realm.getDefaultInstance();

            realm = Realm.getInstance(config);

        }catch (Exception e){
            Log.e("Exception", e.toString());
        }
        return realm;
    }

    public void closeRealm(){
        if(realm != null) {
            realm.close();
        }
    }
}
