package com.example.aditya.gojek.ui.main.util;

import com.example.aditya.gojek.data.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class TestDataFactory {
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }


    public static List<Contact> makeContactList(int number) {
        List<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            contactList.add(makeContact(String.valueOf(i)));
        }
        return contactList;
    }

    private static Contact makeContact(String s) {
        Contact contact = new Contact();
        contact.setId(0L);
        contact.setFirstName("Firstname " + s);
        contact.setLastName("Lastname " + s);
        contact.setProfilePic("Profile " + s);
        contact.setUrl("Url " + s);
        return contact;
    }
}
