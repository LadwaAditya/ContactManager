package com.example.aditya.gojek;

import android.app.Application;
import android.content.Context;

import com.example.aditya.gojek.injection.component.ApplicationComponent;
import com.example.aditya.gojek.injection.component.DaggerApplicationComponent;
import com.example.aditya.gojek.injection.module.ApplicationModule;
import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class GoJekApp extends Application {

    public static final String TAG = GoJekApp.class.getCanonicalName();
    ApplicationComponent mApplicationComponent;
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

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static synchronized GoJekApp getInstance() {
        return sGoJekApp;
    }
}
