package com.devgd.melonclone.domain.user.view.activity;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModelMelonClone;
import com.devgd.melonclone.global.model.view.activity.MelonCloneBaseActivity;

import org.watermelon.framework.global.customview.RoundImageView;
import org.watermelon.framework.global.model.view.states.LoginState;

public class ProfileActivity extends MelonCloneBaseActivity {

    // Views
    TextView userNameTxtView;
    RoundImageView userProfileImgView;
    ImageButton settingBtn;
    ImageButton closeBtn;

    // ViewModels
    LoginViewModelMelonClone loginViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.profile_layout);

        userNameTxtView = findViewById(R.id.user_nickname);
        userProfileImgView = findViewById(R.id.user_profile);
        settingBtn = findViewById(R.id.setting_btn);
        closeBtn = findViewById(R.id.close_btn);
    }

    @Override
    protected void viewInit() {

    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        loginViewModel = new ViewModelProvider(this).get(LoginViewModelMelonClone.class);

        // Check User
        loginViewModel.getLoginState().observe(this, this::getUserState);
        loginViewModel.checkLogin();
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

    private void getUserState(LoginState<User> loginState) {
        if (loginState.isLogin()) {
            userNameTxtView.setText(loginState.getUser().getNickname());
        }
    }
}