package com.ladwa.aditya.contact;

import android.content.Context;

import com.ladwa.aditya.contact.data.DataManager;
import com.ladwa.aditya.contact.injection.component.DaggerTestComponent;
import com.ladwa.aditya.contact.injection.component.TestComponent;
import com.ladwa.aditya.contact.injection.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by Aditya on 13-Feb-17.
 */

public class TestComponentRule implements TestRule {
    private final TestComponent mTestComponent;
    private final Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
        ContactApp application = ContactApp.get(context);
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application, RESTMockServer.getUrl()))
                .build();
    }

    public TestComponent getTestComponent() {
        return mTestComponent;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    @Override public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                ContactApp application = ContactApp.get(mContext);
                application.setComponent(mTestComponent);


                base.evaluate();

                application.setComponent(null);
            }
        };
    }
}
