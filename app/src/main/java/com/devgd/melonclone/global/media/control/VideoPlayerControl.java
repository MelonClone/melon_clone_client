package com.devgd.melonclone.global.media.control;

import android.view.Surface;

import com.devgd.melonclone.global.media.control.MusicPlayerControl;

public interface VideoPlayerControl extends MusicPlayerControl {
    void setDisplay(Surface surface);
}
