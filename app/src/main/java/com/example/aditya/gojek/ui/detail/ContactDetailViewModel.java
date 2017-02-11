package com.example.aditya.gojek.ui.detail;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

}
