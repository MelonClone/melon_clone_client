package com.devgd.melonclone.global.model.viewmodel;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ListViewModel<T> {
    LiveData<List<T>> getList();
}
