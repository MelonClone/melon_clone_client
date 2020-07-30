package com.devgd.melonclone.domain.player.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.global.customview.SqureImageView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;

    // override other abstract methods here
    List<Playlist> playlistList;
    int curPlay = 0;

    public PlaylistAdapter(Context context, LayoutInflater inflater, List<Playlist> playlistList) {
        this.context = context;
        this.inflater = inflater;
        this.playlistList = playlistList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        SqureImageView albumImg;
        TextView musicName;
        TextView artistName;
        ImageButton moreBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            albumImg = itemView.findViewById(R.id.album_img);
            musicName = itemView.findViewById(R.id.music_name);
            artistName = itemView.findViewById(R.id.artist_name);
            moreBtn = itemView.findViewById(R.id.more_btn);
        }
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.playlist_item, parent, false);
        return new PlaylistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        final int pos = position;

        Playlist playlist = playlistList.get(position);
        // TODO albumImg set using Glide
        holder.musicName.setText(playlist.getMusic().getMusicTitleName());
        holder.artistName.setText(playlist.getMusic().getArtist().getArtistName());

        holder.moreBtn.setOnClickListener(view -> {
            Toast.makeText(context, "MORE!", Toast.LENGTH_SHORT).show();
        });

        int textColor = (curPlay == position) ? context.getColor(R.color.colorMain) : context.getColor(R.color.colorLight);
        holder.musicName.setTextColor(textColor);
        holder.artistName.setTextColor(textColor);
    }

    @Override
    public long getItemId(int position) {
        return playlistList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public void setList(List<Playlist> playlists) {
        this.playlistList = playlists;
    }

    public void addItem(Playlist playlist) {
        this.playlistList.add(playlist);
    }

    public Playlist getItem(int index) {
        return this.playlistList.get(index);
    }

    public Playlist removeItem(int index) {
        return this.playlistList.remove(index);
    }

    public void setCurPlay(int position) {
        this.curPlay = position;
    }

}
