package com.example.qlsach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



import java.io.ByteArrayOutputStream;

import Models.DuLieu;
import Models.LoaiSach;
import Models.Sach;
import util.DBConfigUtil;

public class SachActivity extends AppCompatActivity {

    EditText txtMaSach,txtTenSach,txtTacGia,txtNXB;
    Sach sach;
    Button btnLuu, btnThuVien,btnCamera;
    Spinner snLoai;
    ImageView imgSach;
    ArrayAdapter<LoaiSach> adapter;

    final String DB_NAME= "dbSach2.sqlite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        DBConfigUtil.copyDatabaseFromAssets(SachActivity.this);
        addControl();
        GetIntentData();
        addEvents();
    }

    private void GetIntentData(){
        Intent intent=getIntent();
        if(intent.hasExtra("vitri")){
            int vitri=intent.getIntExtra("vitri",-1);
            sach=DuLieu.dsSach.get(vitri);
            txtMaSach.setText(sach.getMasach());
            txtTenSach.setText(sach.getTensach());
            int vitriLoai=viTriLoai(sach.getLoaisach());
            snLoai.setSelection(vitriLoai);
            txtTacGia.setText(sach.getTacgia());
            txtNXB.setText(sach.getNhaxuatban());
            if(sach.getHinhanh()!=null)
                imgSach.setImageBitmap(blobToBitmap(sach.getHinhanh()));
        }
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sach==null)
                    xuLyThem();
                else
                    xuLySua();
            finish();
            }
        });
        btnThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,4);

            }
        });
    }

    private void xuLySua() {
        String tensach=txtTenSach.getText().toString();
        String tacgia=txtTacGia.getText().toString();
        String nxb=txtNXB.getText().toString();
        Bitmap hinhanh=getBitmapFormImageView(imgSach);
        LoaiSach l= (LoaiSach) snLoai.getSelectedItem();
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        ContentValues cv=new ContentValues();
        cv.put("ten",tensach);
        cv.put("tacgia",tacgia);
        cv.put("nhaxuatban",nxb);
        cv.put("maloai",l.getMaloai());
        cv.put("hinhanh",bitmapToBlob(hinhanh));
        database.update("Sach_TBL",cv,"ma=?",new String[]{sach.getMasach()});
        Toast.makeText(SachActivity.this,"Sửa thành công: "+sach.getMasach(), Toast.LENGTH_SHORT).show();
        //setResult(Activity.RESULT_OK);


    }



    private void xuLyThem() {
        String masach=txtMaSach.getText().toString();
        String tensach=txtTenSach.getText().toString();
        String tacgia=txtTacGia.getText().toString();
        String nxb=txtNXB.getText().toString();
        Bitmap hinhanh=getBitmapFormImageView(imgSach);
        LoaiSach l= (LoaiSach) snLoai.getSelectedItem();
        SQLiteDatabase database = openOrCreateDatabase(
                DB_NAME,
                MODE_PRIVATE,
                null
        );
        ContentValues cv=new ContentValues();
        cv.put("ma",masach);
        cv.put("ten",tensach);
        cv.put("tacgia",tacgia);
        cv.put("nhaxuatban",nxb);
        cv.put("maloai",l.getMaloai());
        cv.put("hinhanh",bitmapToBlob(hinhanh));
        database.insert("Sach_TBL",null,cv);
        Toast.makeText(SachActivity.this,"Thêm thành công: "+masach, Toast.LENGTH_SHORT).show();

    }


    private void addControl()
    {
        txtMaSach=findViewById(R.id.txtMasach);
        txtTenSach=findViewById(R.id.txtTenSach);
        snLoai=findViewById(R.id.snTheLoai);
        txtNXB=findViewById(R.id.txtNhaXuatBan);
        txtTacGia=findViewById(R.id.txtTacGia);
        btnLuu=findViewById(R.id.btnLuu);
        btnThuVien =findViewById(R.id.btnThuVien_Sach);
        btnCamera=findViewById(R.id.btnCamera_Sach);
        imgSach=findViewById(R.id.imgSach_Sach);
        adapter= new ArrayAdapter<>(SachActivity.this,R.layout.support_simple_spinner_dropdown_item,DuLieu.dsLoai);
        snLoai.setAdapter(adapter);
    }

    private int viTriLoai(LoaiSach l){
        for(int i = 0; i< DuLieu.dsLoai.size(); i++) {
            LoaiSach t=DuLieu.dsLoai.get(i);
            if (DuLieu.dsLoai.get(i).getMaloai().equals(l.getMaloai())) {
                return i;
            }
        }
        return -1;
    }

    private byte[] bitmapToBlob(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private Bitmap getBitmapFormImageView(ImageView img){
        img.setDrawingCacheEnabled(true);
        img.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        img.layout(0, 0, img.getMeasuredWidth(), img.getMeasuredHeight());
        img.buildDrawingCache(true);
        Bitmap b=Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return b;
    }
    public Bitmap blobToBitmap(byte[] t){
        if(t!=null)
            return BitmapFactory.decodeByteArray(t,0,t.length);
        else
            return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 3:
                Uri img= data.getData();
                imgSach.setImageURI(img);
                break;
            case 4:
                Bitmap imgCamera=(Bitmap) data.getExtras().get("data");
                imgSach.setImageBitmap(imgCamera);
                break;
        }
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
                Intent intent = new Intent(SachActivity.this,TraCuuSachActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuLoaiSach:
                Intent intent1 = new Intent(SachActivity.this,TraCuuPhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTin:
                Intent intent2 = new Intent(SachActivity.this, LienHeActivity.class);
                startActivity(intent2);
                break;
            case R.id.mnuThoat:
                System.exit(0);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}