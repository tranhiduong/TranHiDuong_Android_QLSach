package com.example.qlsach;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Models.LoaiSach;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    Activity context;
    int resource;
    List<LoaiSach> objects;

    public LoaiSachAdapter(@NonNull Activity context, int resource, @NonNull List<LoaiSach> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource= resource;
        this.objects = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource,null);
        TextView txtMaLoai = item.findViewById(R.id.txtMaLoai);
        TextView txtTenLoai = item.findViewById(R.id.txtTenLoai);
        LoaiSach ls = this.objects.get(position);
        txtMaLoai.setText(ls.getMaloai());
        txtTenLoai.setText(ls.getTenloai());
        return item;
    }
}
