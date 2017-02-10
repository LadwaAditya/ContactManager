package com.example.aditya.gojek.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.databinding.ActivityMainBinding;
import com.example.aditya.gojek.ui.adapter.ContactAdapter;
import com.example.aditya.gojek.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject MainPresenter mainPresenter;


    private ArrayList<Contact> contactArrayList;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityComponent().inject(this);
        setSupportActionBar(binding.toolbar);

        mainPresenter.attachView(this);
    }

    @Override public void setUpView() {
        binding.included.recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        mainPresenter.getContacts();
        binding.included.progressBar.progressiveStart();
    }

    @Override public void showContact(List<Contact> contacts) {
        contactArrayList = new ArrayList<>(contacts);
        Collections.sort(contactArrayList);
        binding.included.recyclerViewContact.setAdapter(new ContactAdapter(contactArrayList));
        Timber.d(String.valueOf(contacts.size()));
        binding.included.progressBar.progressiveStop();
    }

    @Override public void showError(Throwable error) {
        Timber.d(error.toString());
        error.printStackTrace();
        binding.included.progressBar.progressiveStop();
    }
}
