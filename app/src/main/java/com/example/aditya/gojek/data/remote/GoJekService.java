package com.example.aditya.gojek.data.remote;

import com.example.aditya.gojek.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Aditya on 09-Feb-17.
 */

public interface GoJekService {

    @GET("contacts.json")
    Single<ArrayList<Contact>> getContacts();
}
