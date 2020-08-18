package com.devgd.melonclone.global.model.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.global.model.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {

    @Getter
    @Setter
    List<BaseFragment> list = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    public void addItem(BaseFragment fragment) {
        getList().add(fragment);
    }

    public abstract void addFragmentViewModel(ViewModel... viewModels);
}
