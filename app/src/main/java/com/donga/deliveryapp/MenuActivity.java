package com.donga.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    Intent getter;
    TextView tv_price,tv_name,tv_minprice,tv_totalprice,tv_amount;
    String totalprice,minprice,price;
    Button plus,minus,apply;
    public int amount = 1;
    myDBHelper MyHelper;
    SQLiteDatabase sqldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getter = getIntent();
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_price = (TextView)findViewById(R.id.tv_price);
        tv_minprice = (TextView)findViewById(R.id.tv_minprice);
        tv_totalprice = (TextView)findViewById(R.id.total);
        tv_amount = (TextView)findViewById(R.id.amount);
        plus = (Button)findViewById(R.id.plus);
        minus = (Button)findViewById(R.id.minus);
        apply = (Button)findViewById(R.id.apply);
        apply.setText(amount + "개 담기");
        price = String.valueOf(getter.getIntExtra("Price",0));
        tv_price.setText(price);
        tv_amount.setText(String.valueOf(amount));
        tv_name.setText(getter.getStringExtra("Name"));
        minprice = String.valueOf(getter.getIntExtra("minprice",0));
        tv_minprice.setText(minprice);
        totalprice =String.valueOf(amount * Integer.parseInt(price));
        tv_totalprice.setText(totalprice);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount <= 20){
                    amount++;
                    tv_amount.setText(String.valueOf(amount));
                    totalprice =String.valueOf(amount * Integer.parseInt(price));
                    tv_totalprice.setText(totalprice);
                    apply.setText(amount + "개 담기");
                }

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount >0){
                    amount--;
                    tv_amount.setText(String.valueOf(amount));
                    totalprice =String.valueOf(amount * Integer.parseInt(price));
                    tv_totalprice.setText(totalprice);
                    apply.setText(amount + "개 담기");
                }

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(totalprice) >= Integer.parseInt(minprice)){
                    MyHelper = new myDBHelper(getApplicationContext());
                    sqldb = MyHelper.getWritableDatabase();
                    sqldb.execSQL("INSERT INTO Basket VALUES ('" +getter.getStringExtra("Name")+ "','" +price+"',null,null, '"+amount+"')");
                    sqldb.close();
                    Toast.makeText(getApplicationContext(),"장바구니에 담겼습니다.",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"최소주문 금액 이상 주문해야 합니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}