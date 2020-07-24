package com.devgd.melonclone.utils.jwt;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

import io.jsonwebtoken.Jwts;

public class JwtParser {

    public static boolean verify(String jwt) {
        return Jwts.parserBuilder().build().isSigned(jwt);
    }
    public static Jwt decoded(String jwtString) {
        Jwt jwt = new Jwt();
        try {
            String[] split = jwtString.split("\\.");
            jwt.setHeader(getHeader(getJsonString(split[0])));
            jwt.setPublicClaim(getBody(getJsonString(split[1])));
        } catch (JSONException e) {
            //Error
        }

        return jwt;
    }

    private static String getJsonString(String strEncoded) {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private static JSONObject getJson(String jsonString) throws JSONException{
        return new JSONObject(jsonString);
    }

    private static Jwt.Header getHeader(String jsonString) throws JSONException {
        JSONObject headerJson = getJson(jsonString);
        return new Jwt.Header(headerJson.getString("typ"), headerJson.getString("alg"));
    }

    private static Jwt.PublicClaim getBody(String jsonString) throws JSONException {
        JSONObject bodyJson = getJson(jsonString);
        return new Jwt.PublicClaim(bodyJson);
    }
}
