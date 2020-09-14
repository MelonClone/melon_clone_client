package com.devgd.melonclone;


import com.devgd.melonclone.global.initiator.MelonDatabaseInitiator;
import com.devgd.melonclone.global.initiator.MelonFontInitiator;
import com.devgd.melonclone.global.initiator.MelonNetworkInitiator;
import com.devgd.melonclone.global.initiator.MelonSPInitiator;

import org.watermelon.framework.global.model.application.BaseApplication;
import org.watermelon.framework.global.model.application.Initializer;


public class MelonApplication extends BaseApplication {

    public void initiate() {
        Initializer.addInitiator(new MelonDatabaseInitiator());
        Initializer.addInitiator(new MelonSPInitiator());
        Initializer.addInitiator(new MelonFontInitiator());
        Initializer.addInitiator(new MelonNetworkInitiator());
    }
}
