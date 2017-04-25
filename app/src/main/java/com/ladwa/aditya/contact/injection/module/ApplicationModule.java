package com.ladwa.aditya.contact.injection.module;

import android.app.Application;
import android.content.Context;

import com.ladwa.aditya.contact.data.local.GoJekLocalRepository;
import com.ladwa.aditya.contact.data.remote.ContactService;
import com.ladwa.aditya.contact.data.remote.ContactServiceFactory;
import com.ladwa.aditya.contact.injection.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aditya on 09-Feb-17.
 */

@Module
public class ApplicationModule {

    protected final Application mApplication;
    private final String mBaseUrl;

    public ApplicationModule(Application mApplication, String mBaseUrl) {
        this.mApplication = mApplication;
        this.mBaseUrl = mBaseUrl;
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
    ContactService providesGoJekService() {
        return ContactServiceFactory.makeGoJekService(mBaseUrl);
    }

    @Provides
    @Singleton
    GoJekLocalRepository providesGoJekLocalRepository() {
        return GoJekLocalRepository.makeGoJekLocalData(mApplication.getApplicationContext());
    }

}
