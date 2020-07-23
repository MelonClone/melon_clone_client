package com.devgd.melonclone.utils.jwt;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;

public class JwtParser {

    public static boolean verify(String jwt) {
        return Jwts.parserBuilder().build().isSigned(jwt);
    }
    public static Jwt decoded(String jwt) {
        try {
            String[] split = jwt.split("\\.");

            Log.d("JWT_DECODED", "Header: " + getJsonString(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJsonString(split[1]));
            Jwt.Header header = getHeader(getJsonString(split[0]));
            List<Jwt.Claim> claims = new ArrayList<>();
            claims.add(getBody(getJsonString(split[1])));

            return new Jwt(header, claims);
        } catch (UnsupportedEncodingException | JSONException e) {
            //Error
        }

        return null;
    }

    private static String getJsonString(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    private static JSONObject getJson(String jsonString) throws JSONException{
        JSONObject jsonObject = new JSONObject(jsonString);

        return jsonObject;
    }

    private static Jwt.Header getHeader(String jsonString) throws JSONException {
        JSONObject headerJson = getJson(jsonString);
        Jwt.Header header = new Jwt.Header(headerJson.getString("type"), headerJson.getString("alg"));
        return header;
    }

    private static Jwt.Claim getBody(String jsonString) throws JSONException {
        JSONObject bodyJson = getJson(jsonString);
        HashMap bodyMap = new HashMap<String, Object>();
        Jwt.PublicClaim publicClaim = new Jwt.PublicClaim();

        return publicClaim;
    }
}
