package com.donga.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StoreActivity extends AppCompatActivity {
    Intent getter,intent1;
    FloatingActionButton fab;
    String name, rate,Tel,Minprice,Time,tip,Category;
    TextView tv_name,tv_rate,tv_Minprice,tv_Time,tv_Tip;
    RatingBar rtbar;
    LinearLayout tel;
    ListView listView;
    myDBHelper MyHelper;
    SQLiteDatabase sqldb;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getter = getIntent();
        name = getter.getStringExtra("Name");
        rate = String.valueOf(getter.getDoubleExtra("Rate",0));
        Tel = getter.getStringExtra("Tel");
        Minprice = String.valueOf(getter.getIntExtra("Minprice",0));
        Time = String.valueOf(getter.getIntExtra("Time",0));
        tip = String.valueOf(getter.getIntExtra("Tip",0));
        Category = getter.getStringExtra("Category");
        fab = (FloatingActionButton)findViewById(R.id.fab);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_rate = (TextView)findViewById(R.id.tv_rate);
        tv_Minprice = (TextView)findViewById(R.id.tv_minprice);
        tv_Time = (TextView)findViewById(R.id.tv_time);
        tv_Tip = (TextView)findViewById(R.id.tv_tip);
        rtbar = (RatingBar)findViewById(R.id.ratingbar);
        tel = (LinearLayout)findViewById(R.id.telbtn);
        listView = (ListView)findViewById(R.id.listview);
        MyHelper = new myDBHelper(this);
        sqldb = MyHelper.getWritableDatabase();
        cursor = sqldb.rawQuery("SELECT * FROM menu WHERE Category = '"+ Category + "'",null);
        cursor.moveToFirst();
        String[] from = new String[]{"Name","Price","_id","category"};
        int[] to = new int[]{R.id.tv_name,R.id.tv_price};
        MenuAdapter mAdapter = new MenuAdapter(this,R.layout.menu_list,cursor,from,to,0);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                intent1 = new Intent(getApplicationContext(),MenuActivity.class);
                intent1.putExtra(cursor.getColumnName(0),cursor.getString(0));
                intent1.putExtra(cursor.getColumnName(1),cursor.getInt(1));
                intent1.putExtra(cursor.getColumnName(3),cursor.getString(3));
                intent1.putExtra("minprice",Integer.parseInt(Minprice));
                startActivity(intent1);
            }
        });
        tv_name.setText(name);
        tv_rate.setText(rate);
        tv_Minprice.setText(Minprice);
        tv_Time.setText(Time);
        tv_Tip.setText(tip);
        rtbar.setRating(Float.parseFloat(rate));
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(Tel));
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Your phone does not support CALL Option",Toast.LENGTH_LONG).show();
                }

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BasketActivity.class);
                intent.putExtra("tip",tip);
                startActivity(intent);
            }
        });
    }
}