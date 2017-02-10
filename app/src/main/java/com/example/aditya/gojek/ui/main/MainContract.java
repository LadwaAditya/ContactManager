package com.example.aditya.gojek.ui.main;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.MvpPresenter;
import com.example.aditya.gojek.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class MainContract {
    interface View extends MvpView {
        void setUpView();

        void showContact(ArrayList<Contact> contacts);

        void showError(Throwable error);
    }

    interface Presenter extends MvpPresenter<View> {
        void getContacts();
    }
}
