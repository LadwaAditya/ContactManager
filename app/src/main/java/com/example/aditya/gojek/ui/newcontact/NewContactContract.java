package com.example.aditya.gojek.ui.newcontact;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.MvpPresenter;
import com.example.aditya.gojek.ui.base.MvpView;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Aditya on 12-Feb-17.
 */

public class NewContactContract {
    interface View extends MvpView {
        void setUpView();

        void showContact(Contact contact);

        void showError(Throwable error);

    }

    interface Presenter extends MvpPresenter<NewContactContract.View> {
//        void saveNewContact(Contact contact, File file);
        void saveNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image);
    }
}
