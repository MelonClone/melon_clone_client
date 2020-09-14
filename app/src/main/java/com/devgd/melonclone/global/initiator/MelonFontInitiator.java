package com.devgd.melonclone.global.initiator;

import com.devgd.melonclone.global.consts.Constants;

import org.watermelon.framework.global.model.application.FontInitiator;

public class MelonFontInitiator extends FontInitiator {

    @Override
    public String getFontLocation() {
        return Constants.FONT_LOCATION;
    }

}
