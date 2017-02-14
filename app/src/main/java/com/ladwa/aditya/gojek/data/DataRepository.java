package com.ladwa.aditya.gojek.data;

import com.ladwa.aditya.gojek.data.model.Contact;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * An interface that encapsulates the Business logic
 * Created by Aditya on 09-Feb-17.
 */

public interface DataRepository {

    /**
     * Get a list of {@link Contact} from Network
     *
     * @return List of contacts
     */
    Single<List<Contact>> getContactFromRemote();

    /**
     * Get a List of {@link Contact} from database
     *
     * @return List of contacts
     */
    Single<List<Contact>> getContactFromDatabase();

    /**
     * Get a single {@link Contact}
     *
     * @return com.example.aditya.gojek.data.model.Contact
     */
    Single<Contact> getIndividualContact(int id);

    /**
     * @return {@link Contact}
     */
    Single<Contact> updateIndividualContact(int id, Contact contact);

    /**
     * @return com.example.aditya.gojek.data.model.Contact
     */
    Single<Contact> createNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image);


    /**
     * Insert or update list of contacts
     *
     * @param contacts
     * @return boolean
     */
    boolean putContactsInDatabase(List<Contact> contacts);

    /**
     * Insert or update a contact
     *
     * @param contact
     * @return
     */
    boolean putContact(Contact contact);
}
