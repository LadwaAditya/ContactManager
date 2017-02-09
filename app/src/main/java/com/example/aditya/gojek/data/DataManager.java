package com.example.aditya.gojek.data;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.data.remote.GoJekService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


/**
 * Created by Aditya on 09-Feb-17.
 */

@Singleton
public class DataManager {

    private final GoJekService mGoJekService;

    @Inject
    public DataManager(GoJekService mGoJekService) {
        this.mGoJekService = mGoJekService;
    }


    public Single<List<Contact>> getContact() {
        return mGoJekService.getContacts();
    }
}
