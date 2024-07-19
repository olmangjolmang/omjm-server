package com.ticle.server.user.service;

import com.ticle.server.user.dto.request.LoginRequest;
import com.ticle.server.user.redis.CacheNames;
import com.ticle.server.user.redis.RedisDao;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.request.JoinRequest;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.dto.response.UserResponse;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    public static final long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24 * 7L;// 7일

    @Cacheable(cacheNames = CacheNames.LOGINUSER, key = "#p0.email()", unless = "#result== null")
    @Transactional
    public JwtTokenResponse signIn(LoginRequest loginRequest){
        String email = loginRequest.email();
        String password = loginRequest.password();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtTokenResponse jwtTokenResponse = jwtTokenProvider.generateToken(authentication);
        redisDao.setRefreshToken(email, jwtTokenResponse.getRefreshToken(), REFRESH_TOKEN_TIME);

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

    @CacheEvict(cacheNames = CacheNames.USERBYEMAIL, key = "#p1")
    @Transactional
    public ResponseEntity logout(CustomUserDetails customUserDetails, HttpServletRequest request) {
        String email = "";
        String accessToken = jwtTokenProvider.resolveToken(request);
        if(userRepository.findById(customUserDetails.getUserId()).isPresent()){
            email = userRepository.findById(customUserDetails.getUserId()).get().getEmail();
        }
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

    public JwtTokenResponse reissueAtk(CustomUserDetails customUserDetails,String reToken) {
        String email = "";
        String password = "";

        Optional<User> user = userRepository.findById(customUserDetails.getUserId());
        if(user.isPresent()){
            email = user.get().getEmail();
            password = user.get().getPassword();
        }
        // 레디스 저장된 리프레쉬토큰값을 가져와서 입력된 reToken 같은지 유무 확인
        if (!redisDao.getRefreshToken(email).equals(reToken)) {
            throw new RuntimeException("Refresh");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String refreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();
        redisDao.setRefreshToken(email, refreshToken, REFRESH_TOKEN_TIME);
        return new JwtTokenResponse("Bearer",accessToken, refreshToken);
    }

}
