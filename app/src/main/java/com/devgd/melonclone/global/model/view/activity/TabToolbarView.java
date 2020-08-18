package com.devgd.melonclone.global.model.view.activity;

import android.content.Context;
import android.view.LayoutInflater;

import com.devgd.melonclone.global.model.handler.TabMenu;
import com.google.android.material.tabs.TabLayout;

public interface TabToolbarView {
    void tabLayoutInit(TabLayout tabLayout);
    void setupCustomTab(LayoutInflater inflater, TabMenu tabMenu);
    void optionInit();
    void addOnTabSelectedListener(Context context, TabSelectedAction action);

    interface TabSelectedAction {
        void onSelected(TabLayout.Tab tab);
    }

    TabLayout getLayout();
    TabLayout.TabLayoutOnPageChangeListener getListener();
}
