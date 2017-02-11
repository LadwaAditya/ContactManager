package com.example.aditya.gojek.ui.detail;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aditya on 10-Feb-17.
 */

public class ContactDetailPresenter extends BasePresenter<ContactDetailContract.View> implements ContactDetailContract.Presenter {


    private final DataManager dataManager;

    @Inject public ContactDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override public void attachView(ContactDetailContract.View mvpView) {
        super.attachView(mvpView);
        checkViewAttached();
        getMvpView().setUpView();
    }

    @Override public void detachView() {
        super.detachView();
    }

    @Override public void getIndividualContact(int id) {
        checkViewAttached();
        addDisposable(dataManager.getIndividualContact(id)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Contact>() {
                    @Override public void onNext(Contact contact) {
                        Timber.d(contact.getPhoneNumber());
                        checkViewAttached();
                        getMvpView().showContact(contact);
                    }

                    @Override public void onError(Throwable e) {
                        checkViewAttached();
                        getMvpView().showError(e);
                    }

                    @Override public void onComplete() {
                        Timber.d("OnComplete");
                    }
                }));

    }
}
