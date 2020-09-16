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
import com.devgd.melonclone.global.db.version.AppDatabase;
import com.devgd.melonclone.global.model.viewmodel.MelonCloneBaseViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.watermelon.framework.global.model.domain.Message;
import org.watermelon.framework.global.model.repository.Repository;
import org.watermelon.framework.global.model.view.states.NetworkState;
import org.watermelon.framework.global.model.view.states.ViewState;
import org.watermelon.framework.utils.StringVerifier;
import org.watermelon.framework.utils.db.DBHelper;
import org.watermelon.framework.utils.jwt.Jwt;
import org.watermelon.framework.utils.jwt.JwtParser;

import java.util.Objects;

import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.UNEXPECTED_ERROR;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.USER_INPUT_WRONG;
import static org.watermelon.framework.global.model.view.states.StateCode.ACTIVITY_CHANGE;

public class LoginViewModel extends MelonCloneBaseViewModel {

    private MutableLiveData<AuthState> loginInfoText;

    // TODO DI
    private UserRepository userRepository;
    private UserDataSource userDataSource;

    @Override
    protected void init() {
        loginInfoText = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
        userDataSource = new LocalUserDataSource(((AppDatabase) DBHelper.getInstance().getDB()).userDao());
    }

    public void loggedIn() {
        getViewState().postValue(new ViewState(ACTIVITY_CHANGE, ProfileActivity.class, null));
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

                userDataSource.insertOrUpdateUser(user, message -> {
                    getViewState().postValue(new ViewState(ACTIVITY_CHANGE, PlayerActivity.class, null));
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
