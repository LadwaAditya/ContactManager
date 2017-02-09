package com.example.aditya.gojek.injection.component;

import android.app.Application;
import android.content.Context;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.injection.scope.ApplicationContext;
import com.example.aditya.gojek.injection.module.ApplicationModule;

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