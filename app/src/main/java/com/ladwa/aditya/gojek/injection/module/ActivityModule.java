package com.ladwa.aditya.gojek.injection.module;

import android.app.Activity;
import android.content.Context;

import com.ladwa.aditya.gojek.injection.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aditya on 09-Feb-17.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}