package com.devgd.melonclone.global.model.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = viewContainerInit(inflater, container);
        layoutInit(view);
        viewInit();
        viewModelInit();
        listenerInit();
        return view;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    abstract protected View viewContainerInit(LayoutInflater inflater, ViewGroup container);

    // If need parents viewModel in fragment
    abstract public void setParentViewModel(ViewModel... viewModels);
    abstract protected void layoutInit(View view);
    abstract protected void viewInit();
    abstract protected void viewModelInit();
    abstract protected void listenerInit();
}
