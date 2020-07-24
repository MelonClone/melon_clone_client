package com.devgd.melonclone.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class StringVerifier {

    public static boolean passwordVerify(String a, String b) {
        return a.trim().equals(b.trim());
    }

    public static boolean emailVerify(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean textVerify(String a) {
        String regex = "^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅢ]*$";
        return a.matches(regex);
    }
}
