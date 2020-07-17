package com.devgd.melonclone.domain.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devgd.melonclone.R;

public class LoginActivity extends AppCompatActivity {

    LinearLayout loginKBtn;
    LinearLayout loginMBtn;
    LinearLayout registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        loginKBtn = findViewById(R.id.kakao_id_login_btn);
        loginMBtn = findViewById(R.id.melon_id_login_btn);
        registerBtn = findViewById(R.id.melon_id_register_btn);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "K login", Toast.LENGTH_SHORT).show();
            }
        });

        loginMBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "M login", Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Regist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}