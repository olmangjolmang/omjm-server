package com.ticle.server.user.service;

import com.ticle.server.user.redis.CacheNames;
import com.ticle.server.user.redis.RedisDao;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.request.JoinRequest;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.dto.response.UserResponse;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisDao redisDao;

//    @Cacheable(cacheNames = CacheNames.LOGINUSER, key = "'login'+#p0", unless = "#result== null")
    @Transactional
    public JwtTokenResponse signIn(String email, String password){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtTokenResponse jwtTokenResponse = jwtTokenProvider.generateToken(authentication);

        return jwtTokenResponse;
    }

    @Transactional
    public UserResponse signUp(JoinRequest joinRequest){
        if(userRepository.existsByEmail(joinRequest.getEmail())){
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return UserResponse.toDto(userRepository.save(joinRequest.toEntity(encodedPassword,roles)));
    }

//    @CacheEvict(cacheNames = CacheNames.USERBYEMAIL, key = "'login'+#p1")
    @Transactional
    public ResponseEntity logout(String accessToken, String email) {

        // 레디스에 accessToken 사용못하도록 등록
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisDao.setBlackList(accessToken, "logout", expiration);
        if (redisDao.hasKey(email)) {
            redisDao.deleteRefreshToken(email);
        } else {
            throw new IllegalArgumentException("이미 로그아웃한 유저입니다.");
        }
        return ResponseEntity.ok("로그아웃 완료");
    }

}
