package com.donga.deliveryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MenuAdapter extends SimpleCursorAdapter {
    private Cursor c;
    private int layout;
    private Context context;
    private String[] from;
    private int[] to;
    public MenuAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.c = c;
        this.layout = layout;
        this.context = context;
        this.from = from;
        this.to = to;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        c.moveToPosition(position);
        String name = c.getString(c.getColumnIndex(from[0]));
        Integer price = c.getInt(c.getColumnIndex(from[1]));
        TextView tv_name = v.findViewById(to[0]);
        TextView tv_price = v.findViewById(to[1]);
        tv_name.setText(name);
        tv_price.setText(price + "원");
        return v;
    }
}
