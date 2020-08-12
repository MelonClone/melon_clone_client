package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.player.domain.Player;

public class PlayerSample {
    public static Player getSample() {
        return new Player(0, 3 * 60 * 1000, 0, false);
    }
}
