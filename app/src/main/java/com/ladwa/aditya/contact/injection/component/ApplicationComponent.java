package com.ladwa.aditya.contact.injection.component;

import android.app.Application;
import android.content.Context;

import com.ladwa.aditya.contact.data.DataManager;
import com.ladwa.aditya.contact.injection.scope.ApplicationContext;
import com.ladwa.aditya.contact.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Aditya on 09-Feb-17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();
}