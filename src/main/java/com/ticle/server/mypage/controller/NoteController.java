package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.dto.MyNoteDto;
import com.ticle.server.mypage.service.MyPageService;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="TicleNote",description = "티클노트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class NoteController {

    private final MyPageService myPageService;

    @Operation(summary="티클노트",description = "userId에 해당하는 note들을 불러옴")
    @GetMapping("/my-note")
    public ResponseEntity<ResponseTemplate<Object>> getMyNotes(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = customUserDetails.getUserId();
        List<MyNoteDto> myNoteDtos = myPageService.getMyNotes(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(myNoteDtos));
    }
}
