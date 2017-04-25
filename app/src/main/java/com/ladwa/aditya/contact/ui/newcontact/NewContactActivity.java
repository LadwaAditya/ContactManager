package com.ladwa.aditya.contact.ui.newcontact;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devdoo.rxpermissions.RxPermission;
import com.ladwa.aditya.contact.R;
import com.ladwa.aditya.contact.data.model.Contact;
import com.ladwa.aditya.contact.databinding.ActivityNewContactBinding;
import com.ladwa.aditya.contact.ui.base.BaseActivity;
import com.ladwa.aditya.contact.util.ConnectionReceiver;
import com.ladwa.aditya.contact.util.FieldUtil;
import com.ladwa.aditya.contact.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class NewContactActivity extends BaseActivity implements NewContactContract.View,
        ConnectionReceiver.ConnectionReceiverListener {

    @Inject NewContactPresenter newContactPresenter;

    private final int REQUEST_CODE_CAPTURE_IMAGE = 100;
    private final int REQUEST_CODE_GALLERY = 101;

    private ActivityNewContactBinding mBinding;
    private boolean isNetworkConnected;
    private Uri imageUri;
    private Bitmap photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);

        activityComponent().inject(this);
        setSupportActionBar(mBinding.toolbar);
        newContactPresenter.attachView(this);
    }

    @Override protected void onResume() {
        super.onResume();
        ConnectionReceiver.setConnectionReceiverListener(this);
    }

    @Override public void setUpView() {
        isNetworkConnected = ConnectionReceiver.isConnected();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.title_add_new_contact);
        }

    }

    @Override public void showContact(Contact contact) {
        stopProgressBar();
        Toast.makeText(this, R.string.user_successfully_added, Toast.LENGTH_SHORT).show();
        //TODO Redirect back to main activity and refresh list
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.extra_contact), contact);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override public void showError(Throwable error) {
        stopProgressBar();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error_adding_new_contact)
                .setPositiveButton(R.string.retry, (dialog, which) -> saveContact()).setNegativeButton(R.string.cancel, (dialog, which) -> finish()).create().show();
        error.printStackTrace();
    }

    public void onClickImage(View view) {
        RxPermission.with(this.getFragmentManager()).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(R.string.choose_image)
                                .setItems(R.array.choose_image_type, (dialogInterface, i) -> {
                                    switch (i) {
                                        case 0:
                                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
        saveContact();
    }

    private void saveContact() {
        boolean name = true, phone = true, emailflag = true, lastname = true;
        String firstName = mBinding.included.txtFirstName.getText().toString();
        String lastName = mBinding.included.txtLastName.getText().toString();
        String email = mBinding.included.txtEmail.getText().toString();
        String mobileNumber = mBinding.included.txtMobileNumber.getText().toString();

        if (firstName.length() < 3) {
            name = false;
            Snackbar.make(mBinding.coordinatorLayout, R.string.first_name_invalid, Snackbar.LENGTH_LONG)
                    .show();
        }
        if (lastName.length() == 0) {
            lastname = false;
            Snackbar.make(mBinding.coordinatorLayout, R.string.invalid_last_name, Snackbar.LENGTH_LONG)
                    .show();
        }
        if (mobileNumber.length() < 10 || mobileNumber.length() > 13) {
            phone = false;
            Snackbar.make(mBinding.coordinatorLayout, R.string.phone_number_invalid, Snackbar.LENGTH_LONG)
                    .show();
        }

        if (!FieldUtil.isEmailValid(email)) {
            emailflag = false;
            Snackbar.make(mBinding.coordinatorLayout, R.string.email_is_invalid, Snackbar.LENGTH_LONG)
                    .show();
        }
        if (phone && name && emailflag && lastname) {
            //Make rest call
            Contact contact = new Contact();
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setPhoneNumber(mobileNumber);
            contact.setEmail(email);
            if (imageUri != null) {
                RxPermission.with(this.getFragmentManager())
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            File file = FileUtil.createImageFile(this, photo);
                            if (isNetworkConnected) {
                                RequestBody imagePart = RequestBody.create(MediaType.parse("image/jpg"), file);
                                MultipartBody.Part image = MultipartBody.Part.createFormData("contact[profile_pic]", file.getName(), imagePart);
                                RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getFirstName());
                                RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), contact.getLastName());
                                RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), contact.getEmail());
                                RequestBody phonePart = RequestBody.create(MediaType.parse("text/plain"), contact.getPhoneNumber());
                                newContactPresenter.saveNewContact(firstNamePart, lastNamePart, emailPart, phonePart, image);
                                startProgressBar();
                            } else {
                                Snackbar.make(mBinding.coordinatorLayout, R.string.no_internet_message, Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
            } else {
                Snackbar.make(mBinding.coordinatorLayout, R.string.choose_image_to_upload, Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imageUri = getImageUri(this, photo);
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
                imageUri = data.getData();
                try {
                    photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this)
                        .load(imageUri)
                        .error(R.mipmap.round)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(mBinding.included.imgProfile);

            }
        }
    }

    private void startProgressBar() {
        mBinding.included.progressBar.setVisibility(View.VISIBLE);
        mBinding.included.progressBar.progressiveStart();
    }

    private void stopProgressBar() {
        mBinding.included.progressBar.progressiveStop();
    }

    @Override protected void onDestroy() {
        newContactPresenter.detachView();
        super.onDestroy();
    }

    @Override protected void onStop() {
        ConnectionReceiver.destoryInstance();
        super.onStop();
    }

    @Override public void onNetworkConnectionChanged(boolean isConnected) {
        isNetworkConnected = isConnected;
    }
}
