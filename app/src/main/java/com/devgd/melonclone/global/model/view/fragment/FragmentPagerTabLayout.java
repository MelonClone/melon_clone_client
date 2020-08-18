package com.devgd.melonclone.global.model.view.fragment;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.devgd.melonclone.global.model.view.adapter.BaseFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public abstract class FragmentPagerTabLayout extends TabLayout {
    public FragmentPagerTabLayout(@NonNull Context context) {
        super(context);
    }

    public FragmentPagerTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentPagerTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPager(ViewPager pager, BaseFragmentPagerAdapter pagerAdapter) {
        // Set Pager
        if (pagerAdapter.getCount() >= getSelectedTabPosition() + 1) {
            pager.setCurrentItem(getSelectedTabPosition());
        }
    }


}
