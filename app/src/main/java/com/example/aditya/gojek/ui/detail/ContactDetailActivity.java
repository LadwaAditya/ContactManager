package com.example.aditya.gojek.ui.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.databinding.ActivityContactDetailBinding;
import com.example.aditya.gojek.ui.base.BaseActivity;


public class ContactDetailActivity extends BaseActivity {

    private ActivityContactDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail);
        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);
    }

}
