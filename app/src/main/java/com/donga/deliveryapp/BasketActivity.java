package com.donga.deliveryapp;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BasketActivity extends AppCompatActivity {
    myDBHelper MyHelper;
    SQLiteDatabase sqldb;
    BasketAdapter mAdapter;
    ListView listView;
    Button order;
    Cursor cursor;
    Intent intent;
    Integer Tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        order=findViewById(R.id.order);
        intent = getIntent();
        Tip = Integer.parseInt(intent.getStringExtra("tip"));
        MyHelper = new myDBHelper(this);
        sqldb = MyHelper.getWritableDatabase();
        listView = (ListView)findViewById(R.id.listview);
        cursor = sqldb.rawQuery("SELECT * FROM Basket",null);
        cursor.moveToFirst();
        String[] from = new String[]{"Name","Price","_id","storename","amount"};
        int[] to = new int[]{R.id.tv_name,R.id.tv_price,R.id.plus,R.id.minus,R.id.cancel,R.id.amount};
        mAdapter = new BasketAdapter(this,R.layout.basket_list,cursor,from,to,0,sqldb);
        listView.setAdapter(mAdapter);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyHelper = new myDBHelper(BasketActivity.this);
                sqldb = MyHelper.getWritableDatabase();
                Cursor c = sqldb.rawQuery("SELECT Price, amount FROM Basket",null);
                c.moveToFirst();
                int sum=0;
                if(c.getCount() == 0)
                    return;
                for(int i =0; i < c.getCount();i++){
                    sum += c.getInt(0) * c.getInt(1);
                    c.moveToNext();
                }
                sum += Tip;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BasketActivity.this);
                alertDialog.setTitle("주문서");
                alertDialog.setMessage("총 금액 "+sum+"원을 결제하시겠습니까?");
                alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(BasketActivity.this,"결제 완료!",Toast.LENGTH_LONG).show();
                        sqldb.execSQL("DELETE FROM Basket");
                        finish();
                    }
                });
                alertDialog.setNegativeButton("아니오", null);
                alertDialog.show();
            }
        });
    }

}