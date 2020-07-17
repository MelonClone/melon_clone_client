package com.devgd.melonclone.domain.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.main.viewmodel.PlayerViewModel;
import com.devgd.melonclone.utils.store.LyricSample;
import com.devgd.melonclone.utils.view.SqureImageView;

public class MainActivity extends AppCompatActivity {
    LinearLayout halfView;
    LinearLayout lyricBox;
    ListView lyricView;
    ImageButton lyricClose;

    SqureImageView albumImg;
    LinearLayout statusGroup;
    RelativeLayout playTimeGroup;

    PlayerViewModel playerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_player);

        halfView = findViewById(R.id.background_half_light);
        lyricBox = findViewById(R.id.lyric_box);
        lyricView = findViewById(R.id.lyrics_group);
        lyricClose = findViewById(R.id.minimize_btn);

        albumImg = findViewById(R.id.album_img);
        statusGroup = findViewById(R.id.status_group);
        playTimeGroup = findViewById(R.id.play_time_group);

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        lyricView.setAdapter(
                new LyricAdapter(
                        this,
                        getLayoutInflater(),
                        playerViewModel.getMusic()
                                .getValue()
                                .getMusicLyricList()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        lyricBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfView.getVisibility() == View.VISIBLE) {
                    halfView.setVisibility(View.GONE);

                    albumImg.setVisibility(View.INVISIBLE);
                    statusGroup.setVisibility(View.INVISIBLE);
                }
                lyricView.setVisibility(View.VISIBLE);

            }
        });

        lyricClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (halfView.getVisibility() == View.GONE) {
                    halfView.setVisibility(View.VISIBLE);

                    albumImg.setVisibility(View.VISIBLE);
                    statusGroup.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

        /*mainBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST", event.getActionMasked()+"");
                switch (event.getActionMasked()) {
                    case ACTION_UP:
                        mainBtn.setShapeType(ShapeType.FLAT);
                        mainBtn.setShadowColorDark(getColor(R.color.colorDark));
                        mainBtn.setShadowColorLight(getColor(R.color.colorLight));
                        v.performClick();
                        break;
                    case ACTION_DOWN:
                    default:
                        mainBtn.setShapeType(ShapeType.PRESSED);
                        mainBtn.setTranslationZ(0);
                        mainBtn.setShadowColorDark(getColor(R.color.colorMain));
                        mainBtn.setShadowColorLight(getColor(R.color.colorMain));
                        break;
                }
                return true;
            }
        });*/