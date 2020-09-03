package com.devgd.melonclone.domain.player.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.view.adapter.LyricAdapter;
import com.devgd.melonclone.domain.player.view.fragment.PlayerControllerView;
import com.devgd.melonclone.domain.player.viewmodel.MusicViewModel;
import com.devgd.melonclone.domain.player.viewmodel.PlayerViewModel;
import com.devgd.melonclone.domain.search.view.activity.SearchActivity;
import com.devgd.melonclone.global.model.view.activity.MelonCloneBaseActivity;

import org.watermelon.framework.global.consts.Constants;
import org.watermelon.framework.global.customview.SquareImageView;
import org.watermelon.framework.utils.image.GlideImgManager;
import org.watermelon.framework.utils.image.ImageSource;

import java.util.ArrayList;

public class PlayerActivity extends MelonCloneBaseActivity {

    // Views
    ImageButton userBtn;
    LinearLayout halfView;
    LinearLayout lyricBox;
    RecyclerView lyricView;
    LyricAdapter lyricAdapter;
    ImageButton minimizeBtn;
    SquareImageView albumImg;
    LinearLayout statusGroup;
    RelativeLayout playTimeGroup;

    // LifecycleView
    PlayerControllerView playerControllerView;

    // ViewModels
    PlayerViewModel playerViewModel;
    MusicViewModel musicViewModel;

    @Override
    protected void layoutInit() {
        setContentView(R.layout.main_player);

        userBtn = findViewById(R.id.user_btn);
        halfView = findViewById(R.id.background_half_light);
        lyricBox = findViewById(R.id.lyric_box);
        lyricView = findViewById(R.id.lyrics_group);
        minimizeBtn = findViewById(R.id.minimize_btn);

        albumImg = findViewById(R.id.album_img);
        statusGroup = findViewById(R.id.status_group);
        playTimeGroup = findViewById(R.id.play_time_group);

        playerControllerView = new PlayerControllerView(this);
        playerControllerView.layoutInit(getRootView());
    }

    @Override
    protected void viewInit() {
        lyricAdapter = new LyricAdapter(
                this,
                getLayoutInflater(),
                new ArrayList<>());
        lyricView.setAdapter(lyricAdapter);

        playerControllerView.viewInit(getRootView());
    }

    @Override
    protected void viewModelInit() {
        // ViewModel init
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        musicViewModel.getCurrentMusic().observe(this, music -> {
            // TODO get image source
//            Glide.with(this).asFile().load(getResources().getDrawable(R.drawable.sample_img2, getTheme())).apply(new RequestOptions().centerCrop()).into(albumImg);
            GlideImgManager.getInstance().setImages(this, albumImg,
                    new ImageSource(music.getAlbum().getAlbumJacketUrl(), ImageView.ScaleType.CENTER_CROP, ImageSource.SourceType.DRAWABLE));
            if (music.getMusicLyricList() != null) {
                lyricAdapter.setLyrics(music.getMusicLyricList());
                lyricAdapter.notifyDataSetChanged();
            }
        });


        // Check User
        playerViewModel.getViewState().observe(this, getStateObserver(this));
        playerViewModel.checkLogin();

        playerControllerView.viewModelInit(playerViewModel, musicViewModel);
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
            playerControllerView.colorChange(Constants.Theme.COLOR_LIGHT);
        });

        minimizeBtn.setOnClickListener(v -> {
            if (halfView.getVisibility() == View.GONE) {
                halfView.setVisibility(View.VISIBLE);

                albumImg.setVisibility(View.VISIBLE);
                statusGroup.setVisibility(View.VISIBLE);
                playerControllerView.colorChange(Constants.Theme.COLOR_DARK);
            } else {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

/*
        playBtn.setOnClickListener(v -> {
            playerViewModel.musicPlay();
        });*/

        playerControllerView.listenerInit();
    }
}