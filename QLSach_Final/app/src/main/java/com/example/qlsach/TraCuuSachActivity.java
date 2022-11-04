package com.example.qlsach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import Models.DuLieu;
import Models.Sach;
import Models.LoaiSach;
import util.DBConfigUtil;


public class TraCuuSachActivity extends AppCompatActivity {



    SachAdapter adapter;
    ListView lvSach;
    final String DB_NAME= "dbSach2.sqlite";
    FloatingActionButton fsbThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu_sach);
        DBConfigUtil.copyDatabaseFromAssets(TraCuuSachActivity.this);
        addControls();
        addEvents();
        addDocDsLoaiTuDb();
        addDocDsSachTuDb();
    }


    private void addDocDsSachTuDb() {
        DuLieu.dsSach.clear();
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        Cursor cursor = database.query(
                "Sach_TBL",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            String masach= cursor.getString(0);
            String tensach= cursor.getString(1);
            String tacgia= cursor.getString(2);
            String nhaxuatban= cursor.getString(3);
            String maloai=cursor.getString(4);
            byte[] hinhanh=cursor.getBlob(5);
            //
            Sach sach = new Sach();
            //
            sach.setMasach(masach);
            sach.setTensach(tensach);
            sach.setTacgia(tacgia);
            sach.setNhaxuatban(nhaxuatban);
            sach.setHinhanh(hinhanh);
            for(LoaiSach l:DuLieu.dsLoai){
                if(l.getMaloai().equals(maloai))
                    sach.setLoaisach(l);
            }
            DuLieu.dsSach.add(sach);
        }
        cursor.close();
        database.close();
    }

    private void addDocDsLoaiTuDb(){
        DuLieu.dsLoai.clear();
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        Cursor cursor = database.query(
                "LoaiSach",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String maloai = cursor.getString(0);
            String tenloai=cursor.getString(1);
            LoaiSach l=new LoaiSach();
            l.setMaloai(maloai);
            l.setTenloai(tenloai);
            DuLieu.dsLoai.add(l);
        }
        cursor.close();
        database.close();
    }





    private void addControls() {
        adapter= new SachAdapter(
                TraCuuSachActivity.this,
               R.layout.layout_item,
                DuLieu.dsSach
        );
        lvSach=findViewById(R.id.lvSach);
        lvSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        registerForContextMenu(lvSach);
        fsbThem=findViewById(R.id.fabThem);


    }
    private void addEvents() {
        fsbThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TraCuuSachActivity.this, SachActivity.class);

                startActivityForResult(intent,2);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() ==  R.id.lvSach){
            getMenuInflater().inflate(R.menu.mnu_sach, menu);
        }

    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index= info.position;
        switch (item.getItemId()){
            case R.id.mnuSua:
                //Gọi activity SanPham
                Intent intent=new Intent(TraCuuSachActivity.this, SachActivity.class);
                //Gửi giá trị vào
                intent.putExtra("vitri",index);
                startActivityForResult(intent,1);
                break;
            case R.id.mnuXoa:
                SQLiteDatabase database = openOrCreateDatabase(
                        DB_NAME,
                        MODE_PRIVATE,
                        null
                );
                database.delete("Sach_TBL","ma=?",new String[]{DuLieu.dsSach.get(index).getMasach()});
                addDocDsSachTuDb();
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addDocDsSachTuDb();
        adapter.notifyDataSetChanged();
        Toast.makeText(TraCuuSachActivity.this,"Hiển thị", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.mnuSach:

               break;
           case R.id.mnuLoaiSach:
               Intent intent = new Intent(TraCuuSachActivity.this,TraCuuPhanLoaiActivity.class);
               startActivity(intent);
               break;
           case R.id.mnuThongTin:
               Intent intent1 = new Intent( TraCuuSachActivity.this, LienHeActivity.class);
               startActivity(intent1);
               break;
           case R.id.mnuThoat:
               System.exit(0);
               break;
       }
        return super.onOptionsItemSelected(item);
    }




}