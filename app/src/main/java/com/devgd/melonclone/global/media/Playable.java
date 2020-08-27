package com.devgd.melonclone.global.media;

import android.content.Context;
import android.view.TextureView;

import com.devgd.melonclone.domain.player.domain.Music;

public interface Playable {
    void mediaPlay(Context context, Music music, TextureView view);
    void mediaStop();
    boolean isPlay();
    long getCurrentPosition();
}
