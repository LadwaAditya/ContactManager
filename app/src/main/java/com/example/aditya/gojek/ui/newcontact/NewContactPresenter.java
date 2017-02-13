package com.example.aditya.gojek.ui.newcontact;

import com.example.aditya.gojek.data.DataManager;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

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


    @Override public void saveNewContact(RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody phone, MultipartBody.Part image) {
        checkViewAttached();
        addDisposable(
                mDataManager.createNewContact(firstname, lastname, email, phone, image)
                        .toObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Contact>() {
                            @Override public void onNext(Contact contact) {
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
