package com.devgd.melonclone.domain.user.view.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.domain.AuthState;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModel;
import com.devgd.melonclone.domain.user.viewmodel.RegisterViewModel;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;

public class RegisterActivity extends BaseActivity {

    // Views
    EditText userEmail;
    EditText userNickname;
    EditText userPassword;
    EditText userPasswordRetype;
    TextView registerInfo;
    LinearLayout registerBtn;

    // ViewModels
    RegisterViewModel registerViewModel;
    LoginViewModel loginViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.user_regist_layout);

        userEmail = findViewById(R.id.user_email);
        userNickname = findViewById(R.id.user_nickname);
        userPassword = findViewById(R.id.user_password);
        userPasswordRetype = findViewById(R.id.user_password_retype);
        registerInfo = findViewById(R.id.register_info);
        registerBtn = findViewById(R.id.register_btn);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        registerViewModel.getViewState().observe(this, getStateObserver(this));
        registerViewModel.getRegisterInfo().observe(this, getErrorObserver());
        loginViewModel.getLoginInfo().observe(this, getErrorObserver());
    }

    @Override
    protected void viewInit() {

    }

    @Override
    protected void listenerInit() {
        userPasswordRetype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.checkRegistryInfo(userPassword.getText().toString(), userPasswordRetype.getText().toString());
            }
        });

        registerBtn.setOnClickListener(v -> registerViewModel.registerUser(
                        userEmail.getText().toString(),
                        userNickname.getText().toString(),
                        userPassword.getText().toString()));
    }

    @Override
    protected void autoLogin(User user) {
        loginViewModel.loginUser(user);
    }

    private Observer<AuthState> getErrorObserver() {
        return (authState) -> {
            switch (authState.getAuthErrorCode()) {
                case PASSWORD_NOT_MATCH:
                    registerInfo.setText(this.getResources().getString(R.string.password_not_match));
                    registerInfo.setVisibility(View.VISIBLE);
                    break;
                case USER_INPUT_WRONG:
                    registerInfo.setText(this.getResources().getString(R.string.input_verify_fail));
                    registerInfo.setVisibility(View.VISIBLE);
                    break;
                case UNEXPECTED_ERROR:
                    Toast.makeText(this, "문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    registerInfo.setVisibility(View.INVISIBLE);
                case NO_ERROR:
                default:
                    registerInfo.setVisibility(View.INVISIBLE);
            }
        };
    }
}
