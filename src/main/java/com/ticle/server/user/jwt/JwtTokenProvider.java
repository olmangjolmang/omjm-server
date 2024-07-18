package com.ticle.server.user.jwt;


import com.ticle.server.user.redis.RedisDao;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.service.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

import static com.ticle.server.user.service.UserService.REFRESH_TOKEN_TIME;
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



        Date accessTokenExpiresIn = new Date(now+86400000);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth",authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now+86400000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

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
                throw new RuntimeException("로그아웃 했지롱~~");
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(SecurityException | MalformedJwtException e){
            log.info("Invalid JWT Token",e);
        }catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
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
