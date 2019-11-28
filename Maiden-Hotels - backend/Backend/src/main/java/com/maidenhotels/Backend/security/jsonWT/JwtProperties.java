package com.maidenhotels.Backend.security.jsonWT;

public class JwtProperties {

    public static final String SECRET = "What's the meaning of life?"; //Random secret
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
