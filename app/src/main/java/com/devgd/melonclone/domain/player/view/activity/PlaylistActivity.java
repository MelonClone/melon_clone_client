package com.devgd.melonclone.domain.player.view.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.view.adapter.PlaylistAdapter;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.global.customview.RoundImageView;
import com.devgd.melonclone.global.model.view.activity.BaseActivity;
import com.devgd.melonclone.global.model.view.states.LoginState;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

import java.util.ArrayList;

public class PlaylistActivity extends BaseActivity {

    // Views
    ImageButton userBtn;
    ImageButton minimizeBtn;
    ImageButton playlistBtn;
    RelativeLayout musicDetailBtn;
    RoundImageView albumImg;
    RecyclerView playlistRecyclerList;
    PlaylistAdapter playlistAdapter;

    // ViewModels
    PlayerViewModel playerViewModel;

    // LiveDatas
    LiveData<LoginState> loginState;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.main_playlist);

        userBtn = findViewById(R.id.user_btn);
        minimizeBtn = findViewById(R.id.minimize_btn);
        playlistBtn = findViewById(R.id.playlist_btn);
        musicDetailBtn = findViewById(R.id.music_detail_btn);
        albumImg = findViewById(R.id.album_img);
        playlistRecyclerList = findViewById(R.id.playlist_recycler_list);
    }

    @Override
    protected void viewInit() {
        playlistBtn.setVisibility(View.GONE);
        musicDetailBtn.setVisibility(View.VISIBLE);
        playlistRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        playlistAdapter = new PlaylistAdapter(
                this,
                getLayoutInflater(),
                new ArrayList<>());
        playlistRecyclerList.setAdapter(playlistAdapter);
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        playerViewModel.getCurrentMusic().observe(this, music -> {
            // TODO mini player change
            GlideImgManager.getInstance().setImages(this, albumImg,
                    new ImageSource(music.getAlbum().getAlbumJacketUrl(), ImageView.ScaleType.CENTER_CROP));
        });

        // Check User
        playerViewModel.getViewState().observe(this, getStateObserver(this));
        loginState = playerViewModel.getLoginState();
        playerViewModel.checkLogin();

        playerViewModel.getPlaylistCollection().observe(this, playlistCollection -> {
            playlistAdapter.setList(playerViewModel.getCurrentPlaylist());
            playlistAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void listenerInit() {
        userBtn.setOnClickListener(v -> {
            playerViewModel.changeUserPage();
        });

        minimizeBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });
        musicDetailBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }
}