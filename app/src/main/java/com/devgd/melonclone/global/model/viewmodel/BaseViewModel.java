package com.devgd.melonclone.global.model.viewmodel;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.LocalUserDataSource;
import com.devgd.melonclone.domain.user.model.UserDataSource;
import com.devgd.melonclone.global.db.DatabaseCallback;
import com.devgd.melonclone.global.model.view.states.LoginState;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.utils.db.DBHelper;
import com.devgd.melonclone.utils.jwt.JwtParser;

public abstract class BaseViewModel extends ViewModel {
    protected MutableLiveData<ViewState> state;
    protected MutableLiveData<LoginState> loginState;

    // TODO DI
    private UserDataSource userDataSource;

    private DatabaseCallback userDataSourceCallback = new DatabaseCallback() {
        @Override
        public void callback(Message msg) {
            if (msg.arg1 == UserDataSource.GET_USER) {
                User user = (User) msg.obj;
                if (user == null) loginState.setValue(new LoginState(false, null));
                else if (!JwtParser.verify(user.getJwtToken()))
                    loginState.setValue(new LoginState(false, null));

                loginState.setValue(new LoginState(true, user));
            }
        }
    };

    public BaseViewModel() {
        beforeInit();
        init();
    }

    protected void beforeInit() {
        state = new MutableLiveData<>();
        loginState = new MutableLiveData<>();

        userDataSource = new LocalUserDataSource(DBHelper.getInstance().getDB().userDao(), userDataSourceCallback);
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
