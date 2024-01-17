package com.fageniuscode.epargneplus.api.constants;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Le jeton ne peut pas être vérifié";
    public static final String GET_ARRAYS_LLC = "FAGENIUS, Corporation";
    public static final String GET_ARRAYS_ADMINISTRATION = "Système d'épargne électronique";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous connecter pour accéder à cette page";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'avez pas la permission d'accéder à cette page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/user/login", "/user/register" };
    //public static final String[] PUBLIC_URLS = { "**" };
}
