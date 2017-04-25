package com.ladwa.aditya.contact.injection.component;

import com.ladwa.aditya.contact.injection.module.ActivityModule;
import com.ladwa.aditya.contact.injection.scope.PerActivity;
import com.ladwa.aditya.contact.ui.base.BaseActivity;
import com.ladwa.aditya.contact.ui.detail.ContactDetailActivity;
import com.ladwa.aditya.contact.ui.main.MainActivity;
import com.ladwa.aditya.contact.ui.newcontact.NewContactActivity;

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
