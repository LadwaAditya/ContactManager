package com.example.aditya.gojek.ui.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.databinding.ListItemContactBinding;

import java.util.ArrayList;

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
        holder.bindContact(contact);
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

        public void bindContact(Contact contact) {
            mContact = contact;
            mBinding.setContact(contact);
            mBinding.executePendingBindings();
        }

        @BindingAdapter("imageUrl")
        public static void loadImage(ImageView imageView, String imageUrl) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .into(imageView);
        }

        public void onClick(View view) {
            Toast.makeText(view.getContext(), mContact.getFirstName(), Toast.LENGTH_SHORT).show();
        }
    }
}
