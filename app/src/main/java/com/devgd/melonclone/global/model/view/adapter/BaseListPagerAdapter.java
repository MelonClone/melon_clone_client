package com.devgd.melonclone.global.model.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseListPagerAdapter<T> extends PagerAdapter {

    @Getter
    @Setter
    List<T> list = new ArrayList<>();
    int layoutId;

    public abstract void setView(ViewGroup viewGroup, int position);

    public BaseListPagerAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parentLayout = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(layoutId, null);

        setView(parentLayout, position);

        container.addView(parentLayout);
        return parentLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
