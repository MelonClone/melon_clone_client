package com.devgd.melonclone.domain.user.view.activity;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.devgd.melonclone.R;
import com.devgd.melonclone.global.BaseActivity;

public class LoginActivity extends BaseActivity {

    LinearLayout loginKBtn;
    LinearLayout loginMBtn;
    LinearLayout registerBtn;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.user_login);

        loginKBtn = findViewById(R.id.kakao_id_login_btn);
        loginMBtn = findViewById(R.id.melon_id_login_btn);
        registerBtn = findViewById(R.id.melon_id_register_btn);
    }

    @Override
    protected void viewModelInit() {

    }

    @Override
    protected void viewInit() {

    }

    @Override
    protected void listenerInit() {

        loginKBtn.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "K login", Toast.LENGTH_SHORT).show());

        loginMBtn.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "M login", Toast.LENGTH_SHORT).show());

        registerBtn.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "Regist", Toast.LENGTH_SHORT).show());
    }
}