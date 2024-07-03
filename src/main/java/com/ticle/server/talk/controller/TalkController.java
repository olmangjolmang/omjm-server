package com.ticle.server.talk.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.talk.domain.type.Order;
import com.ticle.server.talk.dto.request.CommentUploadRequest;
import com.ticle.server.talk.dto.response.CommentResponse;
import com.ticle.server.talk.dto.response.TalkResponse;
import com.ticle.server.talk.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ticle.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Talk", description = "물어봥 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/talk")
public class TalkController {

    private static final int PAGE_SIZE = 5;
    private final TalkService talkService;

    @Operation(summary = "댓글 등록", description = "질문에 대한 댓글 등록하기")
    @PostMapping("/{talkId}/comment/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> uploadComment(
            @PathVariable Long talkId,
            @PathVariable Long userId,
            @Valid @RequestBody CommentUploadRequest request) {

        talkService.uploadComment(request, talkId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "좋아요/취소 기능", description = "댓글에 대하여 좋아요 및 취소 기능<br>" +
            "유저가 좋아요 했던 댓글이라면 취소를, 좋아요 하지 않았던 댓글이라면 좋아요를 할 수 있습니다.")
    @PostMapping("/comment/{commentId}/heart/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> heartComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        talkService.heartComment(commentId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "질문에 대한 모든 댓글 가져오기", description = "질문에 대한 모든 댓글 가져오는 API입니다.<br>" +
            "orderBy에는 시간순이라면 time, 좋아요순이라면 heart를 넣어주시면 됩니다.(default는 시간순) <br>" +
            "isHeart는 해당 유저가 좋아요를 눌렀는지 유무를 나타냅니다.")
    @GetMapping("/{talkId}/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> getComments(
            @PathVariable Long talkId,
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "TIME") Order orderBy) {

        List<CommentResponse> responses = talkService.getComments(talkId, userId, orderBy);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responses));
    }

    @Operation(summary = "물어봥 질문 리스트 조회", description = "물어봥 모든 질문과 함께 인기댓글 2개 조회 가능")
    @GetMapping()
    public ResponseEntity<ResponseTemplate<Object>> getTalks() {

        List<TalkResponse> responses = talkService.getTalks();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responses));
    }
}
