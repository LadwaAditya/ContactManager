package com.example.aditya.gojek;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class GoJekApp extends Application {

    public static final String TAG = GoJekApp.class.getCanonicalName();
    private static GoJekApp sGoJekApp;


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
    }

    public static GoJekApp get(Context context) {
        return (GoJekApp) context.getApplicationContext();
    }


    public static synchronized GoJekApp getInstance() {
        return sGoJekApp;
    }
}
