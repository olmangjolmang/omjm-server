package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.dto.response.NoteResponse;
import com.ticle.server.mypage.dto.request.NoteUpdateRequest;
import com.ticle.server.mypage.service.MyPageService;
import com.ticle.server.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class NoteController {

    private final MyPageService myPageService;

    @Operation(summary="티클노트 조회",description = "userId에 해당하는 note들을 불러옵니다.")
    @GetMapping("/my-note")
    public ResponseEntity<ResponseTemplate<Object>> getMyNotes(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PageableDefault(page=0,size=9,sort="createdDate",direction = Sort.Direction.DESC) Pageable pageable){
        Long userId = customUserDetails.getUserId();
        List<NoteResponse> noteResponses = myPageService.getMyNotes(userId,pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(noteResponses));
    }

    @Operation(summary="티클노트 수정",description = "noteId에 해당하는 note들을 수정합니다.")
    @PutMapping("/my-note/{id}")
    public ResponseEntity<ResponseTemplate<Object>> updateMyNotes(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable("id") Long id, @RequestBody NoteUpdateRequest noteUpdateRequest){
        myPageService.updateNote(customUserDetails,id,noteUpdateRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(id + "번 노트가 수정되었습니다."));
    }

    @Operation(summary="티클노트 삭제",description = "noteId에 해당하는 note들을 삭제합니다.")
    @DeleteMapping("/my-note/{id}")
    public ResponseEntity<ResponseTemplate<Object>> deleteMyNotes(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long id){
        myPageService.deleteNote(customUserDetails,id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(id + "번 노트가 삭제되었습니다."));
    }
}
