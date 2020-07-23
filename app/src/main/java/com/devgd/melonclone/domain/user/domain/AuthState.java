package com.devgd.melonclone.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthState {
    private final AuthErrorCode authErrorCode;
    @Setter
    private Object msg;
}
