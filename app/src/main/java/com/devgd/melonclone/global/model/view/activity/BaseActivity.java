package com.devgd.melonclone.global.model.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.global.model.view.states.ViewState;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInit();
        toolbarInit();
        viewInit();
        viewModelInit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listenerInit();
    }

    abstract protected void layoutInit();
    protected void toolbarInit() {

    }
    abstract protected void viewInit();
    abstract protected void viewModelInit();
    abstract protected void listenerInit();

    // -- View methods
    protected void getCleanActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    // -- Functional methods

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

    protected ViewGroup getRootView() {
        return (ViewGroup) getWindow().getDecorView();
    }
}
