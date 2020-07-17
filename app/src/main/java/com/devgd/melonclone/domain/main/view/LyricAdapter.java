package com.devgd.melonclone.domain.main.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.main.domain.Lyric;

import java.util.List;

public class LyricAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    // override other abstract methods here
    List<Lyric> lyricsList;
    int curPlay = 0;

    public LyricAdapter(Context context, LayoutInflater inflater, List<Lyric> lyricsList) {
        this.context = context;
        this.inflater = inflater;
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
    public View getView(int position, View convertView, ViewGroup parent) {

//        Lyric lyric = lyricsList.get(position);
//        ((TextView) convertView).setText(lyric.getLyric());
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lyrics_line, parent, false);
        }
        int textColor = (curPlay == position) ? context.getColor(R.color.colorMain) : context.getColor(R.color.colorLight);

        return setViewRes(position, (TextView) convertView, textColor);
    }

    private View setViewRes(int position, TextView convertView, int textColor) {

        Lyric lyric = lyricsList.get(position);
        convertView.setText(lyric.getLyricTxt());
        convertView.setTextColor(textColor);
        return convertView;
    }

    public void setCurPlay(int position) {
        this.curPlay = position;
    }
}
