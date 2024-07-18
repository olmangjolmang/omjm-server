package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.dto.response.QnAResponse;
import com.ticle.server.mypage.dto.response.QuestionResponse;
import com.ticle.server.mypage.dto.request.CommentUpdateRequest;
import com.ticle.server.mypage.service.MyPageService;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Operation(summary = "티클문답",description = "Jwt token을 통해 userId를 가져온 후 티클문답의 질문들을 가져옴")
    @GetMapping("/my-question")
    public ResponseEntity<ResponseTemplate<Object>> getMyQuestions(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PageableDefault(page=0,size=9,sort="created_date",direction = Sort.Direction.DESC) Pageable pageable){

        Long userId = customUserDetails.getUserId();

        List<QnAResponse> qnAResponseList = myPageService.getMyQnA(userId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(qnAResponseList));

    }
    @Operation(summary = "티클문답 수정", description = "마이페이지(티클문답)에서 user가 쓴 답변 수정하기")
    @PutMapping("/my-question/{questionId}")
    public ResponseEntity<ResponseTemplate<Object>> updateQuestion(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("questionId") Long questionId, @RequestBody CommentUpdateRequest commentUpdateRequest) {

        Long userId = customUserDetails.getUserId();
        myPageService.updateComment(userId, questionId, commentUpdateRequest.getContent());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(customUserDetails.getUserId() + "님의 질문에 대한 답변이 성공적으로 수정되었습니다."));
    }

    @Operation(summary = "티클문답 삭제", description = "마이페이지(티클문답)에서 user가 쓴 답변 삭제하기")
    @DeleteMapping("/my-question/{questionId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @PathVariable("questionId") Long questionId) {
        Long userId = customUserDetails.getUserId();
        myPageService.deleteComment(userId, questionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(userId + "님의 질문에 대한 댓글이 성공적으로 삭제되었습니다."));

    }

}
