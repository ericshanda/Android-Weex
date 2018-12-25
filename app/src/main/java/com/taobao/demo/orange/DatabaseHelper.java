package com.taobao.demo.orange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wuer on 2018/5/28.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "create table candidate (" +
            "key text primary key, " +
            "clientVal text, " +
            "compareClz text)";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
