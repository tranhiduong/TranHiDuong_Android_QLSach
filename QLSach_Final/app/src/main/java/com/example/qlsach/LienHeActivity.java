package com.example.qlsach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LienHeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Button btnGoi;
    TextView txtSDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        AddControls();
        AddEvent();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void AddControls(){
        txtSDT=findViewById(R.id.txtSDT);
        btnGoi=findViewById(R.id.btnGoi);
    }
    private void AddEvent(){
        btnGoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = txtSDT.getText().toString();
                Intent intent = new Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:" + phoneNum)
                );
                startActivity(intent);

            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng stu = new LatLng(10.738265040664425, 106.67829696931567);
        mMap.addMarker(new MarkerOptions()
                .position(stu)
                .title("STU")
                .snippet("Trường Đại học Công nghệ Sài Gòn")

        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stu, 18));
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
                Intent intent = new Intent(LienHeActivity.this,TraCuuSachActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuLoaiSach:
                Intent intent1 = new Intent(LienHeActivity.this,TraCuuPhanLoaiActivity.class);
                startActivity(intent1);
                break;
            case R.id.mnuThongTin:

                break;
            case R.id.mnuThoat:
                System.exit(0);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}