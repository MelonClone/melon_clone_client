package com.devgd.melonclone.domain.user.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.user.domain.AuthState;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.UserRepository;
import com.devgd.melonclone.global.model.viewmodel.MelonCloneBaseViewModel;

import org.watermelon.framework.global.model.domain.Message;
import org.watermelon.framework.global.model.repository.Repository;
import org.watermelon.framework.global.model.view.states.NetworkState;
import org.watermelon.framework.global.model.view.states.ViewState;
import org.watermelon.framework.utils.StringVerifier;

import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.NO_ERROR;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.PASSWORD_NOT_MATCH;
import static com.devgd.melonclone.domain.user.domain.AuthErrorCode.USER_INPUT_WRONG;
import static org.watermelon.framework.global.model.view.states.StateCode.AUTO_LOGIN;

public class RegisterViewModel extends MelonCloneBaseViewModel {

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
        if (StringVerifier.emailVerify(userEmail) && StringVerifier.textVerify(userNickname)) {
            registerInfoText.postValue(new AuthState(NO_ERROR));
            User user = new User(userEmail, userNickname, userPassword);
            userRepository.registerUser(user, new Repository.RepoCallback<Message>() {

                @Override
                public void success(Message message) {
                    getViewState().postValue(new ViewState(AUTO_LOGIN, null, user));
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
        if (!StringVerifier.passwordVerify(userPassword, userPasswordRetype)) {
            registerInfoText.postValue(new AuthState(PASSWORD_NOT_MATCH));
        } else {
            registerInfoText.postValue(new AuthState(NO_ERROR));
        }
    }

    public LiveData<AuthState> getRegisterInfo() {
        return registerInfoText;
    }

}
