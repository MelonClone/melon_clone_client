package com.devgd.melonclone.domain.model;

public interface Dao {
    interface DaoCallback<D extends Domain> {

        void success(D domain);

        void fail();
    }

}
