package com.devgd.melonclone.domain.user.view.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.viewmodel.UserViewModel;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;

public class LoginActivity extends BaseActivity {

    // Views
    LinearLayout loginKBtn;
    LinearLayout loginMBtn;
    LinearLayout registerBtn;

    // ViewModels
    UserViewModel userViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.user_login);

        loginKBtn = findViewById(R.id.kakao_id_login_btn);
        loginMBtn = findViewById(R.id.melon_id_login_btn);
        registerBtn = findViewById(R.id.melon_id_register_btn);
    }

    @Override
    protected void viewModelInit() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected void viewInit() {

    }

    @Override
    protected void listenerInit() {

        loginKBtn.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "K login", Toast.LENGTH_SHORT).show());

        loginMBtn.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "M login", Toast.LENGTH_SHORT).show());

        registerBtn.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Regist", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}