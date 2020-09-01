package com.devgd.melonclone.global.model.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Observer;

import com.devgd.melonclone.domain.user.domain.User;

import org.watermelon.framework.global.model.view.activity.BaseActivity;
import org.watermelon.framework.global.model.view.states.ViewState;

public abstract class MelonCloneBaseActivity extends BaseActivity {

    // -- Functional methods

    protected void autoLogin(User user) {

    }

    protected Observer<ViewState> getStateObserver(Context context) {
        return (Observer<ViewState>) viewState -> {
            switch (viewState.getStateCode()) {
                case AUTO_LOGIN:
                    autoLogin((User) viewState.getArg());
                    break;
                case ACTIVITY_CHANGE:
                    Class nextClass = (Class) viewState.getMsg();
                    Intent intent = new Intent(context, nextClass);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setFlags(Intent.Clea)
                    startActivity(intent);
                    break;

            }
        };
    }
}
