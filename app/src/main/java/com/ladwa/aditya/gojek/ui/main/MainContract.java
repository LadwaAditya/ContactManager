package com.ladwa.aditya.gojek.ui.main;

import com.ladwa.aditya.gojek.data.model.Contact;
import com.ladwa.aditya.gojek.ui.base.MvpPresenter;
import com.ladwa.aditya.gojek.ui.base.MvpView;

import java.util.List;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class MainContract {
    interface View extends MvpView {
        /**
         * Initial setup of views
         */
        void setUpView();

        /**
         * Shows the contacts
         * @param contacts
         */
        void showContact(List<Contact> contacts);

        /**
         * Show errors
         * @param error
         */
        void showError(Throwable error);

    }

    interface Presenter extends MvpPresenter<View> {
        /**
         * Get all contacts
         */
        void getContacts();
    }
}
