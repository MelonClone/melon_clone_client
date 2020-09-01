package com.devgd.melonclone.domain.player.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Lyric;

import java.util.List;

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.ViewHolder> {

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

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView lyricView;

        public ViewHolder(View itemView) {
            super(itemView);
            lyricView = itemView.findViewById(R.id.lyric);
        }
    }

    @NonNull
    @Override
    public LyricAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lyrics_line, parent, false);
        return new LyricAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricAdapter.ViewHolder holder, int position) {
        final int pos = position;

        Lyric lyric = lyricsList.get(position);
        holder.lyricView.setText(lyric.getLyricTxt());

        int textColor = (curPlay == position) ? context.getColor(R.color.colorMain) : context.getColor(R.color.colorLight);
        holder.lyricView.setTextColor(textColor);
    }

    @Override
    public long getItemId(int position) {
        return lyricsList.get(position).getLyricId();
    }

    @Override
    public int getItemCount() {
        if (lyricsList == null) return 0;
        return lyricsList.size();
    }

    public void setLyrics(List<Lyric> lyrics) {
        this.lyricsList = lyrics;
    }

    public void setCurPlay(int position) {
        this.curPlay = position;
    }
}
