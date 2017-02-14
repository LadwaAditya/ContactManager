package com.ladwa.aditya.gojek.ui.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ladwa.aditya.gojek.R;
import com.ladwa.aditya.gojek.data.model.Contact;
import com.ladwa.aditya.gojek.databinding.ListItemContactBinding;
import com.ladwa.aditya.gojek.ui.detail.ContactDetailActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * An Adapter that holds contact info
 * Created by Aditya on 09-Feb-17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<Contact> contactArrayList;

    public ContactAdapter(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }

    @Override public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemContactBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(binding);
    }

    @Override public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = contactArrayList.get(position);
        holder.bindContact(contact, contactArrayList);
    }

    @Override public int getItemCount() {
        return contactArrayList != null ? contactArrayList.size() : 0;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ListItemContactBinding mBinding;
        private Contact mContact;

        public ContactViewHolder(ListItemContactBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.setHolder(this);
        }

        public void bindContact(Contact contact, ArrayList<Contact> contactArrayList) {
            mContact = contact;
            mBinding.setContact(contact);
            mBinding.executePendingBindings();
            String firstAlpha = String.valueOf(contact.getFirstName().replace(" ", "").charAt(0)).toUpperCase();
            if (getAdapterPosition() != 0) {
                if (contact.isFirstAlpha()) {
                    mBinding.txtAlpha.setText(firstAlpha);
                } else {
                    String alpha = String.valueOf(contactArrayList.get(getAdapterPosition() - 1).getFirstName().replace(" ", "").charAt(0)).toUpperCase();
                    if (!firstAlpha.equalsIgnoreCase(alpha)) {
                        contact.setFirstAlpha(true);
                        mBinding.txtAlpha.setText(firstAlpha);
                    } else {
                        mBinding.txtAlpha.setText("");
                    }
                }
            } else {
                mBinding.txtAlpha.setText(firstAlpha);
            }

        }

        @BindingAdapter({"bind:imageUrl", "bind:error"}) public static void loadImage(ImageView imageView, String imageUrl, Drawable error) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error)
                    .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                    .into(imageView);
        }

        public void onClick(View view) {
            Context context = view.getContext();
            ContactDetailActivity.show(context, mContact);
        }
    }
}
