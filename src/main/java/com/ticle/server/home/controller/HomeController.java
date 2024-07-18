package com.ticle.server.home.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.home.dto.request.SubscriptionRequest;
import com.ticle.server.home.dto.response.HomeResponse;
import com.ticle.server.home.service.HomeService;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "홈화면 정보 가져오기", description = "TOP 3와 3개의 소주제를 가져옵니다. (각각 3개의 아티클 포함)")
    @GetMapping("")
    public ResponseEntity<ResponseTemplate<Object>> getTopicsAndPosts() {

        List<HomeResponse> responseList = homeService.generateHomeInfo();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responseList));
    }
}