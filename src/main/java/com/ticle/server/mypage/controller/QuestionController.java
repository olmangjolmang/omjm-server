package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.dto.MyQuestionDto;
import com.ticle.server.mypage.dto.UpdateCommentDto;
import com.ticle.server.mypage.service.MyPageService;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name="MyPage",description = "마이페이지 관련 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class QuestionController {

    private final MyPageService myPageService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "마이물어봥",description = "Jwt token을 통해 userId를 가져온 후 물어봥 질문들을 가져옴")
    @GetMapping("/my-question")
    public ResponseEntity<ResponseTemplate<Object>> getMyQuestions(@AuthenticationPrincipal CustomUserDetails customUserDetails){

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseTemplate.EMPTY_RESPONSE);
//        }

        Long userId = customUserDetails.getUserId();

        List<MyQuestionDto> myQuestionDtos;
        myQuestionDtos = myPageService.getMyQuestions(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(myQuestionDtos));

    }
    @Operation(summary = "마이물어봥 수정",description = "question_id에서 수정하기")
    @PutMapping("/my-question/{id}")
    public ResponseEntity<ResponseTemplate<Object>> updateQuestion(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable("id") Long questionId, @RequestBody UpdateCommentDto updateCommentDto) {
        Long userId = customUserDetails.getUserId();
        myPageService.updateComment(userId, questionId, updateCommentDto.getContent());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from("질문이 성공적으로 수정되었습니다."));
    }


}