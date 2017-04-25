package com.ladwa.aditya.contact.data;

import com.ladwa.aditya.contact.data.local.GoJekLocalRepository;
import com.ladwa.aditya.contact.data.model.Contact;
import com.ladwa.aditya.contact.data.remote.GoJekService;
import com.ladwa.aditya.contact.util.RxSchedulersOverrideRule;
import com.ladwa.aditya.contact.util.TestDataFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Aditya on 14-Feb-17.
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DataManagerTest {
    @Rule public final RxSchedulersOverrideRule mOverrideRule = new RxSchedulersOverrideRule();

    @Mock GoJekService mockGoJekService;
    @Mock GoJekLocalRepository mockGoJekLocalRepository;

    private DataManager dataManager;
    private int id = 10;

    @Before
    public void setUp() throws Exception {
        dataManager = new DataManager(mockGoJekService, mockGoJekLocalRepository);
    }

    @Test
    public void getContactFromRemote_shouldReturnResult() throws Exception {
        ArrayList<Contact> contactList = TestDataFactory.makeContactList(5);

        when(mockGoJekService.getContacts()).thenReturn(Single.just(contactList));

        dataManager.getContactFromRemote().test()
                .assertComplete()
                .assertValue(contactList);
    }

    @Test
    public void putContactsInDatabase_shouldReturnResult() throws Exception {
        ArrayList<Contact> contactList = TestDataFactory.makeContactList(5);

        when(mockGoJekLocalRepository.saveContactList(contactList)).thenReturn(true);
        assertTrue(dataManager.putContactsInDatabase(contactList));

        when(mockGoJekLocalRepository.saveContactList(contactList)).thenReturn(false);
        assertFalse(dataManager.putContactsInDatabase(contactList));

    }

    @Test
    public void putContact_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Contact");

        when(mockGoJekLocalRepository.saveContact(contact)).thenReturn(true);
        assertTrue(dataManager.putContact(contact));

        when(mockGoJekLocalRepository.saveContact(contact)).thenReturn(false);
        assertFalse(dataManager.putContact(contact));

    }

    @Test
    public void getContactFromDatabase_shouldReturnResult() throws Exception {
        ArrayList<Contact> contactList = TestDataFactory.makeContactList(5);

        when(mockGoJekLocalRepository.getContactList()).thenReturn(Single.just(contactList));

        dataManager.getContactFromDatabase().test().assertComplete().assertValue(contactList);
    }


    @Test
    public void getIndividualContact_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Contact");

        when(mockGoJekService.getIndividualContact(id)).thenReturn(Single.just(contact));

        dataManager.getIndividualContact(id).test().assertComplete().assertValue(contact);
    }

    @Test
    public void updateIndividualContact_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Contact");

        when(mockGoJekService.updateIndividualContact(id, contact)).thenReturn(Single.just(contact));

        dataManager.updateIndividualContact(id, contact).test().assertComplete().assertValue(contact);

    }

    @Test
    public void createNewContact_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Contact");
        contact.setPhoneNumber("7411438334");
        contact.setEmail("ladwa.aditya@gmail.com");

        File file = new File("/path");
        RequestBody imagePart = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("contact[profile_pic]", file.getName(), imagePart);
        RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getFirstName());
        RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getLastName());
        RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), contact.getPhoneNumber());
        RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), contact.getEmail());

        when(mockGoJekService.createContact(firstNamePart, lastNamePart, emailPart, phonePart, image)).thenReturn(Single.just(contact));

        dataManager.createNewContact(firstNamePart, lastNamePart, emailPart, phonePart, image)
                .test().assertComplete().assertValue(contact);

    }


}