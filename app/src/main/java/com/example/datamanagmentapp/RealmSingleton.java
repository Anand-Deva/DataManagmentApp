package com.example.datamanagmentapp;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class RealmSingleton {
    private static RealmSingleton instance = null;
    private static String appID = "ad_testapp_01-uhjhn";
    private App realm;

    public static RealmSingleton getInstance() {
        if (instance == null) {
            instance = new RealmSingleton(new App(new AppConfiguration.Builder(appID).build()));
        }
        return instance;
    }

    private RealmSingleton(App realm) {
        this.realm = realm;
    }

    public App getRealm() {
        return realm;
    }
}
