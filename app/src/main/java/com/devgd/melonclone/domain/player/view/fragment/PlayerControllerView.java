package com.devgd.melonclone.domain.player.view.fragment;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.view.PlayerState;
import com.devgd.melonclone.domain.player.view.activity.PlaylistActivity;
import com.devgd.melonclone.domain.player.viewmodel.MusicViewModel;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.model.view.activity.MelonCloneBaseActivity;

import org.watermelon.framework.global.consts.Constants;
import org.watermelon.framework.global.media.PlayManager;
import org.watermelon.framework.global.media.player.MusicPlayer;
import org.watermelon.framework.global.model.view.activity.LifecycleView;
import org.watermelon.framework.global.model.view.activity.ThemeApplicableState;
import org.watermelon.framework.global.model.viewmodel.BaseViewModel;
import org.watermelon.framework.utils.view.TimeFormatter;

public class PlayerControllerView implements LifecycleView, ThemeApplicableState<PlayerState> {

    MelonCloneBaseActivity mContext;

    // Views
    ImageView playlistBtn;
    ImageButton playBtn;
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
    MusicViewModel musicViewModel;

    PlayerState playerState = new PlayerState();
    Constants.Theme colorTheme = Constants.Theme.COLOR_DARK;

    public PlayerControllerView(MelonCloneBaseActivity context) {
        mContext = context;
    }

    @Override
    public void layoutInit(ViewGroup parentViewGroup) {
        // Controller
        playlistBtn = parentViewGroup.findViewById(R.id.playlist_btn);
        playBtn = parentViewGroup.findViewById(R.id.play_btn);
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
        playtimeSeekbar.setMax(100);
        playtimeSeekbar.setProgress(0);
    }

    @Override
    public void viewModelInit(BaseViewModel... viewModels) {
        for (BaseViewModel viewModel : viewModels) {
            if (viewModel instanceof PlayerViewModel) {
                playerViewModel = (PlayerViewModel) viewModel;
            } else if (viewModel instanceof MusicViewModel) {
                musicViewModel = (MusicViewModel) viewModel;
            }
        }

        if (playerViewModel.getPlayer().getValue() != null) {
            playtimeSeekbar.setProgress(playerViewModel.getPlayer().getValue().getCurrentPlaytime());
            restPlaytime.setText(TimeFormatter.millisecondToClock(PlayManager.getInstance().getDuration()));

        }

        playerViewModel.getPlayer().observe(mContext, player -> {
            if (player == null || !player.isPlay()) {
                playBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_play_button));
            } else {
                playBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_pause));
            }

            if (player == null || player.getPlayMode().getRepeatMode() == MusicPlayer.Repeat.ALL_LOOP) {
                getApplicableState().setRepeat(true);
            } else {
                getApplicableState().setRepeat(false);
            }
            colorChange(getTheme());
        });

        musicViewModel.getCurrentMusic().observe(mContext, music -> {
            long maxTime = music.getPlaytime();
            currentPlaytime.setText(TimeFormatter.millisecondToClock(0));
            restPlaytime.setText(TimeFormatter.millisecondToClock(maxTime));
            playtimeSeekbar.setMax((int) (maxTime / 1000));
            playtimeSeekbar.setProgress(0);

            if (playerViewModel.isPlay()) {
                playerViewModel.mediaStop();
                playerViewModel.mediaPlay(mContext, music, null);
            }
        });
    }

    @Override
    public void listenerInit() {
        PlayManager.getInstance().addPlaytimeListener(progress -> {
            long curTime = PlayManager.getInstance().getCurrentPosition();
            long maxTime = PlayManager.getInstance().getDuration();
            currentPlaytime.setText(TimeFormatter.millisecondToClock(curTime));
            restPlaytime.setText(TimeFormatter.millisecondToClock(maxTime));
            playtimeSeekbar.setMax((int) (maxTime / 1000));
            playtimeSeekbar.setProgress((int) (curTime / 1000));
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
            playerViewModel.mediaPlay(mContext, musicViewModel.getCurrentMusic().getValue(), null);
        });

        playlistBtn.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PlaylistActivity.class);
            mContext.startActivity(intent);
            mContext.overridePendingTransition(0, 0);
        });

        repeatBtn.setOnClickListener(v -> {
            playerViewModel.changeRepeatMode();
        });

        nextBtn.setOnClickListener(v -> {
            musicViewModel.getNextMusic();
        });

        prevBtn.setOnClickListener(v -> {
            musicViewModel.getPrevMusic();
        });
    }

    @Override
    public void colorChange(Constants.Theme colorTheme) {
        switch (colorTheme) {
            case COLOR_DARK:
                playlistBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_playlist_black));
                nextBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_black));
                prevBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_black));
                eqBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_mixer_black));
                repeatBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_repeat_black));
                shuffleBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_random_black));
                break;
            case COLOR_LIGHT:
                playlistBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_playlist_white));
                nextBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_white));
                prevBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_next_white));
                eqBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_mixer_white));
                repeatBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_repeat_white));
                shuffleBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_random_white));
                break;
        }

        if (playerState.isRepeat()) {
            repeatBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_repeat_act));
        }

        if (playerState.isShuffle()) {
            shuffleBtn.setImageDrawable(mContext.getDrawable(R.drawable.ic_random_act));
        }

        setTheme(colorTheme);
    }

    @Override
    public void setTheme(Constants.Theme colorTheme) {
        this.colorTheme = colorTheme;
    }

    @Override
    public Constants.Theme getTheme() {
        return colorTheme;
    }

    @Override
    public void setApplicableState(PlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    public PlayerState getApplicableState() {
        return playerState;
    }
}
