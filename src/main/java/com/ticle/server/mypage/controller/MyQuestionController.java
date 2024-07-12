package com.ticle.server.mypage.controller;

import com.ticle.server.global.dto.ResponseTemplate;
import com.ticle.server.mypage.dto.MyQuestionDto;
import com.ticle.server.mypage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="MyPage",description = "마이페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyQuestionController {

    private final MyPageService myPageService;

    @Operation(summary = "마이물어봥",description = "userId를 RequestParam에 넣어서 물어봥 질문들을 가져옴")
    @GetMapping("/my-question")
    public ResponseEntity<ResponseTemplate<Object>> getMyQuestions(@RequestParam("userid") Long userId){
        List<MyQuestionDto> myQuestionDtos;
        myQuestionDtos = myPageService.getMyQuestions(userId);
        List

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(myQuestionDtos));

    }
    @Operation(summary = "마이물어봥 수정",description = "question_id에서 수정하기")
    @PutMapping("/my-question/{id}")
    public ResponseEntity<ResponseTemplate<Object>> updateQuestion(@PathVariable("id") Long questionId,
                                                                   @RequestBody UpdateQuestionDto updateQuestionDto) {
        myPageService.updateQuestion(questionId, updateQuestionDto.getQuestionContent());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.success("질문이 성공적으로 수정되었습니다."));
    }


}
