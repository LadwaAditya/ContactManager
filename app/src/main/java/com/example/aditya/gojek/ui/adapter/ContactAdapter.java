package com.example.aditya.gojek.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.aditya.gojek.data.model.Contact;

import java.util.ArrayList;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<Contact> contactArrayList;

    @Override public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = contactArrayList.get(position);
        holder.setContact(contact);
    }

    @Override public int getItemCount() {
        return contactArrayList != null ? contactArrayList.size() : 0;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        public void setContact(Contact contact) {

        }

        public ContactViewHolder(View itemView) {
            super(itemView);
        }
    }
}
