package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomListAdaptor extends BaseAdapter {
    private List<ListItem> listData;
    private LayoutInflater layoutInflater;
    public CustomListAdaptor(Context aContext, List<ListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.row_list, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.uDescription = (TextView) v.findViewById(R.id.description);
            holder.uLat = (TextView) v.findViewById(R.id.lat);
            holder.uLng = (TextView) v.findViewById(R.id.lng);
            holder.uImage = (ImageView) v.findViewById(R.id.picture);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getName());
        holder.uDescription.setText(listData.get(position).getDescription());
        holder.uLat.setText(listData.get(position).getLat());
        holder.uLng.setText(listData.get(position).getLng());
        holder.uImage.setImageURI(Uri.fromFile(new File(listData.get(position).getFilePath())));

        return v;
    }
    static class ViewHolder {
        TextView uName;
        TextView uDescription;
        TextView uLat;
        TextView uLng;
        ImageView uImage;
    }
}
