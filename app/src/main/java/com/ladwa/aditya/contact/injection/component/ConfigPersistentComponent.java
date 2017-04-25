package com.ladwa.aditya.contact.injection.component;

import com.ladwa.aditya.contact.injection.scope.ConfigPersistent;
import com.ladwa.aditya.contact.injection.module.ActivityModule;
import com.ladwa.aditya.contact.injection.module.FragmentModule;

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
