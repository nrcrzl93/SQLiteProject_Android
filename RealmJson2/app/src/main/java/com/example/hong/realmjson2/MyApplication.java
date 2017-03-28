package com.example.hong.realmjson2;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by hong on 2017-02-13.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }



}
