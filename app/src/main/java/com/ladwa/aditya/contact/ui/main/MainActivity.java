package com.ladwa.aditya.contact.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ladwa.aditya.contact.R;
import com.ladwa.aditya.contact.data.model.Contact;
import com.ladwa.aditya.contact.databinding.ActivityMainBinding;
import com.ladwa.aditya.contact.ui.adapter.ContactAdapter;
import com.ladwa.aditya.contact.ui.base.BaseActivity;
import com.ladwa.aditya.contact.ui.newcontact.NewContactActivity;
import com.ladwa.aditya.contact.util.ConnectionReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View, ConnectionReceiver.ConnectionReceiverListener {

    @Inject MainPresenter mainPresenter;

    public static final int REQUEST_ADD_CONTACT = 100;

    private ArrayList<Contact> contactArrayList;
    private ActivityMainBinding mBinding;
    private boolean isNetworkConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);
        mainPresenter.attachView(this);
        animateFab();
    }

    @Override protected void onResume() {
        super.onResume();
        ConnectionReceiver.setConnectionReceiverListener(this);
    }

    @Override public void setUpView() {
        mBinding.included.recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        isNetworkConnected = ConnectionReceiver.isConnected();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.title_contacts_app);
        }
        if (isNetworkConnected) {
            mainPresenter.getContacts();
            mBinding.included.progressBar.setVisibility(View.VISIBLE);
            mBinding.included.progressBar.progressiveStart();
        } else {
            Snackbar.make(mBinding.coordinatorLayout, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, view -> setUpView())
                    .show();
        }
    }


    @Override protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    @Override protected void onStop() {
        ConnectionReceiver.destoryInstance();
        super.onStop();
    }

    @Override public void showContact(List<Contact> contacts) {
        if (contacts.size() != 0) {
            contactArrayList = new ArrayList<>(contacts);
            Collections.sort(contactArrayList);
            mBinding.included.recyclerViewContact.setAdapter(new ContactAdapter(contactArrayList));
            Timber.d(String.valueOf(contacts.size()));
        } else {
            mBinding.included.txtNoContacts.setVisibility(View.VISIBLE);
            Snackbar.make(mBinding.coordinatorLayout, R.string.no_contact_found, Snackbar.LENGTH_LONG)
                    .show();
        }
        mBinding.included.progressBar.progressiveStop();
    }

    @Override public void showError(Throwable error) {
        Timber.d(error.toString());
        error.printStackTrace();
        mBinding.included.progressBar.progressiveStop();
    }

    @Override public void onNetworkConnectionChanged(boolean isConnected) {
        isNetworkConnected = isConnected;
    }

    public void onClickFab(View view) {
        startActivityForResult(new Intent(this, NewContactActivity.class), REQUEST_ADD_CONTACT);
    }

    private void animateFab() {
        mBinding.fab.setScaleX(0);
        mBinding.fab.setScaleY(0);
        mBinding.fab.animate().scaleX(1).scaleY(1).start();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_CONTACT && resultCode == Activity.RESULT_OK) {
            Timber.d("Back for start activity result");
            mainPresenter.getContacts();
            Toast.makeText(this, R.string.refreshing_contacts, Toast.LENGTH_SHORT).show();
        }
    }
}
