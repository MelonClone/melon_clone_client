package com.devgd.melonclone.global.initiator;

import com.devgd.melonclone.global.consts.Constants;

import org.watermelon.framework.global.model.application.SharedPreferenceInitiator;

public class MelonSPInitiator extends SharedPreferenceInitiator {

    @Override
    public String getSharedPreferenceName() {
        return Constants.SP_NAME;
    }

}
