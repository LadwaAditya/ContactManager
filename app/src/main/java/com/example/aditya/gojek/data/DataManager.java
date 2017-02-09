package com.example.aditya.gojek.data;

import com.example.aditya.gojek.data.local.GoJekLocalRepository;
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
public class DataManager implements DataRepository {

    private final GoJekService mGoJekService;
    private final GoJekLocalRepository mGoJekLocalRepository;

    @Inject public DataManager(GoJekService mGoJekService, GoJekLocalRepository goJekLocalRepository) {
        this.mGoJekService = mGoJekService;
        this.mGoJekLocalRepository = goJekLocalRepository;
    }


    @Override public Single<List<Contact>> getContactFromRemote() {
        return mGoJekService.getContacts();

    }

    @Override public boolean putContactsInDatabase(List<Contact> contacts) {
        return mGoJekLocalRepository.saveContactList(contacts);

    }

}
