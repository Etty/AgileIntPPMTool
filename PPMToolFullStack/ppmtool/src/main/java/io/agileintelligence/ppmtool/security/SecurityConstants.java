package io.agileintelligence.ppmtool.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "MIGJAoGBAI5yJqLfDc2WnjTgOsbWHm66nILKeN/meFgZRVp5OXLPWBwqeFr66CyW" +
            "m+ZVpufgtKhD++wBWUeKOe5dBdnf6FzPmTOX5BFZWCL9pGN10cqgfTUDLuGQP7R1" +
            "AFecEYzKp6xOv1LKGMIuoez+NrXDirvL8xoWFizIxs9D1OmM9ZDPAgMBAAE=";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 30_000; //30_000 = 30 sec

}
