package com.devgd.melonclone.domain.user.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.view.activity.PlayerActivity;
import com.devgd.melonclone.domain.user.domain.AuthState;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.LocalUserDataSource;
import com.devgd.melonclone.domain.user.model.UserDataSource;
import com.devgd.melonclone.domain.user.model.UserRepository;
import com.devgd.melonclone.domain.user.view.activity.ProfileActivity;
import com.devgd.melonclone.global.db.DatabaseCallback;
import com.devgd.melonclone.global.model.domain.Message;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.StringVerifier;
import com.devgd.melonclone.utils.db.DBHelper;
import com.devgd.melonclone.utils.jwt.Jwt;
import com.devgd.melonclone.utils.jwt.JwtParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.UNEXPECTED_ERROR;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.USER_INPUT_WRONG;
import static com.devgd.melonclone.global.model.view.states.StateCode.ACTIVITY_CHANGE;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<AuthState> loginInfoText;

    // TODO DI
    private UserRepository userRepository;
    private UserDataSource userDataSource;

    private DatabaseCallback userDataSourceCallback = msg -> {
        if (msg.arg1 == UserDataSource.INSERT_USER)
            state.postValue(new ViewState(ACTIVITY_CHANGE, PlayerActivity.class, null));
    };

    @Override
    protected void init() {
        loginInfoText = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
        userDataSource = new LocalUserDataSource(DBHelper.getInstance().getDB().userDao(), userDataSourceCallback);
    }

    public void loggedIn() {
        state.postValue(new ViewState(ACTIVITY_CHANGE, ProfileActivity.class, null));
    }

    // 로그인 = raw String email & password
    public void loginUser(String userEmail, String userPassword) {
        if (StringVerifier.emailVerify(userEmail)) {
            loginUser(new User(userEmail, userPassword));
        } else {
            loginInfoText.postValue(new AuthState(USER_INPUT_WRONG));
        }
    }

    // 로그인 = login request, user db insert, view state change
    public void loginUser(User user) {
        userRepository.loginUser(user, new Repository.RepoCallback<Message>() {

            @Override
            public void success(Message msg) {
                user.setJwtToken(msg.getMsg());
                Jwt jwt = JwtParser.decoded(user.getJwtToken());
                if (jwt.getPublicClaim() != null) {
                    try {
                        JSONObject userInfo = Objects.requireNonNull(jwt.getPublicClaim()
                                .getClaim()
                                .getJSONObject("info"));
                        user.setNickname(userInfo.getString("user_name"));
                        user.setUserId(userInfo.getInt("user_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                userDataSource.insertOrUpdateUser(user);

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
