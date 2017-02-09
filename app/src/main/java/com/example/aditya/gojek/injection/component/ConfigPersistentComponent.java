package com.example.aditya.gojek.injection.component;

import com.example.aditya.gojek.injection.scope.ConfigPersistent;
import com.example.aditya.gojek.injection.module.ActivityModule;
import com.example.aditya.gojek.injection.module.FragmentModule;

import dagger.Component;

/**
 * Created by Aditya on 09-Feb-17.
 */

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

}
