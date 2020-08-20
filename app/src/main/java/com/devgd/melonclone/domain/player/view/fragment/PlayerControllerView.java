package com.devgd.melonclone.domain.player.view.fragment;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.view.activity.PlaylistActivity;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.activity.LifecycleView;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.view.TimeFormatter;

public class PlayerControllerView implements LifecycleView {

    BaseActivity mContext;

    // Views
    ImageView playlistBtn;
    ImageButton playBtn;
    SeekBar progressView;
    ImageButton pauseBtn;
    ImageButton prevBtn;
    ImageButton nextBtn;
    ImageButton eqBtn;

    ImageButton repeatBtn;
    TextView currentPlaytime;
    TextView restPlaytime;
    ImageButton shuffleBtn;
    SeekBar playtimeSeekbar;

    // ViewModels
    PlayerViewModel playerViewModel;

    public PlayerControllerView(BaseActivity context) {
        mContext = context;
    }

    @Override
    public void layoutInit(ViewGroup parentViewGroup) {
        // Controller
        playlistBtn = parentViewGroup.findViewById(R.id.playlist_btn);
        playBtn = parentViewGroup.findViewById(R.id.play_btn);
        progressView = parentViewGroup.findViewById(R.id.playtime_seekbar);
        pauseBtn = parentViewGroup.findViewById(R.id.pause_btn);
        prevBtn = parentViewGroup.findViewById(R.id.prev_btn);
        nextBtn = parentViewGroup.findViewById(R.id.next_btn);
        eqBtn = parentViewGroup.findViewById(R.id.eq_btn);

        // Seekbar
        repeatBtn = parentViewGroup.findViewById(R.id.repeat_btn);
        currentPlaytime = parentViewGroup.findViewById(R.id.current_playtime);
        restPlaytime = parentViewGroup.findViewById(R.id.rest_playtime);
        shuffleBtn = parentViewGroup.findViewById(R.id.shuffle_btn);
        playtimeSeekbar = parentViewGroup.findViewById(R.id.playtime_seekbar);
    }

    @Override
    public void viewInit(ViewGroup viewGroup) {
        progressView.setMax(100);
        progressView.setProgress(0);
    }

    @Override
    public void viewModelInit(BaseViewModel viewModel) {
        if (viewModel instanceof PlayerViewModel) {
            playerViewModel = (PlayerViewModel) viewModel;

            if (playerViewModel.getPlayer().getValue() != null) {
                progressView.setProgress(playerViewModel.getPlayer().getValue().getCurrentPlaytime());
            }

            playerViewModel.getPlayer().observe(mContext, player -> {
                if (player.isPlay()) {
                    playBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_pause));
                } else {
                    playBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_play_button));
                }
            });

            playerViewModel.getCurrentMusic().observe(mContext, music -> {
                restPlaytime.setText(TimeFormatter.millisecondToClock(PlayManager.getInstance().getDuration()));
            });
        }
    }

    @Override
    public void listenerInit() {
        PlayManager.getInstance().addPlaytimeListener(progress -> {
            int curTime = PlayManager.getInstance().getCurrentPosition();
            int maxTime = PlayManager.getInstance().getDuration();
            currentPlaytime.setText(TimeFormatter.millisecondToClock(curTime));
            restPlaytime.setText(TimeFormatter.millisecondToClock(maxTime));
            progressView.setMax(maxTime / 1000);
            progressView.setProgress(curTime / 1000);
        });

        playtimeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    PlayManager.getInstance().seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        playBtn.setOnClickListener(v -> {
            playerViewModel.musicPlay();
        });

        playlistBtn.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PlaylistActivity.class);
            mContext.startActivity(intent);
            mContext.overridePendingTransition(0, 0);
        });
    }

    public void colorChange(Constants.Theme colorTheme) {
        switch (colorTheme) {
            case COLOR_DARK:
                playlistBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_playlist_black));
                nextBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_black));
                prevBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_black));
                eqBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_mixer_black));
                break;
            case COLOR_LIGHT:
                playlistBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_playlist_white));
                nextBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_white));
                prevBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_white));
                eqBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_mixer_white));
                break;
        }
    }
}
