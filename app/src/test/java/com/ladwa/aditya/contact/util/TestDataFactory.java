package com.ladwa.aditya.contact.util;

import com.ladwa.aditya.contact.data.model.Contact;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class TestDataFactory {
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }


    public static ArrayList<Contact> makeContactList(int number) {
        ArrayList<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            contactList.add(makeContact(String.valueOf(i)));
        }
        return contactList;
    }

    public static Contact makeContact(String s) {
        Contact contact = new Contact();
        contact.setId(0);
        contact.setFirstName("Firstname " + s);
        contact.setLastName("Lastname " + s);
        contact.setProfilePic("Profile " + s);
        contact.setUrl("Url " + s);
        return contact;
    }
}
