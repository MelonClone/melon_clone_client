package com.devgd.melonclone.domain.player.view.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.activity.LifecycleView;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;

public class MainPlayerView implements LifecycleView {
    RelativeLayout playlistBtn;
    ImageButton playBtn;
    SeekBar progressView;
    ImageButton pauseBtn;
    ImageButton prevBtn;
    ImageButton nextBtn;

    // ViewModels
    PlayerViewModel playerViewModel;

    @Override
    public void layoutInit(ViewGroup parentViewGroup) {
        playlistBtn = parentViewGroup.findViewById(R.id.playlist_btn_group);
        playBtn = parentViewGroup.findViewById(R.id.play_btn);
        progressView = parentViewGroup.findViewById(R.id.play_seekbar);
        pauseBtn = parentViewGroup.findViewById(R.id.pause_btn);
        prevBtn = parentViewGroup.findViewById(R.id.prev_btn);
        nextBtn = parentViewGroup.findViewById(R.id.next_btn);
    }

    @Override
    public void viewInit(ViewGroup viewGroup) {
        progressView.setMax(100);
        progressView.setProgress(0);
    }

    @Override
    public void viewModelInit(BaseActivity activity, BaseViewModel viewModel) {
        if (viewModel instanceof PlayerViewModel) {
            playerViewModel = (PlayerViewModel) viewModel;

            if (playerViewModel.getPlayer().getValue() != null) {
                progressView.setProgress(playerViewModel.getPlayer().getValue().getCurrentPlaytime());
            }

            playerViewModel.getPlayer().observe(activity, player -> {
                if (player.isPlay()) {
                    playBtn.setImageDrawable(activity.getDrawable(R.drawable.ic_pause));
                } else {
                    playBtn.setImageDrawable(activity.getDrawable(R.drawable.ic_play_button));
                }
            });
        }
    }

    @Override
    public void listenerInit() {
        PlayManager.getInstance().addPlaytimeListener(progress -> {
            progressView.setProgress(progress);
        });


        playBtn.setOnClickListener(v -> {
            playerViewModel.musicPlay();
        });
    }
}
