package com.ladwa.aditya.gojek.data;

import com.ladwa.aditya.gojek.data.local.GoJekLocalRepository;
import com.ladwa.aditya.gojek.data.model.Contact;
import com.ladwa.aditya.gojek.data.remote.GoJekService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * A Data repository to manage the apps data
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
        return mGoJekService.getContacts().doAfterSuccess(this::putContactsInDatabase);
    }

    @Override public boolean putContactsInDatabase(List<Contact> contacts) {
        return mGoJekLocalRepository.saveContactList(contacts);
    }

    @Override public boolean putContact(Contact contact) {
        return mGoJekLocalRepository.saveContact(contact);
    }


    @Override public Single<List<Contact>> getContactFromDatabase() {
        return mGoJekLocalRepository.getContactList();
    }

    @Override public Single<Contact> getIndividualContact(int id) {
        return mGoJekService.getIndividualContact(id).doAfterSuccess(this::putContact);
    }

    @Override public Single<Contact> updateIndividualContact(int id, Contact contact) {
        return mGoJekService.updateIndividualContact(id, contact).doAfterSuccess(this::putContact);
    }

    @Override public Single<Contact> createNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image) {
        return mGoJekService.createContact(firstname, lastname, email,phone,image);
    }



    @Override public Observable<List<Contact>> getContact() {
        return Observable
                .concat(getContactFromDatabase().filter(contacts -> contacts != null && contacts.size() > 0).toObservable()
                        , getContactFromRemote().toObservable())
                .take(1);
    }


}
