package com.devgd.melonclone.domain.player.viewmodel;

import android.content.Context;
import android.view.TextureView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistItem;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.domain.user.view.activity.LoginActivity;
import com.devgd.melonclone.domain.user.view.activity.ProfileActivity;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.media.Playable;
import com.devgd.melonclone.global.media.player.ExoMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.store.PlaylistSample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.devgd.melonclone.global.model.view.states.StateCode.ACTIVITY_CHANGE;

public class PlayerViewModel extends BaseViewModel implements Playable {

    private String currentPlaylistName;
    private PlaylistItem currentPlaylistItem;
    private MutableLiveData<Player> playerInfo;
    private MutableLiveData<Music> currentMusic;
    private MutableLiveData<Map<String, Playlist>> playlistCollection;
    private MusicModel musicModel = new MusicModel();
    private LyricModel lyricModel = new LyricModel();

    // TODO DI or repository to singleton
    private MusicRepository musicRepository;
    private LyricRepository lyricRepository;
    //    private PlaylistRepository playlistRepository;
    private PlayerModel playerModel;

    // private MutableLiveData<List<Playlist>> playlistList;


    @Override
    protected void init() {
        musicRepository = MusicRepository.getInstance();
        lyricRepository = LyricRepository.getInstance();
//        playlistRepository = PlaylistRepository.getInstance();
        playerModel = PlayerModel.getInstance();

        loadPlayer();
        getCurrentMusic();
        loadLyric();
        loadPlaylistCollection();
    }

    public LiveData<Player> getPlayer() {
        if (playerInfo == null) {
            playerInfo = new MutableLiveData<>();
            loadPlayer();
        }

        return playerInfo;
    }

    public LiveData<Music> getCurrentMusic() {

        if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            if (playerModel.getLastPlayedMusic() != null)
                currentMusic.postValue(playerModel.getLastPlayedMusic());
        }

        return currentMusic;
    }

    public List<PlaylistItem> getCurrentPlaylist() {
        if (playlistCollection.getValue() != null) {
            Playlist playlist = playlistCollection.getValue().get(currentPlaylistName);
            if (playlist != null) {
                return playlist.getPlaylistItems();
            }
        }
        return new LinkedList<>();
    }

    public LiveData<Map<String, Playlist>> getPlaylistCollection() {
        if (playlistCollection == null) {
            playlistCollection = new MutableLiveData<>();
            loadPlaylistCollection();
        }
        return playlistCollection;
    }

    private void loadPlayer() {
        playerInfo = new MutableLiveData<>();
        if (playerModel.getPlayer() != null) {
            playerInfo.postValue(playerModel.getPlayer());
        }
    }

    private void loadMusic(Music music) {
        musicRepository.getMusicInfo(music, new Repository.RepoCallback<Music>() {
            @Override
            public void success(Music music) {
                currentMusic.postValue(music);
                loadLyric();
            }

            @Override
            public void fail(NetworkState networkState) {

            }
        });

    }

    private void loadLyric() {
        if (currentMusic.getValue() != null) {
            lyricRepository.getLyricList(currentMusic.getValue(), new Repository.RepoListCallback<List<Lyric>>() {
                @Override
                public void success(List<Lyric> lyrics) {
                    currentMusic.getValue().setMusicLyricList(lyrics);
                }

                @Override
                public void fail(NetworkState networkState) {

                }
            });
        }
    }

    private void loadPlaylistCollection() {
        playlistCollection = new MutableLiveData<>();
        Map<String, Playlist> customPlaylist = new HashMap<>();
        for (int i=0; i<10; i++) {
            Playlist list = PlaylistSample.getSample();
            if (i == 0) list.setPlaylistName("default");
            customPlaylist.put(list.getPlaylistName(), list);
        }
        playlistCollection.setValue(customPlaylist);
        currentPlaylistName = "default";
        getNextMusic(customPlaylist);
        /*playlistRepository.getAllPlaylist(new Repository.RepoListCallback<List<Playlist>>() {
            @Override
            public void success(List<Playlist> playlistList) {
                mPlaylistList.postValue(playlistList);
            }

            @Override
            public void fail(NetworkState networkState) {

            }
        });*/
    }

    public void changeUserPage() {
        if (loginState.getValue() != null && loginState.getValue().isLogin()) {
            state.postValue(new ViewState(ACTIVITY_CHANGE, ProfileActivity.class, null));
        } else {
            state.postValue(new ViewState(ACTIVITY_CHANGE, LoginActivity.class, null));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        musicStop();
    }

    @Override
    public void mediaPlay(Context context, Music music, TextureView view) {
        Player playerInfo = getPlayer().getValue();

        if (playerInfo != null) {
            if (playerInfo.isPlay()) {
                PlayManager.getInstance().pausePlayer();
                playerInfo.setPlay(false);
            } else if (playerInfo.isPlayed()) {
                PlayManager.getInstance().startPlayer();
                playerInfo.setPlay(true);
            } else {
                if (currentMusic.getValue() != null &&
                        !(PlayManager.getInstance().isPrepared() || PlayManager.getInstance().isPlaying())) {
                    MusicPlayer mediaPlayer = new ExoMediaPlayer(currentMusic.getValue().getMusicUrl(), 1f, context);
                    PlayManager.getInstance().setPlayer(mediaPlayer);
                    PlayManager.getInstance().addMusicChangedListener(() -> {
                        if (playlistCollection.getValue() != null) {
                            currentMusic.postValue(getNextMusic(playlistCollection.getValue()));
                        }
                    });
                    PlayManager.getInstance().startPlayer();
                }
                playerInfo.setPlay(true);
            }
            this.playerInfo.postValue(getPlayer().getValue());
        }
    }

    private Music getNextMusic(Map<String, Playlist> playlistCollection) {
        Playlist playlist = playlistCollection.get(currentPlaylistName);
        if (playlist != null) {
            LinkedList<PlaylistItem> playlistItems = playlist.getPlaylistItems();
            PlaylistItem playlistItem;
            if (currentPlaylistItem == null || currentPlaylistItem == playlistItems.getLast()) {
                playlistItem = playlistItems.getFirst();
            } else {
                playlistItem = playlistItems.listIterator(playlistItems.indexOf(currentPlaylistItem)).next();
            }
            currentPlaylistItem = playlistItem;
            currentMusic.postValue(playlistItem.getMusic());
            return currentPlaylistItem.getMusic();
        } else {
            return null;
        }
    }

    @Override
    public void mediaStop() {
        // Player 종료
        if (!PlayManager.getInstance().isDestroyed()) {
            PlayManager.getInstance().destroyPlayer();
        }
    }

    public void changeRepeatMode() {
        Player repeatPlayer = playerInfo.getValue();
        if (repeatPlayer.getPlayMode().getRepeatMode() == MusicPlayer.Repeat.ALL_LOOP) {
            repeatPlayer.getPlayMode().setRepeatMode(MusicPlayer.Repeat.OFF);
        } else {
            repeatPlayer.getPlayMode().setRepeatMode(MusicPlayer.Repeat.ALL_LOOP);
        }
        playerInfo.postValue(repeatPlayer);
        PlayManager.getInstance().setRepeatMode(repeatPlayer.getPlayMode().getRepeatMode());
    }

    @Override
    public boolean isPlay() {
        return PlayManager.getInstance().isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        return PlayManager.getInstance().getCurrentPosition();
    }
}
