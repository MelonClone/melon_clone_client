package com.devgd.melonclone.domain.search.view.adapter;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.search.viewmodel.NewestMusicViewModel;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

import java.io.IOException;
import java.util.List;

public class NewestMusicAdapter extends RecyclerView.Adapter<NewestMusicAdapter.ViewHolder>  {

    Context mContext;
    NewestMusicEventCallback mNewestMusicEventCallback;
    LayoutInflater inflater;
    List<Music> newestMusicList;

    public NewestMusicAdapter(Context context, NewestMusicEventCallback mNewestMusicEventCallback, LayoutInflater inflater, List<Music> newestMusicList) {
        this.mContext = context;
        this.mNewestMusicEventCallback = mNewestMusicEventCallback;
        this.inflater = inflater;
        this.newestMusicList = newestMusicList;
    }

    public interface NewestMusicEventCallback {
        void onClick(TextureView view, Music music);
        void onStop();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout newestAlbumGroup;
        ImageView newestAlbumImg;
        TextureView newestAlbumVideo;
        TextView musicName;
        TextView artistName;

        public ViewHolder(View itemView) {
            super(itemView);
            newestAlbumGroup = itemView.findViewById(R.id.newest_album_group);
            newestAlbumImg = itemView.findViewById(R.id.newest_album_img);
            newestAlbumVideo = itemView.findViewById(R.id.newest_album_video);
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

        holder.newestAlbumGroup.setOnClickListener(v -> {
            mNewestMusicEventCallback.onStop();
            mNewestMusicEventCallback.onClick(holder.newestAlbumVideo, music);
        });

        holder.newestAlbumVideo.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                mNewestMusicEventCallback.onStop();
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                mNewestMusicEventCallback.onStop();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
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
