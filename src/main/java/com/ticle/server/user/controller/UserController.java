package com.ticle.server.user.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.request.JoinRequest;
import com.ticle.server.user.dto.request.ReissueTokenRequest;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.dto.request.LoginRequest;
import com.ticle.server.user.dto.response.UserResponse;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.service.CustomUserDetails;
import com.ticle.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "User", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인", description = "로그인하기")
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseTemplate<Object>> signIn(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String email = loginRequest.email();
        String password = loginRequest.password();
        JwtTokenResponse jwtTokenResponse = userService.signIn(email, password);
        response.addHeader("Authorization",jwtTokenResponse.getAccessToken());
        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(jwtTokenResponse));
    }

    @Operation(summary = "회원가입", description = "회원가입하기")
    @PostMapping("sign-up")
    public ResponseEntity<ResponseTemplate<Object>> signUp(@RequestBody JoinRequest joinRequest) {
        UserResponse savedUserDto = userService.signUp(joinRequest);
        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(savedUserDto));
    }
    @Operation(summary = "로그아웃", description = "로그아웃하기")
    @DeleteMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request){
        String accessToken = jwtTokenProvider.resolveToken(request);
        String email = userRepository.findById(userDetails.getUserId()).get().getEmail();
        return userService.logout(accessToken, email);//username = email
    }

    @PostMapping("/reissue-token")
    public JwtTokenResponse reissueToken(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestBody ReissueTokenRequest tokenRequest){
        //유저 객체 정보를 이용하여 토큰 발행
//        UserResponse user = UserResponse.of(userDetails.get);
        return jwtTokenProvider.reissueAtk(userDetails.getEmail(), userDetails.getPassword(),tokenRequest.getRefreshToken());
    }

}
