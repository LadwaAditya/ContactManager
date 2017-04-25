package com.ladwa.aditya.contact.injection.component;

import com.ladwa.aditya.contact.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Aditya on 13-Feb-17.
 */
@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}
