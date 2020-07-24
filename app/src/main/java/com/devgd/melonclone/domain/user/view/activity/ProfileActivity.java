package com.devgd.melonclone.domain.user.view.activity;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModel;
import com.devgd.melonclone.global.customview.RoundImageView;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.states.LoginState;

public class ProfileActivity extends BaseActivity {

    // Views
    TextView userNameTxtView;
    RoundImageView userProfileImgView;
    ImageButton settingBtn;
    ImageButton closeBtn;

    // ViewModels
    LoginViewModel loginViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.profile_layout);

        userNameTxtView = findViewById(R.id.user_nickname);
        userProfileImgView = findViewById(R.id.user_profile);
        settingBtn = findViewById(R.id.setting_btn);
        closeBtn = findViewById(R.id.close_btn);
    }

    @Override
    protected void viewModelInit() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoginState().observe(this, this::getUserState);
        loginViewModel.checkLogin();
    }

    @Override
    protected void viewInit() {

    }

    @Override
    protected void listenerInit() {
        settingBtn.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "setting", Toast.LENGTH_SHORT).show();
        });

        closeBtn.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "close", Toast.LENGTH_SHORT).show();
        });
    }

    private void getUserState(LoginState loginState) {
        if (loginState.isLogin()) {
            userNameTxtView.setText(loginState.getUser().getNickname());
        }
    }
}