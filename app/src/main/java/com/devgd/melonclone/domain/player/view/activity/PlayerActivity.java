package com.devgd.melonclone.domain.player.view.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.view.adapter.LyricAdapter;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.customview.SqureImageView;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.states.LoginState;

public class PlayerActivity extends BaseActivity {

    // Views
    ImageButton userBtn;
    LinearLayout halfView;
    LinearLayout lyricBox;
    ListView lyricView;
    LyricAdapter lyricAdapter;
    ImageButton lyricClose;

    SqureImageView albumImg;
    LinearLayout statusGroup;
    RelativeLayout playTimeGroup;

    // ViewModels
    PlayerViewModel playerViewModel;

    // LiveDatas
    LiveData<LoginState> loginState;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.main_player);

        userBtn = findViewById(R.id.user_btn);
        halfView = findViewById(R.id.background_half_light);
        lyricBox = findViewById(R.id.lyric_box);
        lyricView = findViewById(R.id.lyrics_group);
        lyricClose = findViewById(R.id.minimize_btn);

        albumImg = findViewById(R.id.album_img);
        statusGroup = findViewById(R.id.status_group);
        playTimeGroup = findViewById(R.id.play_time_group);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        playerViewModel.getCurrentMusic().observe(this, music -> lyricAdapter.notifyDataSetChanged());

        // Check User
        playerViewModel.getViewState().observe(this, getStateObserver(this));
        loginState = playerViewModel.getLoginState();
        playerViewModel.checkLogin();
    }

    @Override
    protected void viewInit() {
        lyricAdapter = new LyricAdapter(
                this,
                getLayoutInflater(),
                playerViewModel.getCurrentMusic());
        lyricView.setAdapter(lyricAdapter);
    }

    @Override
    protected void listenerInit() {
        userBtn.setOnClickListener(v -> {
            playerViewModel.changeUserPage();
        });

        lyricBox.setOnClickListener(v -> {
            if (halfView.getVisibility() == View.VISIBLE) {
                halfView.setVisibility(View.GONE);

                albumImg.setVisibility(View.INVISIBLE);
                statusGroup.setVisibility(View.INVISIBLE);
            }
            lyricView.setVisibility(View.VISIBLE);

        });

        lyricClose.setOnClickListener(v -> {
            if (halfView.getVisibility() == View.GONE) {
                halfView.setVisibility(View.VISIBLE);

                albumImg.setVisibility(View.VISIBLE);
                statusGroup.setVisibility(View.VISIBLE);
            }
        });
    }
}