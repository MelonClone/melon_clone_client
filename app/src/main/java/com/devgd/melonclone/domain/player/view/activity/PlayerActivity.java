package com.devgd.melonclone.domain.player.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.view.adapter.LyricAdapter;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.customview.SqureImageView;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.states.LoginState;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

import java.util.ArrayList;

public class PlayerActivity extends BaseActivity {

    // Views
    ImageButton userBtn;
    LinearLayout halfView;
    LinearLayout lyricBox;
    ListView lyricView;
    LyricAdapter lyricAdapter;
    ImageButton minimizeBtn;
    ImageButton playlistBtn;

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
        minimizeBtn = findViewById(R.id.minimize_btn);
        playlistBtn = findViewById(R.id.playlist_btn);

        albumImg = findViewById(R.id.album_img);
        statusGroup = findViewById(R.id.status_group);
        playTimeGroup = findViewById(R.id.play_time_group);
    }

    @Override
    protected void viewInit() {
        lyricAdapter = new LyricAdapter(
                this,
                getLayoutInflater(),
                new ArrayList<>());
        lyricView.setAdapter(lyricAdapter);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        playerViewModel.getCurrentMusic().observe(this, music -> {
            // TODO get image source
//            Glide.with(this).asFile().load(getResources().getDrawable(R.drawable.sample_img2, getTheme())).apply(new RequestOptions().centerCrop()).into(albumImg);
            GlideImgManager.getInstance().setImages(this, albumImg,
                    new ImageSource(getResources().getDrawable(R.drawable.sample_img2, getTheme()), ImageView.ScaleType.CENTER_CROP, ImageSource.SourceType.DRAWABLE));
            lyricAdapter.notifyDataSetChanged();
            lyricAdapter.setLyrics(music.getMusicLyricList());
        });

        // Check User
        playerViewModel.getViewState().observe(this, getStateObserver(this));
        loginState = playerViewModel.getLoginState();
        playerViewModel.checkLogin();
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

        minimizeBtn.setOnClickListener(v -> {
            if (halfView.getVisibility() == View.GONE) {
                halfView.setVisibility(View.VISIBLE);

                albumImg.setVisibility(View.VISIBLE);
                statusGroup.setVisibility(View.VISIBLE);
            }
        });

        playlistBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaylistActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }
}