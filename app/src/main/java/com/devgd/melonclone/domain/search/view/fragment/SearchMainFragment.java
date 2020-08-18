package com.devgd.melonclone.domain.search.view.fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;
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
import com.devgd.melonclone.global.model.view.fragment.BaseFragment;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

public class SearchMainFragment extends BaseFragment {

    // Activity

    // Views
    AdsPagerAdapter adsPagerAdapter;
    UltraViewPager adsViewPager;
    RecyclerView newestMusicView;
    NewestMusicAdapter newestMusicAdapter;
    RankingPagerAdapter rankingPagerAdapter;
    UltraViewPager rankingViewPager;

    // ViewModels
    PlayerViewModel playerViewModel;
    AdsViewModel adsViewModel;
    NewestMusicViewModel newestMusicViewModel;
    RankingViewModel rankingViewModel;

    @Override
    protected View viewContainerInit(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.home_container_layout, container, false);
    }

    @Override
    public void setParentViewModel(ViewModel... viewModels) {
        for (ViewModel viewModel : viewModels) {
            if (viewModel instanceof PlayerViewModel) {
                playerViewModel = (PlayerViewModel) viewModel;
            }
        }
    }

    @Override
    protected void layoutInit(View view) {

        adsViewPager = view.findViewById(R.id.ads_view_pager);
        adsViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        newestMusicView = view.findViewById(R.id.newest_music_list);

        rankingViewPager = view.findViewById(R.id.ranking_view_pager);
        rankingViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);


    }


    @Override
    protected void viewInit() {
        adsPagerAdapter = new AdsPagerAdapter(getContext(), R.layout.ads_pager);
        adsViewPager.setAdapter(adsPagerAdapter);
        adsViewPager.initIndicator();
        adsViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getContext().getColor(R.color.colorPrimary))
                .setNormalColor(getContext().getColor(R.color.colorLight))
                .setIndicatorPadding(40)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        adsViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        adsViewPager.getIndicator().build();
        adsViewPager.setMultiScreen(1f);
        adsViewPager.setAutoScroll(5000);

        newestMusicAdapter = new NewestMusicAdapter(
                getContext(),
                new NewestMusicAdapter.NewestMusicEventCallback() {
                    @Override
                    public void onClick(TextureView view, Music music) {
                        newestMusicViewModel.musicPlay(view, music);
                    }

                    @Override
                    public void onStop() {
//                        newestMusicViewModel.musicStop();
                    }
                },
                getLayoutInflater(),
                new ArrayList<>());
        newestMusicView.setAdapter(newestMusicAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        newestMusicView.setLayoutManager(layoutManager);

        rankingPagerAdapter = new RankingPagerAdapter(getContext(), R.layout.ranking_pager);
        rankingViewPager.setAdapter(rankingPagerAdapter);
        rankingViewPager.initIndicator();
        rankingViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getContext().getColor(R.color.colorPrimary))
                .setNormalColor(getContext().getColor(R.color.colorLight))
                .setIndicatorPadding(40)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        rankingViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        rankingViewPager.getIndicator().build();
        rankingViewPager.setMultiScreen(0.88f);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        newestMusicViewModel = new ViewModelProvider(this).get(NewestMusicViewModel.class);
        rankingViewModel = new ViewModelProvider(this).get(RankingViewModel.class);

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

    }
}
