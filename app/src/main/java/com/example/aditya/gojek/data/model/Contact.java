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
    @SerializedName("first_name") String first_name;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_LAST_NAME)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_LAST_NAME)
    @SerializedName("last_name") String last_name;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_PROFILE_PIC)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_PROFILE_PIC)
    @SerializedName("profile_pic") String profile_pic;

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_URL)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_URL)
    @SerializedName("url") String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
