package com.devgd.melonclone.global.model.dao;

import android.os.AsyncTask;

import com.devgd.melonclone.global.model.domain.Domain;

public abstract class DaoCallback {
    public static void execute(DaoCallback daoCallback) {
        new AsyncTask<Void, Void, Domain>() {
            @Override
            protected Domain doInBackground(Void... voids) {
                return daoCallback.execute();
            }

            @Override
            protected void onPostExecute(Domain d) {
                daoCallback.postExecute(d);
            }
        }.execute();
    }
    public abstract Domain execute();
    public abstract void postExecute(Domain d);
}
