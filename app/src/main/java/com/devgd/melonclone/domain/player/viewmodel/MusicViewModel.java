package com.devgd.melonclone.domain.player.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistCollection;
import com.devgd.melonclone.domain.player.domain.PlaylistItem;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.domain.player.model.PlaylistModel;
import com.devgd.melonclone.global.model.viewmodel.MelonCloneBaseViewModel;
import com.devgd.melonclone.utils.store.LyricSample;

import org.watermelon.framework.global.media.PlayManager;
import org.watermelon.framework.global.media.player.ExoPlayerWrapper;
import org.watermelon.framework.global.media.player.MusicPlayer;
import org.watermelon.framework.global.model.repository.Repository;
import org.watermelon.framework.global.model.view.states.NetworkState;

import java.util.LinkedList;
import java.util.List;

public class MusicViewModel extends MelonCloneBaseViewModel {

    private String currentPlaylistName;
    private MutableLiveData<Music> currentMusic;
    private MutableLiveData<PlaylistCollection> playlistCollection;

    // TODO DI or repository to singleton
    private MusicRepository musicRepository;
    private LyricRepository lyricRepository;
    //    private PlaylistRepository playlistRepository;
    private MusicModel musicModel;
    private PlaylistModel playlistModel;
    private LyricModel lyricModel = new LyricModel();

    @Override
    protected void init() {
        musicRepository = MusicRepository.getInstance();
        lyricRepository = LyricRepository.getInstance();
//        playlistRepository = PlaylistRepository.getInstance();
        musicModel = MusicModel.getInstance();
        playlistModel = PlaylistModel.getInstance();
        loadMusic();
        loadLyric();
        loadPlaylistCollection();
    }

    public LiveData<Music> getCurrentMusic() {

        if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            loadMusic();
        }

        return currentMusic;
    }

    public List<PlaylistItem> getCurrentPlaylist() {
        if (playlistCollection.getValue() != null) {
            Playlist playlist = playlistCollection.getValue().getPlaylist(playlistModel.getLastPlaylist().getPlaylistName());
            if (playlist != null) {
                return playlist.getPlaylistItems();
            }
        }
        return new LinkedList<>();
    }

    public LiveData<PlaylistCollection> getPlaylistCollection() {
        if (playlistCollection == null) {
            playlistCollection = new MutableLiveData<>();
            loadPlaylistCollection();
        }
        return playlistCollection;
    }


    private void loadMusic() {
        currentMusic = new MutableLiveData<>();
        if (musicModel.getLastPlayedMusic() != null) {

            currentMusic.postValue(playlistModel.getLastPlaylist().getCurrentPlaylistItem().getMusic());
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
            Music music = currentMusic.getValue();
            music.setMusicLyricList(LyricSample.getSample());
            currentMusic.postValue(music);
            /*
            lyricRepository.getLyricList(music, new Repository.RepoListCallback<List<Lyric>>() {
                @Override
                public void success(List<Lyric> lyrics) {
                    music.setMusicLyricList(lyrics);
                    currentMusic.postValue(music);

                }

                @Override
                public void fail(NetworkState networkState) {

                }
            });

             */
        }
    }

    private void loadLyric(Music music) {
        music.setMusicLyricList(LyricSample.getSample());
        currentMusic.postValue(music);

        /*

        lyricRepository.getLyricList(music, new Repository.RepoListCallback<List<Lyric>>() {
            @Override
            public void success(List<Lyric> lyrics) {
                music.setMusicLyricList(lyrics);
                currentMusic.postValue(music);

            }

            @Override
            public void fail(NetworkState networkState) {

            }
        });

         */
    }

    private void loadPlaylistCollection() {
        playlistCollection = new MutableLiveData<>();
        playlistCollection.setValue(playlistModel.getPlaylistCollection());

        // TODO get Last played playlist name
        currentPlaylistName = playlistModel.getPlaylistCollection().getDefaultPlaylist().getPlaylistName();


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

    // TODO Open interface
    public void play(Context context) {
        if (currentMusic.getValue() != null &&
                !(PlayManager.getInstance().isPrepared() || PlayManager.getInstance().isPlaying())) {
            MusicPlayer mediaPlayer = new ExoPlayerWrapper(currentMusic.getValue().getMusicUrl(), 1f, context);
            PlayManager.getInstance().setPlayer(mediaPlayer);
            PlayManager.getInstance().addMusicChangedListener(this::getNextMusic);
            PlayManager.getInstance().startPlayer();
        }
    }

    public void getNextMusic() {
        if (playlistCollection.getValue() != null) {
            Playlist currentPlaylist = playlistCollection.getValue().getPlaylist(currentPlaylistName);
            playlistModel.setLastPlayed(currentPlaylist);
            Music nextMusic = currentPlaylist.next().getMusic();
            loadLyric(nextMusic);
            currentMusic.postValue(nextMusic);
        }
    }

    public void getPrevMusic() {
        if (PlayManager.getInstance().getCurrentPosition() < 3000) {
            if (playlistCollection.getValue() != null) {
                Playlist currentPlaylist = playlistCollection.getValue()
                        .getPlaylist(currentPlaylistName);
                playlistModel.setLastPlayed(currentPlaylist);
                Music prevMusic = currentPlaylist.prev().getMusic();
                loadLyric(prevMusic);
                currentMusic.postValue(prevMusic);
            }
        } else {
            PlayManager.getInstance().resetPlayer();
            PlayManager.getInstance().startPlayer();
        }
    }
}
