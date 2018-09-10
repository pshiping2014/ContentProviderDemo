package com.psp.contentproviderdemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by psp on 2018/9/7.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    //数据库名
    private static final String DB_NAME = "user.db";
    //表名
    public static final String DB_TABLE_NAME = "user";
    //数据可版本号
    private static final int DB_VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一张用户表
        String sql_create_user = "CREATE TABLE IF NOT EXISTS "+DB_TABLE_NAME+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,age TEXT)";
        db.execSQL(sql_create_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
