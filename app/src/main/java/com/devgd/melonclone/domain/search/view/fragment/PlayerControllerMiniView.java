package com.devgd.melonclone.domain.search.view.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.viewmodel.MusicViewModel;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.activity.LifecycleView;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.github.guilhe.views.CircularProgressView;

public class PlayerControllerMiniView implements LifecycleView {

    BaseActivity mContext;

    // Views
    RelativeLayout playlistBtn;
    ImageButton playBtn;
    CircularProgressView progressView;
    ImageButton pauseBtn;
    ImageButton prevBtn;
    ImageButton nextBtn;

    // ViewModels
    PlayerViewModel playerViewModel;

    public PlayerControllerMiniView(BaseActivity context) {
        mContext = context;
    }

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
    public void viewModelInit(BaseViewModel... viewModels) {
        for (BaseViewModel viewModel : viewModels) {
            if (viewModel instanceof PlayerViewModel) {
                playerViewModel = (PlayerViewModel) viewModel;
            }
        }

        if (playerViewModel.getPlayer().getValue() != null) {
            progressView.setProgress(playerViewModel.getPlayer().getValue().getCurrentPlaytime());
        }

        if (PlayManager.getInstance().isPlaying()) {
            playBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_pause));
        }

        playerViewModel.getPlayer().observe(mContext, player -> {
            if (player.isPlay()) {
                playBtn.setVisibility(View.INVISIBLE);
                pauseBtn.setVisibility(View.VISIBLE);
            } else {
                playBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void listenerInit() {
        PlayManager.getInstance().addPlaytimeListener(progress -> {
            progressView.setProgress(progress);
        });
    }

    @Override
    public void colorChange(Constants.Theme colorTheme) {

    }
}
