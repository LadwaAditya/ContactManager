package com.example.aditya.gojek.ui.detail;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.MvpPresenter;
import com.example.aditya.gojek.ui.base.MvpView;

/**
 * Created by Aditya on 10-Feb-17.
 */

public class ContactDetailContract {

    interface View extends MvpView {
        void setUpView();

        void showContact(Contact contact);

        void showError(Throwable error);

    }

    interface Presenter extends MvpPresenter<ContactDetailContract.View> {
        void getIndividualContact(int id);
    }
}
