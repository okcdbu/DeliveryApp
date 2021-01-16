package com.donga.deliveryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BasketAdapter extends SimpleCursorAdapter {
    private Cursor c;
    private int layout;
    private Context context;
    private String[] from;
    private int[] to;
    SQLiteDatabase sqldb;
    View v;
    public BasketAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags,SQLiteDatabase sqldb) {
        super(context, layout, c, from, to, flags);
        this.c = c;
        this.layout = layout;
        this.context = context;
        this.from = from;
        this.to = to;
        this.sqldb = sqldb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        c.moveToPosition(position);
        TextView tv_name = v.findViewById(to[0]);
        TextView tv_price = v.findViewById(to[1]);
        final TextView tv_amount = v.findViewById(to[5]);
        tv_amount.setText(String.valueOf(c.getInt(c.getColumnIndex(from[4]))));
        tv_name.setText(c.getString(c.getColumnIndex(from[0])));
        tv_price.setText(String.valueOf(c.getInt(c.getColumnIndex(from[1]))));
        ImageView delete = (ImageView)v.findViewById(to[4]);
        delete.setTag(c.getInt(c.getColumnIndex(from[2])));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = (Integer)view.getTag();
                sqldb.execSQL("DELETE FROM Basket WHERE _id = '" + id + "'");
                View parent = (View)view.getParent();
                parent.setVisibility(View.GONE);

            }
        });

        return v;
    }
}
