package com.example.aditya.gojek.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.aditya.gojek.data.local.GoJekLocalRepository;
import com.example.aditya.gojek.data.remote.GoJekService;
import com.example.aditya.gojek.data.remote.GoJekServiceFactory;
import com.example.aditya.gojek.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aditya on 09-Feb-17.
 */

@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    GoJekService providesGoJekService() {
        return GoJekServiceFactory.makeGoJekService(mApplication.getApplicationContext());
    }

    @Provides
    @Singleton
    GoJekLocalRepository providesGoJekLocalRepository() {
        return GoJekLocalRepository.makeGoJekLocalData(mApplication.getApplicationContext());
    }

}
