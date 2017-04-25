package com.ladwa.aditya.contact.data.remote;

import com.ladwa.aditya.contact.data.model.Contact;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Single<Contact> updateIndividualContact(@Path("id") int id,
                                            @Body Contact contact);

    @Multipart
    @POST("contacts.json")
    Single<Contact> createContact(@Part("contact[first_name]") RequestBody firstName,
                                  @Part("contact[last_name]") RequestBody lastName,
                                  @Part("contact[email]") RequestBody email,
                                  @Part("contact[phone_number]") RequestBody phoneNumber,
                                  @Part MultipartBody.Part file);


}
