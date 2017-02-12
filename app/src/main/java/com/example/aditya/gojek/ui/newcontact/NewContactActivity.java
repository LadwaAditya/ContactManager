package com.example.aditya.gojek.ui.newcontact;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.databinding.ActivityNewContactBinding;
import com.example.aditya.gojek.ui.base.BaseActivity;


public class NewContactActivity extends BaseActivity {

    private ActivityNewContactBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);
        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);

    }

}
