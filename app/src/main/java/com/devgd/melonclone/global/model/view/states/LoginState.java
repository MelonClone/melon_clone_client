package com.devgd.melonclone.global.model.view.states;

import com.devgd.melonclone.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginState {
    boolean isLogin;
    User user;
}
