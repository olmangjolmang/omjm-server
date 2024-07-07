package com.ticle.server.home.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Home", description = "홈 화면 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "이메일 검증", description = "이메일 유효성 검증 <br>" +
            "가입되어 있는 이메일이라면 true, 가입되지 않은 이메일이라면 false를 반환합니다.")
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseTemplate<Object>> validateEmail(
            @PathVariable String email) {

        Boolean response = homeService.validateEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "닉네임 검증", description = "닉네임 유효성 검증 <br> " +
            "가입되어 있는 닉네임이라면 true, 가입되지 않은 닉네임이라면 false를 반환합니다.")
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<ResponseTemplate<Object>> validateNickname(
            @PathVariable String nickname) {

        Boolean response = homeService.validateNickName(nickname);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}