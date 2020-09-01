package com.devgd.melonclone.domain.user.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.viewmodel.LoginViewModelMelonClone;

import org.watermelon.framework.global.model.view.activity.ChangeableFragmentActivity;
import org.watermelon.framework.global.model.view.fragment.BaseFragment;

public class LoginListFragment extends BaseFragment {

    // Activity
    ChangeableFragmentActivity activity;

    // Views
    LinearLayout loginKBtn;
    LinearLayout loginMBtn;

    // ViewModels
    LoginViewModelMelonClone loginViewModel;

    @Override
    protected View viewContainerInit(LayoutInflater inflater, ViewGroup container) {
        activity = (ChangeableFragmentActivity) getActivity();
        return inflater.inflate(R.layout.login_list_fragment, container, false);
    }

    @Override
    public void setParentViewModel(ViewModel... viewModels) {

    }

    @Override
    protected void layoutInit(View view) {

        loginKBtn = view.findViewById(R.id.kakao_id_login_btn);
        loginMBtn = view.findViewById(R.id.melon_id_login_btn);

    }

    @Override
    protected void viewInit() {
    }

    @Override
    protected void viewModelInit() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModelMelonClone.class);

    }

    @Override
    protected void listenerInit() {

        loginKBtn.setOnClickListener(v -> Toast.makeText(getContext(), "K login", Toast.LENGTH_SHORT).show());
        loginMBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "M login", Toast.LENGTH_SHORT).show();
            activity.onFragmentChange(1);
        });
    }
}
