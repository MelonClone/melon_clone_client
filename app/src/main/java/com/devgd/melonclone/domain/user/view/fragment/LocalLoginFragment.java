package com.devgd.melonclone.domain.user.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.domain.AuthState;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModel;
import com.devgd.melonclone.global.model.view.fragment.BaseFragment;

public class LocalLoginFragment extends BaseFragment {

    // Views
    EditText userEmail;
    EditText userPassword;
    TextView loginInfo;
    LinearLayout loginMBtn;

    // ViewModels
    LoginViewModel loginViewModel;

    @Override
    protected View viewContainerInit(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.local_login_fragment, container, false);
    }

    @Override
    protected void layoutInit(View view) {
        userEmail = view.findViewById(R.id.user_email);
        userPassword = view.findViewById(R.id.user_password);
        loginInfo = view.findViewById(R.id.login_info);
        loginMBtn = view.findViewById(R.id.melon_id_login_btn);

    }

    @Override
    protected void viewModelInit() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoginInfo().observe(this, getLoginObserver());

    }

    @Override
    protected void viewInit() {
    }

    @Override
    protected void listenerInit() {
        loginMBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "M login", Toast.LENGTH_SHORT).show();
            loginViewModel.loginUser(userEmail.getText().toString(), userPassword.getText().toString());
        });

    }

    private Observer<AuthState> getLoginObserver() {
        return (Observer<AuthState>) authState -> {
            switch (authState.getAuthErrorCode()) {
                case USER_INPUT_WRONG:
                    loginInfo.setText(this.getResources().getString(R.string.input_verify_fail));
                    loginInfo.setVisibility(View.VISIBLE);
                    break;
                case UNEXPECTED_ERROR:
                    Toast.makeText(getContext(), this.getResources().getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
                    loginInfo.setVisibility(View.INVISIBLE);
                case NO_ERROR:
                default:
                    loginInfo.setVisibility(View.INVISIBLE);
            }
        };
    }
}
