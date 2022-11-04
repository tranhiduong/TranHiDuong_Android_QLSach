package com.example.qlsach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Models.DuLieu;
import Models.LoaiSach;
import util.DBConfigUtil;

public class PhanLoaiActivity extends AppCompatActivity {
    EditText txtMaloai, txtTenLoai;
    LoaiSach ls;
    Button btnLuu;
    final String DB_NAME = "dbSach2.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_loai);
        DBConfigUtil.copyDatabaseFromAssets(PhanLoaiActivity.this);
        addControls();
        GetIntentData();
        addEvents();

    }


    private void addControls() {
       txtMaloai= findViewById(R.id.txtMaLoai);
       txtTenLoai=findViewById(R.id.txtTenLoai);
       btnLuu= findViewById(R.id.btnLuu);

    }

    private void GetIntentData() {
        Intent intent = getIntent();
        if(intent.hasExtra("vitri")){
            int vitri=intent.getIntExtra("vitri",-1);
            ls= DuLieu.dsLoai.get(vitri);
            txtMaloai.setText(ls.getMaloai());
            txtTenLoai.setText(ls.getTenloai());
        }
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ls == null)
                    xuLyThem();
                else
                    xuLySua();
                finish();
            }
        });
    }

    private void xuLyThem() {
        String maloai = txtMaloai.getText().toString();
        String tenloai = txtTenLoai.getText().toString();
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        ContentValues cv= new ContentValues();
        cv.put("maloai",maloai);
        cv.put("tenloai",tenloai);
        database.insert("LoaiSach",null,cv);
        Toast.makeText(PhanLoaiActivity.this, "Thêm thành công" + maloai, Toast.LENGTH_SHORT).show();
    }

    private void xuLySua() {

        String tenloai = txtTenLoai.getText().toString();
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        ContentValues cv= new ContentValues();
        cv.put("tenloai",tenloai);
        database.update("LoaiSach",cv,"maloai=?",new String[]{ls.getMaloai()});
        Toast.makeText(PhanLoaiActivity.this, "Sửa thành công" + ls.getMaloai(), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(PhanLoaiActivity.this,TraCuuSachActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuLoaiSach:
                Intent intent1 = new Intent(PhanLoaiActivity.this,TraCuuPhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTin:
                Intent intent2 = new Intent(PhanLoaiActivity.this, LienHeActivity.class);
                startActivity(intent2);
                break;
            case R.id.mnuThoat:
                System.exit(0);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}