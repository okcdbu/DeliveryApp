package com.donga.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    myDBHelper MyHelper;
    SQLiteDatabase sqldb;
    String Category;
    Cursor cursor;
    Intent intent1;
    public static final String ROOT_DIR = "/data/data/com.donga.deliveryapp/databases/";

    public static void setDB(Context ctx) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        // db파일 이름 적어주기
        File outfile = new File(ROOT_DIR+"database.db");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open("database.db", AssetManager.ACCESS_BUFFER);
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {}
        } catch (IOException e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setDB(this);
        Intent intent = getIntent();
        Category = intent.getStringExtra("category");
        ListView listView = (ListView)findViewById(R.id.listview);
        TextView textView = (TextView)findViewById(R.id.category);
        textView.setText(Category.toUpperCase());


        MyHelper = new myDBHelper(this);
        sqldb = MyHelper.getWritableDatabase();

        cursor = sqldb.rawQuery("SELECT * FROM store WHERE Category = '"+ Category + "'",null);
        cursor.moveToFirst();

        Log.e("Tag",cursor.getCount()+"");
        String[] from = new String[]{"Name","Rate","Tel","Minprice","Time","Tip","_id","Category"};
        int[] to = new int[]{R.id.tv_name,R.id.tv_rate,R.id.tv_Tel,R.id.tv_minprice,R.id.tv_time,R.id.tv_tip};
        StoreAdapter mAdapter = new StoreAdapter(this,R.layout.store_list,cursor,from,to,0);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                intent1 = new Intent(getApplicationContext(),StoreActivity.class);
                intent1.putExtra(cursor.getColumnName(0),cursor.getString(0));
                intent1.putExtra(cursor.getColumnName(1),cursor.getDouble(1));
                intent1.putExtra(cursor.getColumnName(2),cursor.getString(2));
                intent1.putExtra(cursor.getColumnName(3),cursor.getInt(3));
                intent1.putExtra(cursor.getColumnName(4),cursor.getInt(4));
                intent1.putExtra(cursor.getColumnName(5),cursor.getInt(5));
                intent1.putExtra(cursor.getColumnName(7),cursor.getString(7));
                startActivity(intent1);
            }
        });
    }

}