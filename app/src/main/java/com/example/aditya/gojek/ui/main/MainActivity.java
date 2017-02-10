package com.example.aditya.gojek.ui.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.ui.adapter.ContactAdapter;
import com.example.aditya.gojek.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject MainPresenter mainPresenter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView_contact) RecyclerView recyclerViewContact;
    @BindView(R.id.progress_bar) SmoothProgressBar progressBar;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activityComponent().inject(this);
        setSupportActionBar(toolbar);

        mainPresenter.attachView(this);
    }


    @Override public void setUpView() {
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        mainPresenter.getContacts();
        progressBar.progressiveStart();
    }

    @Override public void showContact(ArrayList<Contact> contacts) {
        recyclerViewContact.setAdapter(new ContactAdapter(contacts));
        Timber.d(String.valueOf(contacts.size()));
        progressBar.progressiveStop();
    }

    @Override public void showError(Throwable error) {
        Timber.d(error.toString());
        progressBar.progressiveStop();
    }
}
