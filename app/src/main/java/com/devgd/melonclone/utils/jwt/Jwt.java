package com.devgd.melonclone.utils.jwt;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
    public class RegisteredClaim implements Claim {
        String iss;
        String sub;
        String aud;
        String exp;
        String nbf;
        String iat;
        String jti;
    }

    @Getter
    public static class PublicClaim implements Claim {
        HashMap<String, Object> claim;
    }

    @Getter
    public class PrivateClaim implements Claim {
        HashMap<String, Object> claim;
    }
}
