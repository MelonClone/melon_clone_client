package com.devgd.melonclone.domain.user.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.view.activity.PlayerActivity;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.UserRepository;
import com.devgd.melonclone.global.consts.ViewState;
import com.devgd.melonclone.global.model.domain.Domain;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.Verifier;

import static com.devgd.melonclone.global.consts.ErrorCode.NO_ERROR;
import static com.devgd.melonclone.global.consts.ErrorCode.PASSWORD_NOT_MATCH;
import static com.devgd.melonclone.global.consts.ErrorCode.USER_INPUT_WRONG;
import static com.devgd.melonclone.global.consts.StateCode.ACTIVITY_CHANGE;

public class UserViewModel extends BaseViewModel {

    private MutableLiveData<Integer> registerInfoText;

    // TODO DI or repository to singleton
    private UserRepository userRepository;

    @Override
    public void init() {
        userRepository = userRepository.getInstance();
        registerInfoText = new MutableLiveData<>();
    }

    public void registerUser(String userEmail, String userNickname, String userPassword) {
        if (Verifier.emailVerify(userEmail) && Verifier.textVerify(userNickname)) {
            registerInfoText.postValue(NO_ERROR);
            userRepository.registerUser(new User(userEmail, userNickname, userPassword), new Repository.RepoCallback<Domain>() {

                @Override
                public void success(Domain domain) {
                    state.postValue(new ViewState(ACTIVITY_CHANGE, PlayerActivity.class));
                }

                @Override
                public void fail() {

                }
            });
        } else {
            registerInfoText.postValue(USER_INPUT_WRONG);
        }
    }


    public void checkRegistryInfo(String userPassword, String userPasswordRetype) {
        if (!Verifier.passwordVerify(userPassword, userPasswordRetype)) {
            registerInfoText.postValue(PASSWORD_NOT_MATCH);
        } else {
            registerInfoText.postValue(NO_ERROR);
        }
    }

    public LiveData<Integer> getRegisterInfo() {
        return registerInfoText;
    }

}
