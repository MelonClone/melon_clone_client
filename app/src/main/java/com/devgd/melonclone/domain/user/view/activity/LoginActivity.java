package com.devgd.melonclone.domain.user.view.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.view.fragment.LocalLoginFragment;
import com.devgd.melonclone.domain.user.view.fragment.LoginListFragment;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModel;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.activity.ChangeableFragmentActivity;
import com.devgd.melonclone.global.model.view.states.LoginState;

public class LoginActivity extends BaseActivity implements ChangeableFragmentActivity {

    // Views
    LinearLayout registerBtn;
    LoginListFragment loginListFragment;
    LocalLoginFragment localLoginFragment;

    // ViewModels
    LoginViewModel loginViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.user_login_layout);

        loginListFragment = new LoginListFragment();
        localLoginFragment = new LocalLoginFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.login_frg_container, loginListFragment);
        fragmentTransaction.commit();

        registerBtn = findViewById(R.id.melon_id_register_btn);
    }

    @Override
    protected void viewInit() {

    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Check User
        loginViewModel.getViewState().observe(this, getStateObserver(this));
        loginViewModel.getLoginState().observe(this, loginState -> {
            if (loginState.isLogin())
                loginViewModel.loggedIn();
        });
        loginViewModel.checkLogin();
    }

    @Override
    protected void listenerInit() {
        registerBtn.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Regist", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onFragmentChange(int index) {
        if (index == 0)
            getSupportFragmentManager().beginTransaction().replace(R.id.login_frg_container, loginListFragment).commit();
        else if (index == 1)
            getSupportFragmentManager().beginTransaction().replace(R.id.login_frg_container, localLoginFragment).commit();

    }
}