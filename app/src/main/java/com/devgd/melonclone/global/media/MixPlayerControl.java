package com.devgd.melonclone.global.media;

public interface MixPlayerControl extends MusicPlayerControl {

    void startMix();
    void stopMix();
    void pauseMix();
    void resetMix();
    void clearMix();
    void addMix(MusicPlayerControl mixPlayer);
    void setMixVolume(int index, float volume);
}