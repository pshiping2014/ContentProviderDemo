package com.psp.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class UserContentProvider extends ContentProvider {

    //授权，和AndroidManifest.xml中的保持一致
    public static final String AUTHORITY = "com.psp.user.provider";
    //匹配Uri的类
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //设置ContentProvider的唯一标识，用于匹配不同的Uri
    private static final int USER_INFO = 1;

    private SQLiteDatabase mDb;

    //初始化
    static {
        //若URI资源路径 = content://com.psp.user.provider/user ，则返回注册码USER_INFO
        uriMatcher.addURI(AUTHORITY, "user", USER_INFO);
    }

    private Context mContext;

    public UserContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uri_code = uriMatcher.match(uri);
        switch (uri_code) {
            case USER_INFO:
                mDb.insert(DBOpenHelper.DB_TABLE_NAME, null, values);
                break;
            default:
                throw new RuntimeException("出错了!!");
                // 向该表添加数据
        }
        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDb = new DBOpenHelper(getContext()).getReadableDatabase();
        //初始化一条数据
        ContentValues initialValues = new ContentValues();
        initialValues.put("name","zhang san");
        initialValues.put("age",18);

        mDb.insert(DBOpenHelper.DB_TABLE_NAME,null,initialValues);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int result = uriMatcher.match(uri);
        //查询数据
        switch (result){
            case USER_INFO:
                return mDb.query(DBOpenHelper.DB_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
            default:
                    throw new RuntimeException("出错了!!");

        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
