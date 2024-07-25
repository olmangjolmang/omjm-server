package com.ticle.server.user.service;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.request.CategoryUpdateRequest;
import com.ticle.server.user.dto.request.JoinRequest;
import com.ticle.server.user.dto.request.LoginRequest;
import com.ticle.server.user.dto.request.ProfileUpdateRequest;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.dto.response.UserInfoResponse;
import com.ticle.server.user.dto.response.UserResponse;
import com.ticle.server.user.exception.InvalidPasswordException;
import com.ticle.server.user.exception.UserNotFoundException;
import com.ticle.server.user.exception.UserNotLoginException;
import com.ticle.server.user.jwt.CustomUserDetails;
import com.ticle.server.user.jwt.ExpireTime;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.redis.RedisDao;
import com.ticle.server.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;
import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_LOGIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisDao redisDao;


    @Transactional
    public JwtTokenResponse signIn(LoginRequest loginRequest){
        String email = loginRequest.email();
        String password = loginRequest.password();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtTokenResponse jwtTokenResponse = jwtTokenProvider.generateToken(authentication);
        redisDao.setRefreshToken(email, jwtTokenResponse.getRefreshToken(), ExpireTime.REFRESH_TOKEN_EXPIRE_TIME);

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


    @Transactional
    public ResponseEntity logout(CustomUserDetails customUserDetails, HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        log.info("여기까진 실행함!");

        User user = userRepository.findById(customUserDetails.getUserId()).orElseThrow(()-> {
                log.info("useruseruser");
                return new UserNotFoundException(USER_NOT_FOUND);});
        String email = user.getEmail();
        // 레디스에 accessToken 사용못하도록 등록
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisDao.setBlackList(accessToken, "logout", expiration);
        if (redisDao.hasKey(email)) {
            redisDao.deleteRefreshToken(email);
        } else {
            throw new IllegalArgumentException("이미 로그아웃한 유저입니다.");
        }

        log.info("Logout successful for user with email: {}", email);

        return ResponseEntity.ok("로그아웃 완료");
    }

    @Transactional
    public JwtTokenResponse reissueAtk(CustomUserDetails customUserDetails,String refreshToken) {
        String email = "";
        String password = "";

        Optional<User> user = userRepository.findById(customUserDetails.getUserId());
        if(user.isPresent()){
            email = user.get().getEmail();
            password = user.get().getPassword();
        }
        // 레디스 저장된 리프레쉬토큰값을 가져와서 입력된 reToken 같은지 유무 확인
        if (!redisDao.getRefreshToken(email).equals(refreshToken)) {
            throw new RuntimeException("Refresh");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,null,user.get().getAuthorities());
        JwtTokenResponse jwtTokenResponse = jwtTokenProvider.generateToken(authenticationToken);
        String newAccessToken = jwtTokenResponse.getAccessToken();
        String newRefreshToken = jwtTokenResponse.getRefreshToken();

        redisDao.setRefreshToken(email, newRefreshToken, ExpireTime.REFRESH_TOKEN_EXPIRE_TIME);

        return new JwtTokenResponse("Bearer",newAccessToken, newRefreshToken);
    }

    @Transactional
    public void updateProfile(CustomUserDetails customUserDetails, ProfileUpdateRequest profileUpdateRequest){
        Long userId = customUserDetails.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("not found user"));

        profileUpdateRequest.nickName().ifPresent(user::setNickName);
        profileUpdateRequest.email().ifPresent(user::setEmail);

        userRepository.save(user);
    }

    @Transactional
    public void updateCategory(CustomUserDetails customUserDetails, CategoryUpdateRequest categoryUpdateRequest){
        Long userId = customUserDetails.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("not found user"));

        user.setCategory(categoryUpdateRequest.category());

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(CustomUserDetails customUserDetails){
        Long userId = customUserDetails.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("not found user"));

        userRepository.delete(user);
    }

    public UserInfoResponse getUserInfo(CustomUserDetails userDetails){
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new UserNotLoginException(USER_NOT_LOGIN);
        }

        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return UserInfoResponse.from(user);
    }
}
