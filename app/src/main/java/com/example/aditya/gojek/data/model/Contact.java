package com.example.aditya.gojek.data.model;

import android.support.annotation.NonNull;

import com.example.aditya.gojek.data.local.DatabaseContract;
import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverColumn;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverType;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by Aditya on 09-Feb-17.
 */
@StorIOSQLiteType(table = DatabaseContract.Contacts.TABLE_NAME)
@StorIOContentResolverType(uri = DatabaseContract.Contacts.CONTENT_URI_STRING)
public class Contact implements Comparable<Contact> {

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_CONTACT_ID, key = true)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_CONTACT_ID, key = true)
    @SerializedName("id") Long id;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_FIRST_NAME)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_FIRST_NAME)
    @SerializedName("first_name") String firstName;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_LAST_NAME)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_LAST_NAME)
    @SerializedName("last_name") String lastName;


    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_EMAIL)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_EMAIL)
    @SerializedName("email") String email;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_PHONE_NUMBER)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_PHONE_NUMBER)
    @SerializedName("phone_number") String phoneNumber;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_FAVORITE)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_FAVORITE)
    @SerializedName("favorite") boolean favorite;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_PROFILE_PIC)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_PROFILE_PIC)
    @SerializedName("profile_pic") String profilePic;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_URL)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_URL)
    @SerializedName("url") String url;


    boolean firstAlpha;

    public boolean isFirstAlpha() {
        return firstAlpha;
    }

    public void setFirstAlpha(boolean firstAlpha) {
        this.firstAlpha = firstAlpha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override public int compareTo(@NonNull Contact contact) {
        return this.firstName.toLowerCase().replace(" ", "").compareTo(contact.getFirstName().toLowerCase().replace(" ", ""));
    }
}
