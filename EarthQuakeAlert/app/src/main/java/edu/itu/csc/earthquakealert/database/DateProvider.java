package edu.itu.csc.earthquakealert.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * ContentProvider
 *
 *
 * @author "Jigar Gosalia"
 */
public class DateProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "edu.itu.csc.earthquakealert";
    private static final String URL = "content://" + PROVIDER_NAME + "/registration";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String APP_NAME = "app_name";
    public static final String INSTALL_DATE = "install_date";
    public static final String LAST_DATE = "last_date";

    private DateDataHelper dateDataHelper = null;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dateDataHelper = new DateDataHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = dateDataHelper.getEntry(uri.getPathSegments().get(1), null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri result = null;
        long _id = dateDataHelper.addEntry(contentValues);
        if ( _id > 0 )
            result = ContentUris.withAppendedId(CONTENT_URI, _id);
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String id = uri.getPathSegments().get(1);
        int result = dateDataHelper.deleteEntry(id);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String id = id = uri.getPathSegments().get(1);
        int result = dateDataHelper.updateEntry(id, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}
