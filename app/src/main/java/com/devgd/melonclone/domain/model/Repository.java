package com.devgd.melonclone.domain.model;

import java.util.List;

public interface Repository {
    interface RepoCallback<D extends Domain> {

        void success(D domain);

        void fail();
    }

    interface RepoListCallback<L extends List> {

        void success(L domain);

        void fail();
    }
}
