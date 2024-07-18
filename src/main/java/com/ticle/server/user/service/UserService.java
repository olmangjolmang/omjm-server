package com.ticle.server.user.service;

import com.ticle.server.global.util.RedisUtil;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.request.JoinRequest;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.dto.response.UserResponse;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Cacheable(cacheNames = "LOGIN_USER", key = "#email", unless = "#result== null")
    @Transactional
    public JwtTokenResponse signIn(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtTokenResponse jwtTokenResponse = jwtTokenProvider.generateToken(authentication);

        log.info("hello" + authentication.getDetails());
        log.info("hello22" + authentication.getName());


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
    @CacheEvict(cacheNames = "USERBYID", key = "'login'+#p1")
    @Transactional
    public ResponseEntity logout(String accessToken, Long id) {
        // 레디스에 accessToken 사용못하도록 등록
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisUtil.setBlackList(accessToken, "logout", expiration);
        if (redisUtil.hasKey(id)) {
            redisUtil.delete(id);
        } else {
            throw new IllegalArgumentException("이미 로그아웃한 유저입니다.");
        }
        return ResponseEntity.ok("로그아웃 완료");
    }


    public User getLoginUserByEmail(String email) {
        if (email == null)
            return null;

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty())
            return null;

        return optionalUser.get();
    }

}
