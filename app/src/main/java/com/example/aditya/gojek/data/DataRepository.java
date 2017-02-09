package com.example.aditya.gojek.data;

import com.example.aditya.gojek.data.model.Contact;

import java.util.List;

import io.reactivex.Single;

/**
 * An interface that encapsulates the Business logic
 * Created by Aditya on 09-Feb-17.
 */

public interface DataRepository {

    Single<List<Contact>> getContactFromRemote();

    boolean putContactsInDatabase(List<Contact> contacts);
}
