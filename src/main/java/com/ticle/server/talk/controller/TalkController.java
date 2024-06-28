package com.ticle.server.talk.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.talk.dto.request.CommentUploadRequest;
import com.ticle.server.talk.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ticle.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Talk", description = "물어봥 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/talk")
public class TalkController {

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
}
