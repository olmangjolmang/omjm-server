package com.ticle.server.user.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.user.dto.request.*;
import com.ticle.server.user.dto.response.JwtTokenResponse;
import com.ticle.server.user.dto.response.UserInfoResponse;
import com.ticle.server.user.dto.response.UserResponse;
import com.ticle.server.user.jwt.CustomUserDetails;
import com.ticle.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "User", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "email과 password로 로그인을 진행합니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseTemplate<Object>> signIn(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        JwtTokenResponse jwtTokenResponse = userService.signIn(loginRequest);
        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(jwtTokenResponse));
    }

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate<Object>> signUp(@RequestBody JoinRequest joinRequest) {
        UserResponse savedUserDto = userService.signUp(joinRequest);
        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(savedUserDto));
    }

    @Operation(summary = "로그아웃", description = "user의 email을 받아와서 redis에서 email을 삭제, accessToken을 블랙리스트 처리합니다. ")
    @DeleteMapping("/logout")
    public ResponseEntity<ResponseTemplate<Object>> logout(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request){

        return  ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(userService.logout(userDetails, request)));
    }

    @Operation(summary = "토큰 재발급", description = "Header의 accessToken과 RequestBody로 refreshToken을 넣어서 토큰을 재발급받을 수 있습니다.")
    @PostMapping("/reissue")
    public ResponseEntity<ResponseTemplate<Object>> reissueToken(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestBody ReissueTokenRequest tokenRequest){
        //유저 객체 정보를 이용하여 토큰 발행
        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(userService.reissueAtk(userDetails,tokenRequest.getRefreshToken())));
    }



    @Operation(summary = "프로필 수정", description = "닉네임과 이메일을 수정할 수 있는 페이지입니다.")
    @PutMapping("/profile")
    public ResponseEntity<ResponseTemplate<Object>> updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ProfileUpdateRequest profileUpdateRequest){
        userService.updateProfile(customUserDetails, profileUpdateRequest);

        return ResponseEntity
                 .status(OK)
                 .body(ResponseTemplate.from(customUserDetails.getUserId() + "님의 회원정보가 수정되었습니다.\n" + profileUpdateRequest.toString()));
    }

    @Operation(summary = "관심분야 수정", description = "관심분야를 수정할 수 있는 페이지입니다.")
    @PutMapping("/category")
    public ResponseEntity<ResponseTemplate<Object>> updateCategory(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        userService.updateCategory(customUserDetails, categoryUpdateRequest);

        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(customUserDetails.getUserId() + "님의 회원정보가 수정되었습니다.\n" + categoryUpdateRequest.toString()));
    }

    @Operation(summary = "탈퇴하기", description ="유저의 정보를 DB에서 삭제합니다.")
    @DeleteMapping("/profile")
    public ResponseEntity<ResponseTemplate<Object>> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        userService.deleteUser(customUserDetails);

        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(customUserDetails.getUserId() + "님의 회원정보가 삭제되었습니다.\n" ));
    }

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회")
    @GetMapping("/profile")
    public ResponseEntity<ResponseTemplate<Object>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails){

        UserInfoResponse response = userService.getUserInfo(userDetails);

        return ResponseEntity
                .status(OK)
                .body(ResponseTemplate.from(response));
    }
}
