package com.example.aditya.gojek.data.local;

import android.content.Context;

import com.example.aditya.gojek.data.model.Contact;
import com.example.aditya.gojek.data.model.ContactStorIOContentResolverDeleteResolver;
import com.example.aditya.gojek.data.model.ContactStorIOContentResolverGetResolver;
import com.example.aditya.gojek.data.model.ContactStorIOContentResolverPutResolver;
import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.operations.put.PutResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class GoJekLocalRepository {
    private StorIOContentResolver mStorIOContentResolver;
    private Context context;

    public static GoJekLocalRepository makeGoJekLocalData(Context context) {
        return new GoJekLocalRepository(context);
    }

    private GoJekLocalRepository(Context context) {
        this.context = context;
        mStorIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(context.getContentResolver())
                .addTypeMapping(Contact.class, ContentResolverTypeMapping.<Contact>builder()
                        .putResolver(new ContactStorIOContentResolverPutResolver())
                        .getResolver(new ContactStorIOContentResolverGetResolver())
                        .deleteResolver(new ContactStorIOContentResolverDeleteResolver())
                        .build()).build();
    }

    public boolean saveContactList(ArrayList<Contact> contacts) {
        PutResults<Contact> contactPutResults = mStorIOContentResolver.put().objects(contacts).prepare().executeAsBlocking();
        return contactPutResults.numberOfInserts() > 0 || contactPutResults.numberOfUpdates() > 0;
    }
}
