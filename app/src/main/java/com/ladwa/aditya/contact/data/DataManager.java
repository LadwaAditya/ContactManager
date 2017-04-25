package com.ladwa.aditya.contact.data;

import com.ladwa.aditya.contact.data.local.GoJekLocalRepository;
import com.ladwa.aditya.contact.data.model.Contact;
import com.ladwa.aditya.contact.data.remote.ContactService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * A Data repository to manage the apps data
 * Created by Aditya on 09-Feb-17.
 */
@Singleton
public class DataManager implements DataRepository {

    private final ContactService mContactService;
    private final GoJekLocalRepository mGoJekLocalRepository;

    @Inject public DataManager(ContactService mContactService, GoJekLocalRepository goJekLocalRepository) {
        this.mContactService = mContactService;
        this.mGoJekLocalRepository = goJekLocalRepository;
    }


    @Override public Single<List<Contact>> getContactFromRemote() {
        return mContactService.getContacts().doAfterSuccess(this::putContactsInDatabase);
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
        return mContactService.getIndividualContact(id).doAfterSuccess(this::putContact);
    }

    @Override public Single<Contact> updateIndividualContact(int id, Contact contact) {
        return mContactService.updateIndividualContact(id, contact).doAfterSuccess(this::putContact);
    }

    @Override public Single<Contact> createNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image) {
        return mContactService.createContact(firstname, lastname, email,phone,image);
    }


}
