package com.example.aditya.gojek.ui.newcontact;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Aditya on 12-Feb-17.
 */

public class NewContactPresenter extends BasePresenter<NewContactContract.View> implements NewContactContract.Presenter {

    private final DataManager mDataManager;

    @Inject public NewContactPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override public void attachView(NewContactContract.View mvpView) {
        super.attachView(mvpView);
        checkViewAttached();
        getMvpView().setUpView();
    }

    @Override public void detachView() {
        super.detachView();
    }

    @Override public void saveNewContact(Contact contact) {

    }
}
