package com.example.aditya.gojek;

import com.example.aditya.gojek.injection.component.ApplicationComponent;
import com.example.aditya.gojek.injection.component.DaggerApplicationComponent;
import com.example.aditya.gojek.injection.module.ApplicationModule;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by Aditya on 13-Feb-17.
 */

public class TestApplication extends GoJekApp {
    @Override public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this, RESTMockServer.getUrl()))
                    .build();
        }
        return mApplicationComponent;
    }
}
