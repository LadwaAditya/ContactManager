package com.example.aditya.gojek.ui.main;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Presenter for Main Screen
 * Created by Aditya on 09-Feb-17.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private final DataManager mDataManager;

    @Inject public MainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override public void attachView(MainContract.View mvpView) {
        super.attachView(mvpView);
        checkViewAttached();
        getMvpView().setUpView();
    }

    @Override public void detachView() {
        super.detachView();
    }

    @Override public void getContacts() {
        checkViewAttached();
        addDisposable(mDataManager.getContactFromRemote()
                .toObservable()
                .doOnNext(mDataManager::putContactsInDatabase)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<ArrayList<Contact>, ObservableSource<ArrayList<Contact>>>() {
                    @Override public ObservableSource<ArrayList<Contact>> apply(ArrayList<Contact> contacts) throws Exception {
                        Collections.sort(contacts);
                        return Observable.just(contacts);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Contact>>() {
                    @Override public void onNext(ArrayList<Contact> contacts) {

                        getMvpView().showContact(contacts);
                    }

                    @Override public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override public void onComplete() {
                        Timber.d("Completed");
                    }
                })

        );
    }
}
