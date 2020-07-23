package com.devgd.melonclone.domain.user.viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.view.activity.PlayerActivity;
import com.devgd.melonclone.domain.user.domain.AuthState;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.LocalUserDataSource;
import com.devgd.melonclone.domain.user.model.UserDataSource;
import com.devgd.melonclone.domain.user.model.UserRepository;
import com.devgd.melonclone.global.consts.ViewState;
import com.devgd.melonclone.global.model.domain.Message;
import com.devgd.melonclone.global.model.repository.NetworkState;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.jwt.Jwt;
import com.devgd.melonclone.utils.jwt.JwtParser;
import com.devgd.melonclone.utils.Verifier;
import com.devgd.melonclone.utils.db.DBHelper;

import io.jsonwebtoken.Jwts;

import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.UNEXPECTED_ERROR;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.USER_INPUT_WRONG;
import static com.devgd.melonclone.global.consts.StateCode.ACTIVITY_CHANGE;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<AuthState> loginInfoText;

    // TODO DI
    private UserRepository userRepository;
    private UserDataSource userDataSource;

    @Override
    protected void init() {
        loginInfoText = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
        userDataSource = new LocalUserDataSource(DBHelper.getInstance().getDB().userDao());
    }

    // 로그인 = login request, user db insert, view state change
    public void loginUser(String userEmail, String userPassword) {
        if (Verifier.emailVerify(userEmail)) {
            loginUser(new User(userEmail, userPassword));
        } else {
            loginInfoText.postValue(new AuthState(USER_INPUT_WRONG));
        }
    }

    public void loginUser(User user) {
        userRepository.loginUser(user, new Repository.RepoCallback<Message>() {

            @Override
            public void success(Message msg) {
                user.setJwtToken(msg.getMsg());
                Jwt jwt = JwtParser.decoded(user.getJwtToken());
                user.setNickname(jwt.getPublicClaim().getClaim().get("info"));

                userDataSource.insertOrUpdateUser(user, new Handler() {
                    @Override
                    public void handleMessage(@NonNull android.os.Message msg) {
                        state.postValue(new ViewState(ACTIVITY_CHANGE, PlayerActivity.class, null));
                    }
                });

            }

            @Override
            public void fail(NetworkState networkState) {
                loginInfoText.postValue(new AuthState(UNEXPECTED_ERROR));
            }
        });
    }

    public LiveData<AuthState> getLoginInfo() {
        return loginInfoText;
    }
}
