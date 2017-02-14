package com.ladwa.aditya.gojek.ui.main;

import com.ladwa.aditya.gojek.data.DataManager;
import com.ladwa.aditya.gojek.data.model.Contact;
import com.ladwa.aditya.gojek.util.RxSchedulersOverrideRule;
import com.ladwa.aditya.gojek.util.TestDataFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * MainPresenter test using
 * Created by Aditya on 09-Feb-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock DataManager mockDataManager;
    @Mock MainContract.View mockView;
    @Rule public final RxSchedulersOverrideRule mOverrideRule = new RxSchedulersOverrideRule();

    private MainPresenter mainPresenter;

    @Before public void init() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(mockDataManager);
        mainPresenter.attachView(mockView);
    }

    @After public void tearDown() throws Exception {
        mainPresenter.detachView();
    }


    @Test public void getContacts_shouldReturnResult() throws Exception {
        ArrayList<Contact> contactList = TestDataFactory.makeContactList(5);
        when(mockDataManager.getContactFromRemote())
                .thenReturn(Single.just(contactList));

        mainPresenter.getContacts();

        verify(mockView).setUpView();
        verify(mockView).showContact(contactList);
        verify(mockView, never()).showError(any(Throwable.class));

    }


    @Test
    public void getContact_shouldThrowError() throws Exception {
        when(mockDataManager.getContactFromRemote())
                .thenReturn(Single.error(new RuntimeException()));

        mainPresenter.getContacts();

        verify(mockView).setUpView();
        verify(mockView).showError(any(Throwable.class));
        verify(mockView, never()).showContact(anyList());
    }
}