package com.devgd.melonclone.domain.search.view.adapter;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.global.model.handler.FeedPlayerHandler;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

import java.util.List;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder>  {

    Context mContext;
    LayoutInflater inflater;
    List<Music> feedList;
    FeedPlayerHandler handler;

    public FeedListAdapter(Context context, LayoutInflater inflater, List<Music> feedList, FeedPlayerHandler handler) {
        this.mContext = context;
        this.inflater = inflater;
        this.feedList = feedList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextureView feedVideo;
        ImageView feedAlbumImg;
        ImageButton feedPlayBtn;
        TextView musicName;
        TextView artistName;

        public ViewHolder(View itemView) {
            super(itemView);
            feedVideo = itemView.findViewById(R.id.feed_video);
            feedAlbumImg = itemView.findViewById(R.id.feed_image);
            feedPlayBtn = itemView.findViewById(R.id.feed_play_btn);
//            musicName = itemView.findViewById(R.id.newest_music_name);
//            artistName = itemView.findViewById(R.id.newest_artist_name);
        }
    }

    @NonNull
    @Override
    public FeedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_music_item, parent, false);
        return new FeedListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedListAdapter.ViewHolder holder, int position) {
        Music music = feedList.get(position);
        GlideImgManager.getInstance().setImages(mContext, holder.feedAlbumImg,
                new ImageSource(
                        music.getAlbum().getAlbumJacketUrl(),
                        ImageView.ScaleType.CENTER_CROP,
                        ImageSource.SourceType.DRAWABLE
                )
        );
        holder.feedPlayBtn.setOnClickListener(v -> {
            holder.feedPlayBtn.setVisibility(View.INVISIBLE);
            if (music.isMV()) {
                holder.feedAlbumImg.setVisibility(View.INVISIBLE);
                holder.feedVideo.setVisibility(View.VISIBLE);
            }
        });
        holder.feedVideo.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Message message = new Message();
                message.what = FeedPlayerHandler.PLAY;
                message.obj = music;
                handler.sendMessage(message);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                playerList.get(position).pause();
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
//        holder.musicName.setText(music.getMusicTitleName());
//        holder.artistName.setText(music.getArtist().getArtistName());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public void setList(List<Music> list) {
        this.feedList = list;
    }
}
