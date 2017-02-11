package com.example.aditya.gojek.data.model;

import android.os.Parcel;
import android.os.Parcelable;
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
public class Contact implements Comparable<Contact>, Parcelable {

    @StorIOSQLiteColumn(name = DatabaseContract.Contacts.COLUMN_CONTACT_ID, key = true)
    @StorIOContentResolverColumn(name = DatabaseContract.Contacts.COLUMN_CONTACT_ID, key = true)
    @SerializedName("id") int id;

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

    protected Contact(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        favorite = in.readByte() != 0;
        profilePic = in.readString();
        url = in.readString();
        firstAlpha = in.readByte() != 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public Contact() {
    }

    public boolean isFirstAlpha() {
        return firstAlpha;
    }

    public void setFirstAlpha(boolean firstAlpha) {
        this.firstAlpha = firstAlpha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
        parcel.writeByte((byte) (favorite ? 1 : 0));
        parcel.writeString(profilePic);
        parcel.writeString(url);
        parcel.writeByte((byte) (firstAlpha ? 1 : 0));
    }
}
