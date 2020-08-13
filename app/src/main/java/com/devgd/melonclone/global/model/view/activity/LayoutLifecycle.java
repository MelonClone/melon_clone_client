package com.devgd.melonclone.global.model.view.activity;

import android.content.Context;
import android.view.ViewGroup;

import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;

public interface LayoutLifecycle {
    void layoutInit(ViewGroup parentViewGroup);
    void viewInit(ViewGroup viewGroup);
    void viewModelInit(BaseActivity activity, BaseViewModel viewModel);
    void listenerInit();
}
