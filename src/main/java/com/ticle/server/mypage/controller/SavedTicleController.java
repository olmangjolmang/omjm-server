package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.dto.response.SavedTicleResponseList;
import com.ticle.server.mypage.service.MyPageService;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="MyPage",description = "마이페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class SavedTicleController {

    private final MyPageService myPageService;

    @Operation(summary = "저장한 아티클",description = "userId와 category를 RequestParam에 넣어서 아티클을 가져옴")
    @GetMapping("/saved-ticles")
    public ResponseEntity<ResponseTemplate<Object>> getSavedTicles(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                   @RequestParam(value = "category", required = false) Category category,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page) {

        SavedTicleResponseList savedTicleResponses;

        if (ObjectUtils.isNotEmpty(category)) {
            savedTicleResponses = myPageService.getSavedArticlesByCategory(customUserDetails, category, page);
        } else {
            savedTicleResponses = myPageService.getSavedArticles(customUserDetails, page);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(savedTicleResponses));
    }
}
