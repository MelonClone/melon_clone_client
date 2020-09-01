package com.devgd.melonclone.domain.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.global.model.viewmodel.MelonCloneBaseViewModel;

import org.watermelon.framework.global.model.handler.TabMenu;
import org.watermelon.framework.global.model.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchTabViewModelMelonClone extends MelonCloneBaseViewModel implements ListViewModel<TabMenu> {
    private MutableLiveData<List<TabMenu>> mTabMenuList;

    @Override
    protected void init() {

    }

    @Override
    public LiveData<List<TabMenu>> getList() {
        if (mTabMenuList == null) {
            mTabMenuList = new MutableLiveData<>();
            loadTabMenu();
        }
        return mTabMenuList;
    }

    public void loadTabMenu() {
        List<TabMenu> menus = new ArrayList<>();
        menus.add(TabMenu.builder().tabName("뮤직").build());
        menus.add(TabMenu.builder().tabName("For U").build());
        menus.add(TabMenu.builder().tabName("MY").build());
        menus.add(TabMenu.builder().tabName("피드").tabItemCount(20).build());
        menus.add(TabMenu.builder().tabName("TV").build());
        menus.add(TabMenu.builder().tabName("DJ").build());
        menus.add(TabMenu.builder().tabName("공연").build());
        mTabMenuList.postValue(menus);
    }
}
