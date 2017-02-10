package com.example.aditya.gojek.data.model;

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
public class Contact {

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_CONTACT_ID, key = true)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_CONTACT_ID, key = true)
    @SerializedName("id") Long id;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_FIRST_NAME)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_FIRST_NAME)
    @SerializedName("first_name") String firstName;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_LAST_NAME)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_LAST_NAME)
    @SerializedName("last_name") String lastName;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_PROFILE_PIC)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_PROFILE_PIC)
    @SerializedName("profile_pic") String profilePic;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_URL)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_URL)
    @SerializedName("url") String url;

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
}
