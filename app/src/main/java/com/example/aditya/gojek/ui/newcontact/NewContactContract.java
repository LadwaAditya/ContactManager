package com.example.aditya.gojek.ui.newcontact;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.MvpPresenter;
import com.example.aditya.gojek.ui.base.MvpView;

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
        void saveNewContact(Contact contact);
    }
}
