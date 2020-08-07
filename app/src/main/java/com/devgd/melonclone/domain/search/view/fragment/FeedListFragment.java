package com.devgd.melonclone.domain.search.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModel;
import com.devgd.melonclone.global.model.view.fragment.BaseFragment;

public class FeedListFragment extends BaseFragment {

    // Views
    EditText userEmail;

    // ViewModels
    LoginViewModel loginViewModel;

    @Override
    protected View viewContainerInit(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.feed_container_layout, container, false);
    }

    @Override
    protected void layoutInit(View view) {
        userEmail = view.findViewById(R.id.user_email);

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

    }
}
