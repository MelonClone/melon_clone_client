package com.devgd.melonclone.global.model.view.activity;

import android.view.ViewGroup;

import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;

public interface LifecycleView {
    void layoutInit(ViewGroup parentViewGroup);
    void viewInit(ViewGroup viewGroup);
    void viewModelInit(BaseViewModel... viewModels);
    void listenerInit();
    void colorChange(Constants.Theme colorTheme);
}
