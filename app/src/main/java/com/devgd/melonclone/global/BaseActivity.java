package com.devgd.melonclone.global;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInit();
        viewModelInit();
        viewInit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listenerInit();
    }

    abstract protected void layoutInit();
    abstract protected void viewModelInit();
    abstract protected void viewInit();
    abstract protected void listenerInit();
}
