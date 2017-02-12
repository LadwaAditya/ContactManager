package com.example.aditya.gojek.ui.newcontact;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devdoo.rxpermissions.RxPermission;
import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.databinding.ActivityNewContactBinding;
import com.example.aditya.gojek.ui.base.BaseActivity;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class NewContactActivity extends BaseActivity implements NewContactContract.View {

    private final int REQUEST_CODE_CAPTURE_IMAGE = 100;
    private final int REQUEST_CODE_GALLERY = 101;

    private ActivityNewContactBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);
        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);
    }

    @Override public void setUpView() {

    }

    @Override public void showContact(Contact contact) {

    }

    @Override public void showError(Throwable error) {

    }

    public void onClickImage(View view) {
        RxPermission.with(this.getFragmentManager()).request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(R.string.choose_image)
                                .setItems(R.array.choose_image_type, (dialogInterface, i) -> {
                                    switch (i) {
                                        case 0:
                                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE);
                                            break;
                                        case 1:
                                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
                                            break;
                                    }
                                }).create().show();
                    } else {
                        Toast.makeText(this, R.string.camara_permission, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onClickSaveContact(View view) {
        boolean name = true, phone = true;
        String firstName = mBinding.included.txtFirstName.getText().toString();
        String mobileNumber = mBinding.included.txtMobileNumber.getText().toString();

        if (firstName.length() < 3) {
            name = false;
            Snackbar.make(mBinding.coordinatorLayout, "First Name not valid", Snackbar.LENGTH_LONG)
                    .show();
        }
        if (mobileNumber.length() >= 12 && mobileNumber.length() <= 15) {
            if (mobileNumber.length() == 15) {
                boolean flag;
                flag = Character.toString(mobileNumber.charAt(0)).equalsIgnoreCase("+");
                if (!flag) {
                    phone = false;
                    Snackbar.make(mBinding.coordinatorLayout, "Number not valid", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        } else {
            phone = false;
            Snackbar.make(mBinding.coordinatorLayout, "Number not valid", Snackbar.LENGTH_LONG)
                    .show();
        }

        if (phone && name) {
            //Make rest call
            Toast.makeText(this, "Fields are valid", Toast.LENGTH_SHORT).show();
        }

    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .error(R.mipmap.round)
                    .transform(new CropCircleTransformation(this))
                    .into(mBinding.included.imgProfile);
        }

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                Uri imageUri = data.getData();
                Glide.with(this)
                        .load(imageUri)
                        .error(R.mipmap.round)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(mBinding.included.imgProfile);

            }
        }
    }
}
