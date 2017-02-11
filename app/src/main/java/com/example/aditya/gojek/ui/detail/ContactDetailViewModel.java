package com.example.aditya.gojek.ui.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devdoo.rxpermissions.RxPermission;
import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Aditya on 11-Feb-17.
 */

public class ContactDetailViewModel extends BaseObservable {

    private final Activity mActivity;
    private Contact mContact;
    private ContactDetailPresenter contactDetailPresenter;

    public ContactDetailViewModel(Activity activity, ContactDetailPresenter contactDetailPresenter) {
        this.mActivity = activity;
        this.contactDetailPresenter = contactDetailPresenter;
    }

    public Contact getContact() {
        return mContact;
    }

    public void setContact(Contact contact) {
        this.mContact = contact;
        notifyChange();
    }


    @Bindable public String getName() {
        return String.format(mActivity.getString(R.string.format_firstname_lastname), mContact.getFirstName(), mContact.getLastName());
    }

    @Bindable public String getPhone() {
        return mContact.getPhoneNumber();
    }

    @Bindable public String getEmail() {
        return mContact.getEmail();
    }

    @Bindable public boolean isFav() {
        return mContact.isFavorite();
    }

    @Bindable public String getProfilePicUrl() {
        return mContact.getProfilePic();
    }

    @BindingAdapter("imageUrl") public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.round)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }

    public void onClickSendMessage(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + getPhone()));
        if (intent.resolveActivity(mActivity.getPackageManager()) != null)
            mActivity.startActivity(Intent.createChooser(intent, mActivity.getString(R.string.intent_chooser_title_sms)));
        else
            Toast.makeText(mActivity, R.string.no_activity_sms, Toast.LENGTH_SHORT).show();
    }

    public void onClickShareContact(View view) {
        Context context = view.getContext();
        Toast.makeText(context, mContact.getFirstName(), Toast.LENGTH_SHORT).show();
    }

    public void onClickSendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getEmail()});
        if (intent.resolveActivity(mActivity.getPackageManager()) != null)
            mActivity.startActivity(Intent.createChooser(intent, mActivity.getString(R.string.intent_chooser_title_email)));
        else
            Toast.makeText(mActivity, R.string.no_activity_email, Toast.LENGTH_SHORT).show();
    }


    public void onClickCallPhone(View view) {
        RxPermission.with(mActivity.getFragmentManager()).request(Manifest.permission.CALL_PHONE).subscribe(granted -> {
            if (granted) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getPhone()));
                if (intent.resolveActivity(mActivity.getPackageManager()) != null)
                    mActivity.startActivity(intent);
                else
                    Toast.makeText(mActivity, R.string.no_activity_call, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, R.string.permission_call, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
