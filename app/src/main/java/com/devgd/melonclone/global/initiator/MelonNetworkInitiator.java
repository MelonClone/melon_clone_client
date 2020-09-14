package com.devgd.melonclone.global.initiator;

import android.content.Context;

import org.watermelon.framework.global.model.application.Initializable;
import org.watermelon.framework.utils.network.HttpManager;

import static com.devgd.melonclone.global.consts.Constants.API_PROTOCOL;
import static com.devgd.melonclone.global.consts.Constants.API_SERVER;

public class MelonNetworkInitiator implements Initializable {

    private boolean isInit = false;

    @Override
    public void init(Context context) {
        HttpManager.setBaseUrl(API_PROTOCOL + API_SERVER);
        isInit = true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Override
    public String getTag() {
        return "NETWORK";
    }
}
