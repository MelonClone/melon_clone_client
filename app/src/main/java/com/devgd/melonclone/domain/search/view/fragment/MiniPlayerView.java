package com.devgd.melonclone.domain.search.view.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.activity.LayoutLifecycle;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.github.guilhe.views.CircularProgressView;

public class MiniPlayerView implements LayoutLifecycle {
    RelativeLayout playlistBtn;
    ImageButton playBtn;
    CircularProgressView progressView;
    ImageButton pauseBtn;
    ImageButton prevBtn;
    ImageButton nextBtn;

    @Override
    public void layoutInit(ViewGroup parentViewGroup) {
        playlistBtn = parentViewGroup.findViewById(R.id.playlist_btn_group);
        playBtn = parentViewGroup.findViewById(R.id.play_btn);
        progressView = parentViewGroup.findViewById(R.id.player_progress);
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
        Log.d("TEST", "Viewmodel Check : "+(viewModel instanceof PlayerViewModel));
        if (viewModel instanceof PlayerViewModel) {
            PlayerViewModel playerViewModel = (PlayerViewModel) viewModel;

            if (playerViewModel.isPlay()) {
                playBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.INVISIBLE);

                playerViewModel.getPlayer().observe(activity, player -> {
                    progressView.setProgress(player.getCurrentPlaytime());
                });
            } else {
                playBtn.setVisibility(View.INVISIBLE);
                pauseBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void listenerInit() {
        PlayManager.getInstance().addPlaytimeListener(progress -> {
            progressView.setProgress(progress);
        });
    }
}
