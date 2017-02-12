package com.example.aditya.gojek.injection.component;

import com.example.aditya.gojek.injection.module.ActivityModule;
import com.example.aditya.gojek.injection.scope.PerActivity;
import com.example.aditya.gojek.ui.base.BaseActivity;
import com.example.aditya.gojek.ui.detail.ContactDetailActivity;
import com.example.aditya.gojek.ui.main.MainActivity;
import com.example.aditya.gojek.ui.newcontact.NewContactActivity;

import dagger.Subcomponent;

/**
 * Created by Aditya on 09-Feb-17.
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    void inject(ContactDetailActivity detailActivity);

    void inject(NewContactActivity newContactActivity);
}
