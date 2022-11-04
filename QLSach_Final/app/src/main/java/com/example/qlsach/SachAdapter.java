package com.example.qlsach;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Models.Sach;

public class SachAdapter extends ArrayAdapter<Sach> {
    Activity context;
    int resource;
    List<Sach> objects;


    public SachAdapter(@NonNull Activity context, int resource, @NonNull List<Sach> objects) {
        super(context,resource,objects);
        this.context= context;
        this.resource =resource;
        this.objects=objects;
    }
    public View getView(int position, View convertView, ViewGroup parent){
//        LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater= this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource,null);

        ImageView imgSach = item.findViewById(R.id.imgSach);
        TextView txtMaSach = item.findViewById(R.id.txtMasach);
        TextView txtTenSach = item.findViewById(R.id.txtTenSach);
        TextView txtLoaiSach = item.findViewById(R.id.txtTheLoai);

        Sach s = this.objects.get(position);
        if(s.getHinhanh()!=null){
            imgSach.setImageBitmap(blobToBitmap(s.getHinhanh()));
        }
        //imgSach.setImageBitmap(s.getHinhanh());
        txtMaSach.setText(s.getMasach());
        txtTenSach.setText(s.getTensach());
        if(s.getLoaisach()!=null)
            txtLoaiSach.setText(s.getLoaisach().getTenloai());

        return item;
    }
    public Bitmap blobToBitmap(byte[] t){
        if(t!=null)
            return BitmapFactory.decodeByteArray(t,0,t.length);
        else
            return null;
    }



}
