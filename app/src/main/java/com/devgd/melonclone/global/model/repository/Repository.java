package com.devgd.melonclone.global.model.repository;

import com.devgd.melonclone.global.model.domain.Domain;
import com.devgd.melonclone.global.model.view.states.NetworkState;

import java.util.List;

public interface Repository {
    interface RepoCallback<D extends Domain> {

        void success(D domain);

        void fail(NetworkState e);
    }

    interface RepoListCallback<L extends List> {

        void success(L domain);

        void fail(NetworkState e);
    }
}
