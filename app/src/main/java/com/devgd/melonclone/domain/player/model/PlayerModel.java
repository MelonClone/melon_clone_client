package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.utils.store.PlayerSample;

public class PlayerModel {
    private Player player;

    private static PlayerModel instance;

    public static PlayerModel getInstance() {
        if (instance == null) {
            instance = new PlayerModel();
            instance.init();
        }
        return instance;
    }


    public void init() {

    }

    public Player getPlayer() {
        // TODO getting player from DB
        return PlayerSample.getSample();
    }
}
