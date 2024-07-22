package com.ticle.server.user.jwt;

public class ExpireTime {

    public static final long ACCESS_TOKEN_EXPIRE_TIME = 6 * 60 * 60 * 1000L;               // 액세스 토큰 6시간
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;
}
