package com.example.aditya.gojek.data;

import com.example.aditya.gojek.data.model.Contact;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * An interface that encapsulates the Business logic
 * Created by Aditya on 09-Feb-17.
 */

public interface DataRepository {

    Single<List<Contact>> getContactFromRemote();

    Single<List<Contact>> getContactFromDatabase();

    Single<Contact> getIndividualContact(int id);

    Single<Contact> updateIndividualContact(int id, Contact contact);

    Single<ResponseBody> createNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image);

    Observable<List<Contact>> getContact();

    boolean putContactsInDatabase(List<Contact> contacts);

    boolean putContact(Contact contact);
}
