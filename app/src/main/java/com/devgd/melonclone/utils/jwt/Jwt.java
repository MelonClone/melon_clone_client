package com.devgd.melonclone.utils.jwt;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jwt {
    Header header;
    RegisteredClaim registeredClaim;
    PublicClaim publicClaim;
    PrivateClaim privateClaim;

    @Getter
    @AllArgsConstructor
    public static class Header {
        String typ;
        String alg;
    }

    interface Claim {

    }

    @Getter
    @Setter
    public static class RegisteredClaim implements Claim {
        String iss;
        String sub;
        String aud;
        String exp;
        String nbf;
        String iat;
        String jti;
    }

    @Getter
    @AllArgsConstructor
    public static class PublicClaim implements Claim {
        JSONObject claim;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PrivateClaim implements Claim {
        JSONObject claim;
    }
}
