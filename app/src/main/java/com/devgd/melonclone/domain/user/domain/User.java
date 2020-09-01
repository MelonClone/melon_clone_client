package com.devgd.melonclone.domain.user.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.watermelon.framework.global.model.domain.Domain;
import org.watermelon.framework.global.model.view.states.LoginState;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "user_table")
@Getter
public class User implements Domain, LoginState.Login, Serializable {

    @PrimaryKey
    @ColumnInfo(name = "user_id")
    @NonNull
    @Setter
    private Integer userId;

    @ColumnInfo(name = "email")
    @NonNull
    @lombok.NonNull
    private String email;

    @ColumnInfo(name = "nickname")
    @NonNull
    @Setter
    private String nickname;

    @lombok.NonNull
    private String password;

    @ColumnInfo(name = "token")
    @NonNull
    @Setter
    private String jwtToken;

    @Ignore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
