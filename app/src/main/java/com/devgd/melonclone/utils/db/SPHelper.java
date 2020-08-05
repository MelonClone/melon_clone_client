package com.devgd.melonclone.utils.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import static com.devgd.melonclone.global.consts.Constants.SP_NAME;

public class SPHelper {

    private static SPHelper INSTANCE;
    private Context mContext;

    private SPHelper() {
    }

    public static SPHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (SPHelper.class) {
                INSTANCE = new SPHelper();
            }
        }

        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public void set(Map<String, String> map) {
        try {
            if (mContext == null) throw new SPNotInitializedException();
            SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, 0);
            SharedPreferences.Editor editor = sp.edit();

            for (String key : map.keySet()) {
                editor.putString(key, (String) map.get(key));
            }

            editor.apply();
        } catch(SPNotInitializedException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String value) {
        try {
            if (mContext == null) throw new SPNotInitializedException();
            SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, 0);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString(key, value);

            editor.apply();
        } catch(SPNotInitializedException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getAll() {
        HashMap<String, String> map = new HashMap<>();
        try {
            if (mContext == null) throw new SPNotInitializedException();
            SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, 0);

            Map<String, ?> spMap = sp.getAll();
            if (spMap.isEmpty()) {
                return map;
            }

            for (String key : spMap.keySet()) {
                map.put(key, sp.getString(key, ""));
            }
        } catch(SPNotInitializedException e) {
            e.printStackTrace();
        }

        return map;
    }

    public String getValue(String key) {
        String value = "";
        try {
            if (mContext == null) throw new SPNotInitializedException();
            SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, 0);
            value = sp.getString(key, "");
        } catch(SPNotInitializedException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void clear() {
        try {
            if (mContext == null) throw new SPNotInitializedException();
            SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
        } catch(SPNotInitializedException e) {
            e.printStackTrace();
        }
    }
}
