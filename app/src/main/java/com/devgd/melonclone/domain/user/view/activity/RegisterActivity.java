package com.devgd.melonclone.domain.user.view.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.user.viewmodel.UserViewModel;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;

import static com.devgd.melonclone.global.consts.ErrorCode.PASSWORD_NOT_MATCH;
import static com.devgd.melonclone.global.consts.ErrorCode.USER_INPUT_WRONG;
import static com.devgd.melonclone.global.consts.StateCode.ACTIVITY_CHANGE;

public class RegisterActivity extends BaseActivity {

    // Views
    EditText userEmail;
    EditText userNickname;
    EditText userPassword;
    EditText userPasswordRetype;
    TextView registerInfo;
    LinearLayout registerBtn;

    // ViewModels
    UserViewModel userViewModel;

    @Override
    protected void viewModelInit() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getViewState().observe(this, (viewState) -> {
            switch (viewState.getStateCode()) {
                case ACTIVITY_CHANGE:
                    Class nextClass = (Class) viewState.getMsg();
                    Intent intent = new Intent(this, nextClass);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setFlags(Intent.Clea)
                    startActivity(intent);
                    break;

            }
        });

        userViewModel.getRegisterInfo().observe(this, (errorCode) -> {
            switch (errorCode) {
                case PASSWORD_NOT_MATCH:
                    registerInfo.setText(this.getResources().getString(R.string.password_not_match));
                    registerInfo.setVisibility(View.VISIBLE);
                    break;
                case USER_INPUT_WRONG:
                    registerInfo.setText(this.getResources().getString(R.string.input_verify_fail));
                    registerInfo.setVisibility(View.VISIBLE);
                    break;
                default:
                    registerInfo.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void layoutInit() {
        setContentView(R.layout.user_regist);

        userEmail = findViewById(R.id.user_email);
        userNickname = findViewById(R.id.user_nickname);
        userPassword = findViewById(R.id.user_password);
        userPasswordRetype = findViewById(R.id.user_password_retype);
        registerInfo = findViewById(R.id.register_info);
        registerBtn = findViewById(R.id.register_btn);
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
                userViewModel.checkRegistryInfo(userPassword.getText().toString(), userPasswordRetype.getText().toString());
            }
        });

        registerBtn.setOnClickListener(v -> userViewModel.registerUser(
                        userEmail.getText().toString(),
                        userNickname.getText().toString(),
                        userPassword.getText().toString()));
    }
}
