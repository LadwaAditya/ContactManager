package com.ladwa.aditya.contact;

import com.ladwa.aditya.contact.injection.component.ApplicationComponent;
import com.ladwa.aditya.contact.injection.component.DaggerApplicationComponent;
import com.ladwa.aditya.contact.injection.module.ApplicationModule;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by Aditya on 13-Feb-17.
 */

public class TestApplication extends GoJekApp {
    public static final String PATH_ASSETS_CONTACT = "contact";

    @Override
    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this, RESTMockServer.getUrl()))
                    .build();
        }
        return mApplicationComponent;
    }
}
