package com.example.aditya.gojek.ui.newcontact;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.main.util.RxSchedulersOverrideRule;
import com.example.aditya.gojek.ui.main.util.TestDataFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.io.File;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Aditya on 12-Feb-17.
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class NewContactPresenterTest {

    @Mock DataManager mockDataManager;
    @Mock NewContactContract.View mockView;

    @Rule public final RxSchedulersOverrideRule mOverrideRule = new RxSchedulersOverrideRule();
    @Rule public MockitoRule mockito = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    private NewContactPresenter newContactPresenter;

    @Before public void init() {
        MockitoAnnotations.initMocks(this);
        newContactPresenter = new NewContactPresenter(mockDataManager);
        newContactPresenter.attachView(mockView);
    }

    @After public void tearDown() throws Exception {
        newContactPresenter.detachView();
    }


    @Test
    public void saveNewContact_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Hello");
        contact.setPhoneNumber("88888888888");
        contact.setEmail("abg@gmail.com");
        File file = new File("/path");
        RequestBody imagePart = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("contact[profile_pic]", file.getName(), imagePart);
        RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getFirstName());
        RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getLastName());
        RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), contact.getPhoneNumber());
        RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), contact.getEmail());

        when(mockDataManager.createNewContact(firstNamePart, lastNamePart, emailPart, phonePart, image))
                .thenReturn(Single.just(contact));

        newContactPresenter.saveNewContact(firstNamePart, lastNamePart, emailPart, phonePart, image);

        verify(mockView).setUpView();
        verify(mockView).showContact(contact);
        verify(mockView, never()).showError(any(Throwable.class));

    }


    @Test
    public void saveNewContact_shouldReturnError() throws Exception {

        Contact contact = TestDataFactory.makeContact("Hello");
        contact.setPhoneNumber("88888888888");
        contact.setEmail("abg@gmail.com");
        File file = new File("/path");
        RequestBody imagePart = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("contact[profile_pic]", file.getName(), imagePart);
        RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getFirstName());
        RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getLastName());
        RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), contact.getPhoneNumber());
        RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), contact.getEmail());

        when(mockDataManager.createNewContact(firstNamePart, lastNamePart, emailPart, phonePart, image))
                .thenReturn(Single.error(new RuntimeException()));

        newContactPresenter.saveNewContact(firstNamePart, lastNamePart, emailPart, phonePart, image);

        verify(mockView).setUpView();
        verify(mockView).showError(any(Throwable.class));
        verify(mockView, never()).showContact(any(Contact.class));
    }
}