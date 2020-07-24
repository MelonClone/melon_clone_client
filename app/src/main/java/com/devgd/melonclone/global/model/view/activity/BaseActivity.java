package com.devgd.melonclone.global.model.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.global.model.view.states.ViewState;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelInit();
        layoutInit();
        viewInit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listenerInit();
    }

    abstract protected void viewModelInit();
    abstract protected void layoutInit();
    abstract protected void viewInit();
    abstract protected void listenerInit();

    protected void autoLogin(User user) {

    }

    protected Observer<ViewState> getStateObserver(Context context) {
        return (Observer<ViewState>) viewState -> {
            switch (viewState.getStateCode()) {
                case AUTO_LOGIN:
                    autoLogin((User) viewState.getArg());
                    break;
                case ACTIVITY_CHANGE:
                    Class nextClass = (Class) viewState.getMsg();
                    Intent intent = new Intent(context, nextClass);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setFlags(Intent.Clea)
                    startActivity(intent);
                    break;

            }
        };
    }
}
