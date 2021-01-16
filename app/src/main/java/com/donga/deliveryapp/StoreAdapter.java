package com.donga.deliveryapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class StoreAdapter extends SimpleCursorAdapter {
    private Cursor c;
    private int layout;
    private Context context;
    private String[] from;
    private int[] to;
    public StoreAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
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

        String Name = c.getString(c.getColumnIndex(from[0]));
        Double rate = c.getDouble(c.getColumnIndex(from[1]));
        String Tel = c.getString(c.getColumnIndex(from[2]));
        Integer minprice = c.getInt(c.getColumnIndex(from[3]));
        Integer Time = c.getInt(c.getColumnIndex(from[4]));
        Integer tip = c.getInt(c.getColumnIndex(from[5]));

        TextView tv_name = (TextView)v.findViewById(to[0]);
        TextView tv_rate = (TextView)v.findViewById(to[1]);
        TextView tv_Tel = (TextView)v.findViewById(to[2]);
        TextView tv_minprice = (TextView)v.findViewById(to[3]);
        TextView tv_Time = (TextView)v.findViewById(to[4]);
        TextView tv_tip = (TextView)v.findViewById(to[5]);
        tv_name.setText(Name);
        tv_rate.setText(rate.toString());
        tv_Tel.setText(Tel);
        tv_minprice.setText(minprice.toString() + "원");
        tv_Time.setText(Time.toString()+"분");
        tv_tip.setText(tip.toString() + "원");
        return (v);
    }
}
