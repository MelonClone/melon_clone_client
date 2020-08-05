package com.devgd.melonclone.domain.search.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

import java.util.List;

public class NewestMusicAdapter extends RecyclerView.Adapter<NewestMusicAdapter.ViewHolder>  {

    Context mContext;
    LayoutInflater inflater;
    List<Music> newestMusicList;

    public NewestMusicAdapter(Context context, LayoutInflater inflater, List<Music> newestMusicList) {
        this.mContext = context;
        this.inflater = inflater;
        this.newestMusicList = newestMusicList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newestAlbumImg;
        TextView musicName;
        TextView artistName;

        public ViewHolder(View itemView) {
            super(itemView);
            newestAlbumImg = itemView.findViewById(R.id.newest_album_img);
            musicName = itemView.findViewById(R.id.newest_music_name);
            artistName = itemView.findViewById(R.id.newest_artist_name);

        }
    }

    @NonNull
    @Override
    public NewestMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.newest_music_item, parent, false);
        return new NewestMusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewestMusicAdapter.ViewHolder holder, int position) {
        Music music = newestMusicList.get(position);
        GlideImgManager.getInstance().setImages(mContext, holder.newestAlbumImg,
                new ImageSource(
                        music.getAlbum().getAlbumJacketUrl(),
                        ImageView.ScaleType.CENTER_CROP,
                        ImageSource.SourceType.DRAWABLE
                )
        );
        holder.musicName.setText(music.getMusicTitleName());
        holder.artistName.setText(music.getArtist().getArtistName());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return newestMusicList.size();
    }

    public void setList(List<Music> list) {
        this.newestMusicList = list;
    }
}
