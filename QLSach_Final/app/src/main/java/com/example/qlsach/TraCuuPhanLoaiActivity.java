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

import java.util.ArrayList;

import Models.DuLieu;
import Models.LoaiSach;
import Models.Sach;
import util.DBConfigUtil;

public class TraCuuPhanLoaiActivity extends AppCompatActivity {

    LoaiSachAdapter adapter;
    ListView lvLoaiSach;
    final String DB_NAME = "dbSach2.sqlite";
    FloatingActionButton fsbThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu_phan_loai);
        DBConfigUtil.copyDatabaseFromAssets(TraCuuPhanLoaiActivity.this);
        addControls();
        addEvents();
        addDocDsLoaiTuDb();
    }

    private void addDocDsLoaiTuDb() {
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
        while (cursor.moveToNext()){
            String maloai = cursor.getString(0);
            String tenloai=cursor.getString(1);
            LoaiSach l=new LoaiSach();
            l.setMaloai(maloai);
            l.setTenloai(tenloai);
            DuLieu.dsLoai.add(l);
        }
    }

    private void addEvents() {
        fsbThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TraCuuPhanLoaiActivity.this,PhanLoaiActivity.class);

                startActivityForResult(intent,2);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addDocDsLoaiTuDb();
        adapter.notifyDataSetChanged();
        Toast.makeText(TraCuuPhanLoaiActivity.this,"Hiển thị", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() ==  R.id.lvLoaiSach){
            getMenuInflater().inflate(R.menu.mnu_loaisach, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index= info.position;
        switch (item.getItemId()){
            case R.id.mnuSua:
                //Gọi activity SanPham
                Intent intent=new Intent(TraCuuPhanLoaiActivity.this,PhanLoaiActivity.class);
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
                LoaiSach l=DuLieu.dsLoai.get(index);
                if(kiemTraTonTaiLoaiTrongSach(DuLieu.dsSach,l)){
                    Toast.makeText(TraCuuPhanLoaiActivity.this,"Không thể xóa",Toast.LENGTH_SHORT).show();
                }
                else {
                    database.delete("LoaiSach","maloai=?",new String[]{l.getMaloai()});
                    addDocDsLoaiTuDb();
                    adapter.notifyDataSetChanged();
                }

                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void addControls() {
        adapter = new LoaiSachAdapter(
                TraCuuPhanLoaiActivity.this,
                R.layout.layout_item_loaisach,
                DuLieu.dsLoai
        );
        lvLoaiSach = findViewById(R.id.lvLoaiSach);
        lvLoaiSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        registerForContextMenu(lvLoaiSach);
        fsbThem = findViewById(R.id.fabThem);
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
                Intent intent = new Intent(TraCuuPhanLoaiActivity.this,TraCuuSachActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuLoaiSach:
                break;
            case R.id.mnuThongTin:
                Intent intent1= new Intent(TraCuuPhanLoaiActivity.this, LienHeActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThoat:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean kiemTraTonTaiLoaiTrongSach(ArrayList<Sach> dsSach,LoaiSach l){
        for(Sach s:dsSach){
            if((s.getLoaisach().getMaloai()).equals(l.getMaloai())){
                return true;
            }
        }
        return false;
    }

}