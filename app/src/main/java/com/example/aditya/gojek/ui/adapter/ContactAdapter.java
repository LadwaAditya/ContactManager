package com.example.aditya.gojek.ui.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aditya.gojek.R;
import com.example.aditya.gojek.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {


    private ArrayList<Contact> contactArrayList;

    public ContactAdapter(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }

    @Override public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = contactArrayList.get(position);
        holder.setContact(contact);
    }

    @Override public int getItemCount() {
        return contactArrayList != null ? contactArrayList.size() : 0;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_profile) ImageView imgProfile;
        @BindView(R.id.txt_contact_name) AppCompatTextView txtContactName;

        public void setContact(Contact contact) {
            txtContactName.setText(contact.getFirstName() + " " + contact.getLastName());
        }

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
