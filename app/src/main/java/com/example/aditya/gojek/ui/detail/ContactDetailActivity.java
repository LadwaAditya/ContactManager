package com.example.aditya.gojek.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.databinding.ActivityContactDetailBinding;
import com.example.aditya.gojek.ui.base.BaseActivity;
import com.example.aditya.gojek.util.ConnectionReceiver;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import javax.inject.Inject;


public class ContactDetailActivity extends BaseActivity implements ContactDetailContract.View,
        ConnectionReceiver.ConnectionReceiverListener {

    @Inject ContactDetailPresenter contactDetailPresenter;
    private ActivityContactDetailBinding mBinding;
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
        mBinding.included.imgUser.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_heart).colorRes(R.color.colorAccent));
        mBinding.included.imgPhone.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_phone).colorRes(R.color.colorAccent));
        mBinding.included.imgEmail.setImageDrawable(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_email).colorRes(R.color.colorAccent));

        mBinding.included.txtContactName.setText(String.format(getString(R.string.format_firstname_lastname), mContact.getFirstName(), mContact.getLastName()));
        getSupportActionBar().setTitle(String.format(getString(R.string.format_firstname_lastname), mContact.getFirstName(), mContact.getLastName()));

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
