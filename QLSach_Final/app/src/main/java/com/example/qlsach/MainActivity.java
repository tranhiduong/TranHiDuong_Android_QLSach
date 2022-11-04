package com.example.qlsach;



import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    CheckBox checkboxRememberMe;
    Button btnLogin;
    final String COOKIE = "COOKIE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addEvents() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyLogin();

            }
        });
    }

    private void xulyLogin() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if (kiemTraDangNhap(username, password)) {
            Toast.makeText(MainActivity.this,
                    "Login successed",
                    Toast.LENGTH_SHORT
            ).show();
            if (checkboxRememberMe.isChecked()) {
                SharedPreferences preferences = getSharedPreferences(
                        COOKIE,
                        MODE_PRIVATE
                );
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString("USERNAME",username);
                editor.putString("PASSWORD",password);
                editor.apply();
            }
            Intent intent=new Intent(MainActivity.this,TraCuuSachActivity.class);
            startActivityForResult(intent,1000);
        } else {
            Toast.makeText(MainActivity.this,
                    "Login failed",
                    Toast.LENGTH_SHORT
            ).show();
            SharedPreferences preferences = getSharedPreferences(
                    COOKIE,
                    MODE_PRIVATE
            );
            SharedPreferences.Editor editor= preferences.edit();
            editor.remove("USERNAME");
            editor.remove("PASSWORD");
            editor.apply();
        }
    }

    private boolean kiemTraDangNhap(String username, String password) {
        if (username.equalsIgnoreCase("tranhiduong")) {
            if (password.equals("123456789")) {
                return true;
            }



        }
        return false;

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(
                COOKIE,
                MODE_PRIVATE
        );
        String username= preferences.getString("USERNAME","");
        String password= preferences.getString("PASSWORD","");
        txtUsername.setText(username);
        txtPassword.setText(password);
    }
}
//        dsLoai.add(new LoaiSach("L01","Tình cảm"));
//        dsLoai.add(new LoaiSach("L02","Kinh dị"));
//        dsLoai.add(new LoaiSach("L03","Trinh thám"));
//        dsSach.add(new Sach("1","Sách 1","A","Kim đồng",dsLoai.get(0)));
//        dsSach.add(new Sach("2","Sách 2","C","Tuổi trẻ",dsLoai.get(1)));
//        dsSach.add(new Sach("3","Sách 3","A","Kim đồng",dsLoai.get(2)));