package com.example.aditya.gojek.ui.newcontact;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.MvpPresenter;
import com.example.aditya.gojek.ui.base.MvpView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Aditya on 12-Feb-17.
 */

public class NewContactContract {
    interface View extends MvpView {
        /**
         * Set up the view
         */
        void setUpView();

        /**
         * Show contact
         * @param contact
         */
        void showContact(Contact contact);

        /**
         * Show errors
         * @param error
         */
        void showError(Throwable error);

    }

    interface Presenter extends MvpPresenter<NewContactContract.View> {
        /**
         * Save a new Contact to server
         * @param firstname
         * @param lastname
         * @param email
         * @param phone
         * @param image
         */
        void saveNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image);
    }
}
