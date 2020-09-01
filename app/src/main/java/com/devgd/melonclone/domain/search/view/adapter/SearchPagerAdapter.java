package com.devgd.melonclone.domain.search.view.adapter;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.domain.search.view.fragment.SearchMainFragment;
import com.devgd.melonclone.domain.search.view.fragment.SearchVideoFragment;

import org.watermelon.framework.global.model.view.adapter.BaseFragmentPagerAdapter;
import org.watermelon.framework.global.model.view.fragment.BaseFragment;

public class SearchPagerAdapter extends BaseFragmentPagerAdapter {

    BaseFragment searchMainFragment;
    BaseFragment searchVideoFragment;

    public SearchPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        searchMainFragment = new SearchMainFragment();
        searchVideoFragment = new SearchVideoFragment();

        addItem(searchMainFragment);
        addItem(searchVideoFragment);
    }

    @Override
    public void addFragmentViewModel(ViewModel... viewModels) {
        // Add Fragment ViewModel
        searchMainFragment.setParentViewModel(viewModels);
        searchVideoFragment.setParentViewModel(viewModels);
    }
}
