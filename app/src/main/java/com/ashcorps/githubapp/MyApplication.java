package com.ashcorps.githubapp;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("githubuser.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);

        AndroidNetworking.initialize(getApplicationContext());

    }
}
