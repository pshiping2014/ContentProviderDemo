package com.psp.contentproviderdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //匹配uri和UserContentProvider中的保持一致
    public static final Uri USER_CONTENT_URI = Uri.parse("content://com.psp.user.provider/user");

    Button query_user;
    Button insert_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query_user = findViewById(R.id.bt_querry_user_info);
        insert_user = findViewById(R.id.bt_insert_user_info);

        query_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryProviderData(MainActivity.this);
            }
        });

        insert_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProviderData(MainActivity.this,"li si",22);
            }
        });

    }

    //查询数据库
    public static String queryProviderData(Context context) {
        try {
            Cursor cursor = context.getContentResolver().query(USER_CONTENT_URI,
                    new String[]{"_id", "name","age"}, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Log.d("psp_log","_id= "+cursor.getInt(0)+"\n"+
                            "name="+cursor.getString(1)+"\n"+"age="+cursor.getInt(2));
                    Toast.makeText(context,"name="+cursor.getString(1)+"\n"+"age="+cursor.getInt(2),Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }else {
                Log.d("psp_log","snCursor = null" );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //向数据库中插入数据
    public static void insertProviderData(Context context,String name,int age) {
            ContentValues userValues = new ContentValues();
            userValues.put("name", name);
            userValues.put("age", age);
            context.getContentResolver().insert(USER_CONTENT_URI, userValues);
    }
}
