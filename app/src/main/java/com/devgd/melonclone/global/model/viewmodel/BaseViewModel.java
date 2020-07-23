package com.devgd.melonclone.global.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.global.consts.ViewState;

public abstract class BaseViewModel extends ViewModel {
    protected MutableLiveData<ViewState> state;

    public BaseViewModel() {
        beforeInit();
        init();
    }

    protected void beforeInit() {
        state = new MutableLiveData<>();
    }

    abstract protected void init();

    // Binding with view (views state managing)
    public LiveData<ViewState> getViewState() {
        return state;
    }
}
