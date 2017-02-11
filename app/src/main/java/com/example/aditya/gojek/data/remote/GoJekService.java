package com.example.aditya.gojek.data.remote;

import com.example.aditya.gojek.data.model.Contact;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Aditya on 09-Feb-17.
 */

public interface GoJekService {

    @GET("contacts.json")
    Single<List<Contact>> getContacts();

    @GET("contacts/{id}.json")
    Single<Contact> getIndividualContact(@Path("id") int id);

    @PUT("contacts/{id}.json")
    Single<Contact> updateIndividualContact(@Path("id") int id, @Body Contact contact);
}
