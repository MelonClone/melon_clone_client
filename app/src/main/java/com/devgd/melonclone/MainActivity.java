package com.devgd.melonclone;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.devgd.melonclone.view.SqureImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout halfView;
    LinearLayout lyricBox;
    ListView lyricView;

    SqureImageView albumImg;
    LinearLayout statusGroup;
    RelativeLayout playTimeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_player);

//        mainBtn = findViewById(R.id.main_btn);
        halfView = findViewById(R.id.background_half_light);
        lyricBox = findViewById(R.id.lyric_box);
        lyricView = findViewById(R.id.lyrics_group);

        albumImg = findViewById(R.id.album_img);
        statusGroup = findViewById(R.id.status_group);
        playTimeGroup = findViewById(R.id.play_time_group);

        ArrayList<String> lyricsList = new ArrayList<>();
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");
        lyricsList.add("abcd");

        lyricView.setAdapter(new LyricAdapter(lyricsList));

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
//                    playTimeGroup.setVisibility(View.GONE);

                }

                lyricView.setVisibility(View.VISIBLE);
            }
        });
    }

    private class LyricAdapter extends BaseAdapter {

        // override other abstract methods here
        ArrayList<String> lyricsList;
        int curPlay = 0;

        public LyricAdapter(ArrayList<String> lyricsList) {
            this.lyricsList = lyricsList;
        }

        @Override
        public int getCount() {
            if (lyricsList == null) return 0;
            return lyricsList.size();
        }

        @Override
        public Object getItem(int position) {
            if (lyricsList == null) return null;
            return lyricsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.lyrics_line, container, false);
            }

//            Lyric lyric = lyricsList.get(position);
//            ((TextView) convertView).setText(lyric.getLyric());
            String lyric = lyricsList.get(position);
            ((TextView) convertView).setText(lyric);

            if (curPlay == position)
                ((TextView) convertView).setTextColor(getColor(R.color.colorMain));
            else
                ((TextView) convertView).setTextColor(getColor(R.color.colorLight));
            return convertView;
        }

        public void setCurPlay(int position) {
            this.curPlay = position;
        }
    }

    private class Lyric {
        String text;
        boolean isPlay;

        public Lyric(String text) {
            this.text = text;
        }

        public Lyric(String text, boolean isPlay) {
            this.text = text;
            this.isPlay = isPlay;
        }

        public void setPlay(boolean isPlay) {
            this.isPlay = isPlay;
        }

        public boolean isPlay() {
            return isPlay;
        }

        public String getLyric() {
            return text;
        }
    }
}
