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
import com.pushtorefresh.storio.contentresolver.queries.Query;

import java.util.List;

import io.reactivex.Single;
import timber.log.Timber;

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

    public boolean saveContactList(List<Contact> contacts) {
        PutResults<Contact> contactPutResults = mStorIOContentResolver.put().objects(contacts).prepare().executeAsBlocking();
        return contactPutResults.numberOfInserts() > 0 || contactPutResults.numberOfUpdates() > 0;
    }

    public Single<List<Contact>> getContactList() {
        List<Contact> contactList = mStorIOContentResolver.get().listOfObjects(Contact.class).withQuery(Query.builder().uri(DatabaseContract.Contacts.CONTENT_URI).build()).prepare().executeAsBlocking();
        Timber.d(String.valueOf(contactList.size()));
        return Single.just(contactList);
    }
}
