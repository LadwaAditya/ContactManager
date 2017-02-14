package com.example.aditya.gojek.ui.detail;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.util.RxSchedulersOverrideRule;
import com.example.aditya.gojek.util.TestDataFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A Test class for @{@link ContactDetailPresenter}
 * Created by Aditya on 11-Feb-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactDetailPresenterTest {

    @Mock DataManager mockDataManager;
    @Mock ContactDetailContract.View mockView;
    @Rule public final RxSchedulersOverrideRule mOverrideRule = new RxSchedulersOverrideRule();

    private ContactDetailPresenter mContactDetailPresenter;
    private int id = 0;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mContactDetailPresenter = new ContactDetailPresenter(mockDataManager);
        mContactDetailPresenter.attachView(mockView);
    }


    @After public void tearDown() throws Exception {
        mContactDetailPresenter.detachView();
    }


    @Test
    public void getContactFromNetwork_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Hello");

        when(mockDataManager.getIndividualContact(id)).thenReturn(Single.just(contact));

        mContactDetailPresenter.getIndividualContact(id);

        verify(mockView).setUpView();
        verify(mockView).showContact(contact);
        verify(mockView, never()).showError(any(Throwable.class));
    }


    @Test
    public void getContactFromNetwork_shouldReturnError() throws Exception {
        when(mockDataManager.getIndividualContact(id)).thenReturn(Single.error(new RuntimeException()));

        mContactDetailPresenter.getIndividualContact(id);

        verify(mockView).setUpView();
        verify(mockView).showError(any(Throwable.class));
        verify(mockView, never()).showContact(any(Contact.class));

    }


    @Test
    public void setContactFavourite_shouldReturnResult() throws Exception {
        Contact contact = TestDataFactory.makeContact("Hello");
        contact.setFavorite(true);

        when(mockDataManager.updateIndividualContact(id, contact)).thenReturn(Single.just(contact));

        mContactDetailPresenter.setContactFavourite(contact);

        verify(mockView).setUpView();
        verify(mockView).showContact(contact);
        verify(mockView, never()).showError(any(Throwable.class));
    }


    @Test
    public void setContactFavourite_shouldReturnError() throws Exception {
        Contact contact = TestDataFactory.makeContact("Hello");
        contact.setFavorite(true);
        when(mockDataManager.updateIndividualContact(id, contact)).thenReturn(Single.error(new RuntimeException()));

        mContactDetailPresenter.setContactFavourite(contact);

        verify(mockView).setUpView();
        verify(mockView).showError(any(Throwable.class));
        verify(mockView, never()).showContact(any(Contact.class));

    }
}