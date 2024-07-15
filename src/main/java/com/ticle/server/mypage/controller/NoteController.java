package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="TicleNote",description = "티클노트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class NoteController {

    private final MyPageService myPageService;

    @Operation(summary="티클노트",description = "userId에 해당하는 note들을 불러옴")
    @GetMapping("my-note")
    public ResponseEntity<ResponseTemplate<Object>> getMyNotes(@AuthenticationPrincipal CustomUser)
}
