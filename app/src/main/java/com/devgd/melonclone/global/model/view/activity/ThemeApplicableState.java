package com.devgd.melonclone.global.model.view.activity;

import com.devgd.melonclone.global.consts.Constants;

public interface ThemeApplicableState<T> {
    void colorChange(Constants.Theme colorTheme);
    void setTheme(Constants.Theme colorTheme);
    Constants.Theme getTheme();
    void setApplicableState(T t);
    T getApplicableState();
}
