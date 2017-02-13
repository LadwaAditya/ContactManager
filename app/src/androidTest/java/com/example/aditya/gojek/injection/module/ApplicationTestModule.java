package com.example.aditya.gojek.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.aditya.gojek.data.local.GoJekLocalRepository;
import com.example.aditya.gojek.data.remote.GoJekService;
import com.example.aditya.gojek.data.remote.GoJekServiceFactory;
import com.example.aditya.gojek.injection.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Aditya on 13-Feb-17.
 */
@Module
public class ApplicationTestModule {
    private final Application mApplication;
    private final String mBaseUrl;

    public ApplicationTestModule(Application application, String baseUrl) {
        mApplication = application;
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
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
        return GoJekServiceFactory.makeGoJekService(mBaseUrl);
    }

    @Provides
    @Singleton
    GoJekLocalRepository providesGoJekLocalRepository() {
        return GoJekLocalRepository.makeGoJekLocalData(mApplication.getApplicationContext());
    }
}
