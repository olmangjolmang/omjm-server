package com.ticle.server.user.controller;

import com.ticle.server.user.dto.JoinRequest;
import com.ticle.server.user.dto.JwtToken;
import com.ticle.server.user.dto.LoginRequest;
import com.ticle.server.user.dto.UserDto;
import com.ticle.server.user.jwt.SecurityUtil;
import com.ticle.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        JwtToken jwtToken = userService.signIn(email,password);
        log.info("request username = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }
    @PostMapping("sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody JoinRequest joinRequest){
        UserDto savedUserDto = userService.signUp(joinRequest);
        return ResponseEntity.ok(savedUserDto);
    }
    @PostMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }


}
