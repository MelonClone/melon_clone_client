package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.user.domain.User;

public interface PlayerDataSource {
    void insertOrUpdateUser(User user);
    User getUserInfo();
}
