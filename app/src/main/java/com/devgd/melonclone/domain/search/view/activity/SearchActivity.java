package com.devgd.melonclone.domain.search.view.activity;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.domain.search.view.adapter.AdsPagerAdapter;
import com.devgd.melonclone.domain.search.view.adapter.NewestMusicAdapter;
import com.devgd.melonclone.domain.search.view.adapter.RankingPagerAdapter;
import com.devgd.melonclone.domain.search.viewmodel.AdsViewModel;
import com.devgd.melonclone.domain.search.viewmodel.NewestMusicViewModel;
import com.devgd.melonclone.domain.search.viewmodel.RankingViewModel;
import com.devgd.melonclone.domain.search.viewmodel.SearchTabViewModel;
import com.devgd.melonclone.global.model.handler.TabMenu;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {

    // Views
    TabLayout searchMenuTab;
    AdsPagerAdapter adsPagerAdapter;
    UltraViewPager adsViewPager;
    RecyclerView newestMusicView;
    NewestMusicAdapter newestMusicAdapter;
    RankingPagerAdapter rankingPagerAdapter;
    UltraViewPager rankingViewPager;

    // ViewModels
    SearchTabViewModel searchViewModel;
    PlayerViewModel playerViewModel;
    AdsViewModel adsViewModel;
    NewestMusicViewModel newestMusicViewModel;
    RankingViewModel rankingViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.search_main_layout);
        searchMenuTab = findViewById(R.id.search_tab);

        adsViewPager = findViewById(R.id.ads_view_pager);
        adsViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        newestMusicView = findViewById(R.id.newest_music_list);

        rankingViewPager = findViewById(R.id.ranking_view_pager);
        rankingViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
    }

    @Override
    protected void toolbarInit() {
        Toolbar searchAppbar = findViewById(R.id.search_appbar);
        setSupportActionBar(searchAppbar);
        getCleanActionBar();
    }


    @Override
    protected void viewInit() {
        searchMenuTab.setTabRippleColor(null);

        adsPagerAdapter = new AdsPagerAdapter(this, R.layout.ads_pager);
        adsViewPager.setAdapter(adsPagerAdapter);
        adsViewPager.initIndicator();
        adsViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getColor(R.color.colorPrimary))
                .setNormalColor(getColor(R.color.colorLight))
                .setIndicatorPadding(40)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        adsViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        adsViewPager.getIndicator().build();
        adsViewPager.setMultiScreen(1f);
        adsViewPager.setAutoScroll(5000);

        newestMusicAdapter = new NewestMusicAdapter(
                this,
                new NewestMusicAdapter.NewestMusicEventCallback() {
                    @Override
                    public void onClick(TextureView view, Music music) {
                        newestMusicViewModel.musicPlay(view, music);
                    }

                    @Override
                    public void onStop() {
                        newestMusicViewModel.musicStop();
                    }
                },
                getLayoutInflater(),
                new ArrayList<>());
        newestMusicView.setAdapter(newestMusicAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        newestMusicView.setLayoutManager(layoutManager);

        rankingPagerAdapter = new RankingPagerAdapter(this, R.layout.ranking_pager);
        rankingViewPager.setAdapter(rankingPagerAdapter);
        rankingViewPager.initIndicator();
        rankingViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getColor(R.color.colorPrimary))
                .setNormalColor(getColor(R.color.colorLight))
                .setIndicatorPadding(40)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        rankingViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        rankingViewPager.getIndicator().build();
        rankingViewPager.setMultiScreen(0.88f);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        searchViewModel = new ViewModelProvider(this).get(SearchTabViewModel.class);
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        newestMusicViewModel = new ViewModelProvider(this).get(NewestMusicViewModel.class);
        rankingViewModel = new ViewModelProvider(this).get(RankingViewModel.class);

        // Check User
        playerViewModel.getViewState().observe(this, getStateObserver(this));
        playerViewModel.checkLogin();

        searchViewModel.getList().observe(this, tabs -> {
            for (int i=0; i<tabs.size(); i++) {
                setupCustomTab(tabs.get(i));
            }
        });

        adsViewModel.getList().observe(this, list -> {
            adsPagerAdapter.setList(list);
            adsViewPager.refresh();
        });

        newestMusicViewModel.getList().observe(this, list -> {
            newestMusicAdapter.setList(list);
            newestMusicAdapter.notifyDataSetChanged();
        });

        rankingViewModel.getList().observe(this, list -> {
            rankingPagerAdapter.setList(list);
            rankingViewPager.refresh();
        });
    }
    @Override
    protected void listenerInit() {
        searchMenuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                TextView tabNameView = tabView.findViewById(R.id.tab_name);
                tabNameView.setTextColor(getColor(R.color.colorPrimary));
                ImageView selectedIconView = tabView.findViewById(R.id.tab_selected_icon);
                selectedIconView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                View tabView = tab.getCustomView();
                TextView tabNameView = tabView.findViewById(R.id.tab_name);
                tabNameView.setTextColor(getColor(R.color.colorDarkPoint));
                ImageView selectedIconView = tabView.findViewById(R.id.tab_selected_icon);
                selectedIconView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupCustomTab(TabMenu tabMenu) {
        View tabView = getLayoutInflater().inflate(R.layout.search_menu_tab, null);
        TextView tabNameView = tabView.findViewById(R.id.tab_name);
        TextView tabCountView = tabView.findViewById(R.id.tab_count);
        ImageView selectedIconView = tabView.findViewById(R.id.tab_selected_icon);
        tabNameView.setText(tabMenu.getTabName());
        if (tabMenu.tabItemCount > 0) {
            tabCountView.setText(String.valueOf(tabMenu.getTabItemCount()));
            tabCountView.setVisibility(View.VISIBLE);
        } else {
            tabCountView.setVisibility(View.GONE);
        }

        if (tabMenu.getTabPointImage() != null) {
            selectedIconView.setImageDrawable(tabMenu.getTabPointImage());
        }

        if (tabMenu.isSelected()) {
            selectedIconView.setVisibility(View.VISIBLE);
        } else {
            selectedIconView.setVisibility(View.INVISIBLE);
        }
        searchMenuTab.addTab(searchMenuTab.newTab().setCustomView(tabView));
    }
}