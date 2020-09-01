package com.devgd.melonclone.global.model.viewmodel;

import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public abstract class MelonCloneBaseViewModel extends BaseViewModel {

    // TODO DI
    private UserDataSource userDataSource;

    private DatabaseCallback userDataSourceCallback = new DatabaseCallback() {
        @Override
        public void callback(Message msg) {
            if (msg.arg1 == UserDataSource.GET_USER) {
                User user = (User) msg.obj;
                if (user == null) loginState.setValue(new LoginState<User>(false, null));
                else if (!JwtParser.verify(user.getJwtToken()))
                    loginState.setValue(new LoginState<User>(false, null));

                loginState.setValue(new LoginState<>(true, user));
            }
        }
    };

    public MelonCloneBaseViewModel() {
        beforeInit();
        init();
    }

    protected void beforeInit() {
        state = new MutableLiveData<>();
        loginState = new MutableLiveData<>();

        userDataSource = new LocalUserDataSource(((AppDatabase) DBHelper.getInstance().getDB()).userDao(), userDataSourceCallback);
    }

    abstract protected void init();

    // 로그인 확인
    public void checkLogin() {
        userDataSource.getUserInfo();
    }

    // Binding with view (login state managing)
    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    // Binding with view (views state managing)
    public LiveData<ViewState> getViewState() {
        return state;
    }
}
