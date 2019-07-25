package com.mardesago.umatbertanya;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dwi Randy Herdinanto on 11/17/2017.
 */

public class UmatBertanyaApp extends Application {


    private static UmatBertanyaApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("db.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static UmatBertanyaApp getInstance ()
    {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
