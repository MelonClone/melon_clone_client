package com.devgd.melonclone.global.model.viewmodel;

import androidx.lifecycle.LiveData;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.LocalUserDataSource;
import com.devgd.melonclone.domain.user.model.UserDataSource;
import com.devgd.melonclone.global.db.version.AppDatabase;

import org.watermelon.framework.global.db.DatabaseCallback;
import org.watermelon.framework.global.model.view.states.LoginState;
import org.watermelon.framework.global.model.view.states.ViewState;
import org.watermelon.framework.global.model.viewmodel.BaseViewModel;
import org.watermelon.framework.utils.db.DBHelper;
import org.watermelon.framework.utils.jwt.JwtParser;

public abstract class MelonCloneBaseViewModel extends BaseViewModel<User> {

    // TODO DI
    private UserDataSource userDataSource;
    private DatabaseCallback userCallback = message -> {
        User user = (User) message.obj;
        if (user == null) getLoginState().setValue(new LoginState<>(false, null));
        else if (!JwtParser.verify(user.getJwtToken()))
            getLoginState().setValue(new LoginState<>(false, null));

        getLoginState().setValue(new LoginState<>(true, user));
    };

    @Override
    protected void beforeInit() {
        super.beforeInit();

        userDataSource = new LocalUserDataSource(((AppDatabase) DBHelper.getInstance().getDB()).userDao());
    }

    @Override
    abstract protected void init();

    // 로그인 확인
    public void checkLogin() {
        userDataSource.getUserInfo(userCallback);
    }

    public LiveData<LoginState<User>> getObserveLoginState() {
        return getLoginState();
    }

    public LiveData<ViewState> getObserveViewSate() {
        return getViewState();
    }
}
