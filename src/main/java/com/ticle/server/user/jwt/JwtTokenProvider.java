package com.ticle.server.user.jwt;

import com.ticle.server.user.exception.TokenNotValidationException;
import com.ticle.server.user.redis.RedisDao;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final RedisDao redisDao;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // secret값을 가져와서 key에 저장
    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secretKey, RedisDao redisDao, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.redisDao = redisDao;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // user 정보를 가지고 토큰을 생성하는 메소드
    public JwtTokenResponse generateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now+ExpireTime.ACCESS_TOKEN_EXPIRE_TIME);

        String accessTokenId = UUID.randomUUID().toString();
        String refreshTokenId = UUID.randomUUID().toString();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth",authorities)
                .setExpiration(accessTokenExpiresIn)
                .setIssuedAt(new Date(now))
                .setId(UUID.randomUUID().toString())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now+ExpireTime.REFRESH_TOKEN_EXPIRE_TIME))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(now))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

        log.info("Access Token ID: {}", accessTokenId);
        log.info("Refresh Token ID: {}", refreshTokenId);
        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);

        return JwtTokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    //jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메소드
    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }
//        Long userId = Long.parseLong(claims.getSubject());
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(toList());

        CustomUserDetails principal = new CustomUserDetails(Long.parseLong(claims.getSubject()), "",authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    //토큰 정보를 검증하는 메소드
    public boolean validateToken(String token){
        try{
            if (redisDao.hasKey(token)){
                throw new RuntimeException("이미 로그아웃 된 계정입니다.");
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(SecurityException | MalformedJwtException e){
            log.info("Invalid JWT Token",e);
            throw new TokenNotValidationException("잘못된 JWT 서명입니다",e);
        }catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new TokenNotValidationException("만료된 JWT 토큰입니다",e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new TokenNotValidationException("지원되지 않는 JWT 토큰입니다",e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new TokenNotValidationException("JWT 토큰이 잘못되었습니다",e);
        }
    }
    //주어진 Access token을 복호화하고, 만료된 토큰인 경우에도 Claims 반환
    public Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public Long getExpiration(String accessToken){
        //에세스 토큰 만료시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody()
                .getExpiration();
        //현재시간
        long now = new Date().getTime();
        return (expiration.getTime()-now);
    }

    // Request Header에서 토큰 정보 추출
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
