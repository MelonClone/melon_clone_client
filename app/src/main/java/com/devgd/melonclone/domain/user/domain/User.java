package com.devgd.melonclone.domain.user.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User implements Domain {
    String email;
    String nickname;
    String password;
}
