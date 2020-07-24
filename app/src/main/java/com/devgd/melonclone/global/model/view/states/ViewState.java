package com.devgd.melonclone.global.model.view.states;

import androidx.appcompat.app.AppCompatActivity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ViewState {
    private StateCode stateCode;
    private Class<? extends AppCompatActivity> msg;
    private Object arg;

}
