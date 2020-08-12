package com.devgd.melonclone.global.model.handler;

import android.graphics.drawable.Drawable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TabMenu {
    public boolean isSelected = false;
    public String tabName;
    public Drawable tabPointImage;
    public int tabItemCount;
}
