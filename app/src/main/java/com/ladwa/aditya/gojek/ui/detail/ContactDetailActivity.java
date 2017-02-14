package com.ladwa.aditya.gojek.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.ladwa.aditya.gojek.R;
import com.ladwa.aditya.gojek.data.model.Contact;
import com.ladwa.aditya.gojek.databinding.ActivityContactDetailBinding;
import com.ladwa.aditya.gojek.ui.base.BaseActivity;
import com.ladwa.aditya.gojek.util.ConnectionReceiver;

import javax.inject.Inject;


public class ContactDetailActivity extends BaseActivity implements ContactDetailContract.View,
        ConnectionReceiver.ConnectionReceiverListener {

    @Inject ContactDetailPresenter contactDetailPresenter;
    private ActivityContactDetailBinding mBinding;
    private ContactDetailViewModel contactDetailViewModel;
    private boolean isNetworkConnected;
    private Contact mContact;

    public static void show(@NonNull Context context, Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra(context.getString(R.string.extra_contact), contact);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail);

        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);

        mContact = getIntent().getParcelableExtra(getString(R.string.extra_contact));
        contactDetailPresenter.attachView(this);
    }

    @Override protected void onResume() {
        super.onResume();
        ConnectionReceiver.setConnectionReceiverListener(this);
    }

    @Override public void setUpView() {
        isNetworkConnected = ConnectionReceiver.isConnected();
        contactDetailViewModel = new ContactDetailViewModel(this, contactDetailPresenter);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(mContact.getFirstName());
        }
        contactDetailViewModel.setContact(mContact);
        mBinding.setViewmodel(contactDetailViewModel);


        if (isNetworkConnected) {
            contactDetailPresenter.getIndividualContact(mContact.getId());
            mBinding.included.progressBar.setVisibility(View.VISIBLE);
            mBinding.included.progressBar.progressiveStart();
        } else {
            Snackbar.make(mBinding.coordinatorLayout, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, view -> setUpView())
                    .show();
        }
    }

    @Override public void showContact(Contact contact) {
        contactDetailViewModel.setContact(contact);
        Toast.makeText(this, contact.isFavorite() ? "Favourite" : "Unfavourite", Toast.LENGTH_SHORT).show();
        hideProgressBar();
    }

    private void hideProgressBar() {
        mBinding.included.progressBar.progressiveStop();
        mBinding.included.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void showError(Throwable error) {
        error.printStackTrace();
        hideProgressBar();
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
