package com.example.aditya.gojek.data.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.example.aditya.gojek.BuildConfig;

/**
 * Created by Aditya on 09-Feb-17.
 */

public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    public static final String PATH_CONTACTS = "contacts";

    private DatabaseContract() {
    }

    public static abstract class Contacts implements BaseColumns {
        @NonNull public static final String CONTENT_URI_STRING = "content://" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

        public static final String CONTENT_DOWNVOTE_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;
        public static final String CONTENT_DOWNVOTE_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;

        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_CONTACT_ID = "contact_id";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_PROFILE_PIC = "profile_pic";
        public static final String COLUMN_URL = "url";

        public static String getContactsCreateQuery() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_CONTACT_ID + " TEXT NOT NULL PRIMARY KEY , " +
                    COLUMN_FIRST_NAME + " TEXT NOT NULL , " +
                    COLUMN_LAST_NAME + " TEXT NOT NULL , " +
                    COLUMN_PROFILE_PIC + " TEXT NOT NULL , " +
                    COLUMN_URL + " TEXT NOT NULL" + ");";
        }

        public static String getContactsDeleteQuery() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static Uri buildContactsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
