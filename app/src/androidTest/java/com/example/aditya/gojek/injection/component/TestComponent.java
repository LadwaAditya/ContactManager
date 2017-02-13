package com.example.aditya.gojek.injection.component;

import com.example.aditya.gojek.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Aditya on 13-Feb-17.
 */
@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}
