package edu.itu.exam;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class ImagesProvider extends ContentProvider {

    static final String PROVIDER_NAME = "edu.itu.exam.provider";
    static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/images");
    private static final int IMAGES = 1;
    private static final int IMAGE_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "images", IMAGES);
        uriMatcher.addURI(PROVIDER_NAME, "images/#", IMAGE_ID);
        return uriMatcher;
    }

    private ImageDataBase imageDataBase = null;

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case IMAGES:
                return "vnd.android.cursor.dir/vnd.edu.itu.exam.provider.images";
            case IMAGE_ID:
                return "vnd.android.cursor.item/vnd.edu.itu.exam.provider.images";

        }
        return "";
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        imageDataBase = new ImageDataBase(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor curs = null;
        // TO DO
        switch (uriMatcher.match(uri)) {
            case IMAGE_ID: {
                curs = imageDataBase.getReadableDatabase().query(
                        ImageDataBase.TABLE_NAME,
                        projection,
                        ImageDataBase.TABLE_NAME + "." + "_id = ? ",
                        new String[]{uri.getPathSegments().get(1)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case IMAGES: {
                curs = imageDataBase.getReadableDatabase().query(
                        ImageDataBase.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        curs.setNotificationUri(getContext().getContentResolver(), uri);
        return curs;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uriRes = null;
		// TO DO
        final SQLiteDatabase db = imageDataBase.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case IMAGES: {
                long _id = db.insert(ImageDataBase.TABLE_NAME, null, values);
                if ( _id > 0 )
                    uriRes = ContentUris.withAppendedId(CONTENT_URI, _id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uriRes;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = null;
        if(uriMatcher.match(uri) == IMAGE_ID) {
            //Delete is for one single image. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }

        return imageDataBase.deleteImages(id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = null;
        if(uriMatcher.match(uri) == IMAGE_ID) {
            //Update is for one single image. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }
        return imageDataBase.updateImages(id, values);
    }
}