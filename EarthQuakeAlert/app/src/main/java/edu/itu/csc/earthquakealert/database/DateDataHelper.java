package edu.itu.csc.earthquakealert.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * DateDataHelper with helper methods.
 *
 * @author "Jigar Gosalia"
 */
public class DateDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DateDatabase.db";

    public static final String TABLE_NAME = "registration";

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
            " ( " + DateProvider._ID + " INTEGER PRIMARY KEY, app_name TEXT, install_date TEXT, last_date TEXT )";

    private static final String SQL_DROP = "DROP TABLE IS EXISTS " + TABLE_NAME ;

    public DateDataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DROP);
        onCreate(sqLiteDatabase);
    }

    public Cursor getEntry(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(TABLE_NAME);
        if(id != null) {
            sqliteQueryBuilder.appendWhere("_id" + " = " + id);
        }
        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    public long addEntry(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(TABLE_NAME, "", values);
        if(id <=0 ) {
            throw new SQLException("Failed to add an entry");
        }
        return id;
    }

    public int deleteEntry(String id) {
        if(id == null) {
            return getWritableDatabase().delete(TABLE_NAME, null , null);
        } else {
            return getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateEntry(String id, ContentValues values) {
        if(id != null) {
            return getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[]{id});
        }
        return 0;
    }
}
