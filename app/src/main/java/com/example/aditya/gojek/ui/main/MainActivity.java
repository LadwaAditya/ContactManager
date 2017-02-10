package com.example.aditya.gojek.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.adapter.ContactAdapter;
import com.example.aditya.gojek.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject MainPresenter mainPresenter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView_contact) RecyclerView recyclerViewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setSupportActionBar(toolbar);

        mainPresenter.attachView(this);
    }

    @Override public int getLayout() {
        return R.layout.activity_main;
    }

    @Override public void setUpView() {
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        mainPresenter.getContacts();
    }

    @Override public void showContact(ArrayList<Contact> contacts) {
        recyclerViewContact.setAdapter(new ContactAdapter(contacts));
        Timber.d(String.valueOf(contacts.size()));
    }

    @Override public void showError(Throwable error) {
        Timber.d(error.toString());
    }
}
