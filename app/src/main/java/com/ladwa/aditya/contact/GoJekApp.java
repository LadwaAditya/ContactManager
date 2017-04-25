package com.ladwa.aditya.contact;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.ladwa.aditya.contact.injection.component.ApplicationComponent;
import com.ladwa.aditya.contact.injection.component.DaggerApplicationComponent;
import com.ladwa.aditya.contact.injection.module.ApplicationModule;
import com.facebook.stetho.Stetho;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class GoJekApp extends Application {

    ApplicationComponent mApplicationComponent;
    private static GoJekApp sGoJekApp;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sGoJekApp = this;
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
                    .applicationModule(new ApplicationModule(this,BuildConfig.GOJEK_API_URL))
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
