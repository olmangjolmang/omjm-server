package com.ticle.server.home.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.home.dto.request.SubscriptionRequest;
import com.ticle.server.home.service.HomeService;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ticle.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Home", description = "홈 화면 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "구독하기 등록", description = "아티클을 주기적으로 받을 수 있는 구독하기 등록합니다.")
    @PostMapping("/subscription")
    public ResponseEntity<ResponseTemplate<Object>> uploadSubscription(
            @Valid @RequestBody SubscriptionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        homeService.uploadSubscription(request, userDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}