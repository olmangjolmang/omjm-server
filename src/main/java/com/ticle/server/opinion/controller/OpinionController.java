package com.ticle.server.opinion.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.opinion.domain.type.Order;
import com.ticle.server.opinion.dto.request.CommentUploadRequest;
import com.ticle.server.opinion.dto.response.CommentResponse;
import com.ticle.server.opinion.dto.response.OpinionResponseList;
import com.ticle.server.opinion.service.OpinionService;
import com.ticle.server.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ticle.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Slf4j
@Tag(name = "Opinion", description = "티클 문답 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/opinion")
public class OpinionController {

    private final OpinionService opinionService;

    @Operation(summary = "댓글 등록", description = "질문에 대한 댓글 등록하기")
    @PostMapping("/{opinionId}/comment")
    public ResponseEntity<ResponseTemplate<Object>> uploadComment(
            @PathVariable Long opinionId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CommentUploadRequest request) {

        opinionService.uploadComment(request, opinionId, userDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "좋아요/취소 기능", description = "댓글에 대하여 좋아요 및 취소 기능<br>" +
            "유저가 좋아요 했던 댓글이라면 취소를, 좋아요 하지 않았던 댓글이라면 좋아요를 할 수 있습니다.")
    @PostMapping("/comment/{commentId}/heart")
    public ResponseEntity<ResponseTemplate<Object>> heartComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        opinionService.heartComment(commentId, userDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "질문에 대한 모든 댓글 가져오기", description = "질문에 대한 모든 댓글 가져오는 API입니다.<br>" +
            "orderBy에는 시간순이라면 time, 좋아요순이라면 heart를 넣어주시면 됩니다.(default는 시간순) <br>" +
            "isHeart는 해당 유저가 좋아요를 눌렀는지 유무를 나타냅니다.")
    @GetMapping("/{opinionId}")
    public ResponseEntity<ResponseTemplate<Object>> getComments(
            @PathVariable Long opinionId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false, defaultValue = "TIME") Order orderBy) {

        List<CommentResponse> responses = opinionService.getCommentsByOpinion(opinionId, userDetails, orderBy);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responses));
    }

    @Operation(summary = "티클 문답 질문 리스트 조회", description = "티클 문답 모든 질문과 함께 인기댓글 2개 조회 가능")
    @GetMapping()
    public ResponseEntity<ResponseTemplate<Object>> getOpinions(
            @RequestParam(defaultValue = "1") int page) {

        OpinionResponseList response = opinionService.getOpinionsByPage(page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
