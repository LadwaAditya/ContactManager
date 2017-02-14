package com.ladwa.aditya.gojek.runner;

import android.app.Application;
import android.content.Context;

import com.ladwa.aditya.gojek.TestApplication;

import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.android.AndroidAssetsFileParser;
import io.appflate.restmock.android.AndroidLogger;
import io.appflate.restmock.android.RESTMockTestRunner;

/**
 * Created by Aditya on 13-Feb-17.
 */

public class CustomTestRunner extends RESTMockTestRunner {
    @Override public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        RESTMockServerStarter.startSync(new AndroidAssetsFileParser(getContext()), new AndroidLogger());
        return super.newApplication(cl, TestApplication.class.getCanonicalName(), context);
    }
}
