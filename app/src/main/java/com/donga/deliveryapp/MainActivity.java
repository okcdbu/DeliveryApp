package com.donga.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText address;
Button han,jung,il,yang,bun,chi;
Intent intent;
class BtnListener implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.korean:
                intent.putExtra("category","Korean");
                startActivity(intent);
                break;
            case R.id.chinese:
                intent.putExtra("category","Chinese");
                startActivity(intent);
                break;
            case R.id.japanese:
                intent.putExtra("category","Japanese");
                startActivity(intent);
                break;
            case R.id.western:
                intent.putExtra("category","western");
                startActivity(intent);
                break;
            case R.id.snack:
                intent.putExtra("category","snack");
                startActivity(intent);
                break;
            case R.id.chicken:
                intent.putExtra("category","chicken");
                startActivity(intent);
                break;
        }
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(getApplicationContext(),CategoryActivity.class);
        address = (EditText)findViewById(R.id.address);
        han = (Button)findViewById(R.id.korean);
        jung = (Button)findViewById(R.id.chinese);
        il = (Button)findViewById(R.id.japanese);
        yang = (Button)findViewById(R.id.western);
        bun = (Button)findViewById(R.id.snack);
        chi = (Button)findViewById(R.id.chicken);
        BtnListener mbtnlistener = new BtnListener();
        han.setOnClickListener(mbtnlistener);
        jung.setOnClickListener(mbtnlistener);
        il.setOnClickListener(mbtnlistener);
        yang.setOnClickListener(mbtnlistener);
        bun.setOnClickListener(mbtnlistener);
        chi.setOnClickListener(mbtnlistener);

    }
}