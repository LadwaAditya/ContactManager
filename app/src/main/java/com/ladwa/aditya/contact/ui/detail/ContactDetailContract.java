package com.ladwa.aditya.contact.ui.detail;

import com.ladwa.aditya.contact.data.model.Contact;
import com.ladwa.aditya.contact.ui.base.MvpPresenter;
import com.ladwa.aditya.contact.ui.base.MvpView;

/**
 * Created by Aditya on 10-Feb-17.
 */

public class ContactDetailContract {

    interface View extends MvpView {
        /**
         * Set up the views
         */
        void setUpView();

        /**
         * Show contacts
         * @param contact
         */
        void showContact(Contact contact);

        /**
         * Show errors
         * @param error
         */
        void showError(Throwable error);

    }

    interface Presenter extends MvpPresenter<ContactDetailContract.View> {
        /**
         * Get individual contact
         * @param id
         */
        void getIndividualContact(int id);

        /**
         * Set a contact as favourite
         * @param contact
         */
        void setContactFavourite(Contact contact);
    }
}
