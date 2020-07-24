package com.devgd.melonclone.domain.user.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.user.domain.AuthState;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.LocalUserDataSource;
import com.devgd.melonclone.domain.user.model.UserDataSource;
import com.devgd.melonclone.domain.user.model.UserRepository;
import com.devgd.melonclone.global.db.DatabaseCallback;
import com.devgd.melonclone.global.model.view.states.LoginState;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.global.model.domain.Message;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.Verifier;
import com.devgd.melonclone.utils.db.DBHelper;
import com.devgd.melonclone.utils.jwt.JwtParser;

import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.NO_ERROR;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.PASSWORD_NOT_MATCH;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.USER_INPUT_WRONG;
import static com.devgd.melonclone.global.model.view.states.StateCode.AUTO_LOGIN;

public class RegisterViewModel extends BaseViewModel {

    private MutableLiveData<AuthState> registerInfoText;

    // TODO DI
    private UserRepository userRepository;

    @Override
    protected void init() {
        registerInfoText = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
    }

    // 회원 등록 - verify, register request, login start
    public void registerUser(String userEmail, String userNickname, String userPassword) {
        if (Verifier.emailVerify(userEmail) && Verifier.textVerify(userNickname)) {
            registerInfoText.postValue(new AuthState(NO_ERROR));
            User user = new User(userEmail, userNickname, userPassword);
            userRepository.registerUser(user, new Repository.RepoCallback<Message>() {

                @Override
                public void success(Message message) {
                    state.postValue(new ViewState(AUTO_LOGIN, null, user));
                }

                @Override
                public void fail(NetworkState networkState) {
                }
            });
        } else {
            registerInfoText.postValue(new AuthState(USER_INPUT_WRONG));
        }
    }

    // Password match
    public void checkRegistryInfo(String userPassword, String userPasswordRetype) {
        if (!Verifier.passwordVerify(userPassword, userPasswordRetype)) {
            registerInfoText.postValue(new AuthState(PASSWORD_NOT_MATCH));
        } else {
            registerInfoText.postValue(new AuthState(NO_ERROR));
        }
    }

    public LiveData<AuthState> getRegisterInfo() {
        return registerInfoText;
    }

}
