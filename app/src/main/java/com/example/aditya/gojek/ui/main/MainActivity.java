package com.example.aditya.gojek.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.databinding.ActivityMainBinding;
import com.example.aditya.gojek.ui.adapter.ContactAdapter;
import com.example.aditya.gojek.ui.base.BaseActivity;
import com.example.aditya.gojek.util.ConnectionReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View, ConnectionReceiver.ConnectionReceiverListener {

    @Inject MainPresenter mainPresenter;

    private ArrayList<Contact> contactArrayList;
    private ActivityMainBinding binding;
    private boolean isNetworkConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityComponent().inject(this);
        setSupportActionBar(binding.toolbar);
        mainPresenter.attachView(this);
    }

    @Override protected void onResume() {
        super.onResume();
        ConnectionReceiver.setConnectionReceiverListener(this);
    }

    @Override public void setUpView() {
        binding.included.recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        isNetworkConnected = ConnectionReceiver.isConnected();
        if (isNetworkConnected) {
            mainPresenter.getContacts();
            binding.included.progressBar.setVisibility(View.VISIBLE);
            binding.included.progressBar.progressiveStart();
        } else {
            Snackbar.make(binding.coordinatorLayout, R.string.no_internet_message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, view -> setUpView())
                    .show();
        }
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override protected void onStop() {
        super.onStop();
        ConnectionReceiver.destoryInstance();
    }

    @Override public void showContact(List<Contact> contacts) {
        if (contacts.size() != 0) {
            contactArrayList = new ArrayList<>(contacts);
            Collections.sort(contactArrayList);
            binding.included.recyclerViewContact.setAdapter(new ContactAdapter(contactArrayList));
            Timber.d(String.valueOf(contacts.size()));
        } else {
            Snackbar.make(binding.coordinatorLayout, R.string.no_contact_found, Snackbar.LENGTH_LONG)
                    .show();
        }
        binding.included.progressBar.progressiveStop();
    }

    @Override public void showError(Throwable error) {
        Timber.d(error.toString());
        error.printStackTrace();
        binding.included.progressBar.progressiveStop();
    }

    @Override public void onNetworkConnectionChanged(boolean isConnected) {
        isNetworkConnected = isConnected;
    }

    public void onClickFab(View view) {
        Toast.makeText(this, "Clicked Fab", Toast.LENGTH_SHORT).show();
    }
}
