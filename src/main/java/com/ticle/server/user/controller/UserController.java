package com.ticle.server.user.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.user.dto.JoinRequest;
import com.ticle.server.user.dto.JwtToken;
import com.ticle.server.user.dto.LoginRequest;
import com.ticle.server.user.dto.UserDto;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.jwt.SecurityUtil;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseTemplate<Object>> signIn(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.email();
        String password = loginRequest.password();
        JwtToken jwtToken = userService.signIn(email,password);
        log.info("request username = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(jwtToken));
    }

    @Operation(summary = "회원가입", description = "회원가입하기")
    @PostMapping("sign-up")
    public ResponseEntity<ResponseTemplate<Object>> signUp(@RequestBody JoinRequest joinRequest){
        UserDto savedUserDto = userService.signUp(joinRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(savedUserDto));
    }

    @Operation(summary = "테스트", description = "테스트하기")
    @PostMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }
}
