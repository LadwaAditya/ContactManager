package com.example.aditya.gojek.injection.component;

import com.example.aditya.gojek.injection.PerActivity;
import com.example.aditya.gojek.injection.module.ActivityModule;
import com.example.aditya.gojek.ui.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Aditya on 09-Feb-17.
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
