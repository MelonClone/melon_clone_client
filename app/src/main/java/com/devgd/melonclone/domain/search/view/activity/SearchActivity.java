package com.devgd.melonclone.domain.search.view.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.domain.search.view.adapter.SearchPagerAdapter;
import com.devgd.melonclone.domain.search.view.fragment.MiniPlayerView;
import com.devgd.melonclone.domain.search.viewmodel.SearchTabViewModel;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.activity.TabToolbarView;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends BaseActivity {

    // Views
    TabToolbarView searchTabToolbarView;
    ViewPager searchViewPager;
    SearchPagerAdapter searchPagerAdapter;

    // LifecycleView
    MiniPlayerView miniPlayerView;

    // ViewModels
    SearchTabViewModel searchViewModel;
    PlayerViewModel playerViewModel;


    @Override
    protected void layoutInit() {
        setContentView(R.layout.search_main_layout);
        searchTabToolbarView = new SearchTabToolbarView();
        searchTabToolbarView.tabLayoutInit(findViewById(R.id.search_tab));

        miniPlayerView = new MiniPlayerView();
        miniPlayerView.layoutInit(getRootView());

        searchViewPager = findViewById(R.id.search_view_pager);
    }

    @Override
    protected void toolbarInit() {
        Toolbar searchAppbar = findViewById(R.id.search_appbar);
        setSupportActionBar(searchAppbar);
        getCleanActionBar();
    }


    @Override
    protected void viewInit() {
        searchTabToolbarView.optionInit();

        miniPlayerView.viewInit(getRootView());

        searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        searchViewPager.setAdapter(searchPagerAdapter);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        searchViewModel = new ViewModelProvider(this).get(SearchTabViewModel.class);
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        // Add Fragment ViewModel
        searchPagerAdapter.addFragmentViewModel(playerViewModel);

        // Check User
        playerViewModel.getViewState().observe(this, getStateObserver(this));
        playerViewModel.checkLogin();

        searchViewModel.getList().observe(this, tabs -> {
            for (int i=0; i<tabs.size(); i++) {
                searchTabToolbarView.setupCustomTab(getLayoutInflater(), tabs.get(i));
            }
        });

        playerViewModel.getPlayer().observe(this, player -> {

            player.getCurrentPlaytime();
        });

        miniPlayerView.viewModelInit(this, playerViewModel);
    }

    @Override
    protected void listenerInit() {
        // Set Pager Tab Listener
        searchTabToolbarView.addOnTabSelectedListener(this, tab -> {
            // Set Pager
            if (searchPagerAdapter.getCount() >= tab.getPosition() + 1) {
                searchViewPager.setCurrentItem(tab.getPosition());
            }
        });

        searchViewPager.addOnPageChangeListener(searchTabToolbarView.getListener());

        miniPlayerView.listenerInit();
    }
}