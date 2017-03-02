package com.ladwa.aditya.gojek;

import com.ladwa.aditya.gojek.injection.component.ApplicationComponent;
import com.ladwa.aditya.gojek.injection.component.DaggerApplicationComponent;
import com.ladwa.aditya.gojek.injection.module.ApplicationModule;
import com.ladwa.aditya.gojek.injection.module.ApplicationTestModule;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by Aditya on 13-Feb-17.
 */

public class TestApplication extends GoJekApp {
    public static final String PATH_ASSETS_CONTACT = "contact";

    @Override public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationTestModule(this, RESTMockServer.getUrl()))
                    .build();
        }
        return mApplicationComponent;
    }
}
