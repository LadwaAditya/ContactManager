package com.example.aditya.gojek.ui.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.databinding.ActivityContactDetailBinding;
import com.example.aditya.gojek.ui.base.BaseActivity;
import com.example.aditya.gojek.util.ConnectionReceiver;

import javax.inject.Inject;


public class ContactDetailActivity extends BaseActivity implements ContactDetailContract.View,
        ConnectionReceiver.ConnectionReceiverListener {

    @Inject ContactDetailPresenter contactDetailPresenter;
    private ActivityContactDetailBinding mBinding;
    private boolean isNetworkConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail);
        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);
        contactDetailPresenter.attachView(this);
    }

    @Override protected void onResume() {
        super.onResume();
        ConnectionReceiver.setConnectionReceiverListener(this);

    }

    @Override public void setUpView() {

        isNetworkConnected = ConnectionReceiver.isConnected();
        if (isNetworkConnected) {
            contactDetailPresenter.getIndividualContact(23);

            mBinding.included.progressBar.setVisibility(View.VISIBLE);
            mBinding.included.progressBar.progressiveStart();
        } else {
            Snackbar.make(mBinding.coordinatorLayout, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, view -> setUpView())
                    .show();
        }
    }

    @Override public void showContact(Contact contact) {

    }

    @Override public void showError(Throwable error) {

    }

    @Override public void onNetworkConnectionChanged(boolean isConnected) {
        isNetworkConnected = isConnected;
    }

    @Override protected void onStop() {
        ConnectionReceiver.destoryInstance();
        super.onStop();
    }

    @Override protected void onDestroy() {
        contactDetailPresenter.detachView();
        super.onDestroy();
    }
}
