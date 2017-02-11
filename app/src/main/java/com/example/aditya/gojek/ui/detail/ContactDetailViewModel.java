package com.example.aditya.gojek.ui.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devdoo.rxpermissions.RxPermission;
import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.util.FileUtil;

import java.io.File;

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

    public String getProfilePicUrl() {
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

    @BindingAdapter("srcCompat") public static void loadFav(ImageView imageView, boolean srcCompact) {
        imageView.setImageDrawable(srcCompact ? ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_favourite_checked) : ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_favourite_unchecked));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getString(R.string.share_contact))
                .setItems(R.array.contact_share_options, (dialogInterface, i) -> {
                    switch (i) {
                        case 0:
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto:"));
                            intent.putExtra("sms_body", mActivity.getString(R.string.placeholder_name) + getName() + " \n" +
                                    mActivity.getString(R.string.placeholder_phone) + getPhone() + " \n" +
                                    mActivity.getString(R.string.placeholder_email) + getEmail() + "\n");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mActivity.startActivity(intent);
                            Toast.makeText(mActivity, "SMS", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            RxPermission.with(mActivity.getFragmentManager()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(granted -> {
                                        if (granted) {
                                            File vcfFile = FileUtil.createVcfFile(mActivity, mContact);
                                            Intent vcfIntent = new Intent();
                                            Uri contactUri = FileProvider.getUriForFile(mActivity, mActivity
                                                    .getApplicationContext().getPackageName() + ".provider", vcfFile);
                                            vcfIntent.setAction(Intent.ACTION_SEND);
                                            vcfIntent.putExtra(Intent.EXTRA_STREAM, contactUri);
                                            vcfIntent.setType("text/x-vcard");
                                            mActivity.startActivity(Intent.createChooser(vcfIntent, mActivity.getString(R.string.share_vfc)));
                                            Toast.makeText(mActivity, "VFC", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(mActivity, R.string.permission_to_create_file, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            break;
                    }
                }).create().show();
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


    public void onClickFavourite(View view) {
        ImageView imageView = (ImageView) view;
        imageView.setImageDrawable(isFav() ? ContextCompat.getDrawable(mActivity, R.drawable.ic_favourite_unchecked) : ContextCompat.getDrawable(mActivity, R.drawable.ic_favourite_checked));
        mContact.setFavorite(!isFav());
        contactDetailPresenter.setContactFavourite(mContact);
    }


}
