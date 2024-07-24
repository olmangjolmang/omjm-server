package com.ticle.server.user.controller;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.exception.PostNotFoundException;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.user.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.ticle.server.post.exception.errorcode.PostErrorCode.POST_NOT_FOUND;

@Tag(name = "Email", description = "이메일 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final PostRepository postRepository;

    @Operation(summary = "이메일 인증", description = "이메일 인증코드")
    @PostMapping("/email-send")
    public String mailConfirm(@RequestParam(value = "email", required = false) String email) throws Exception{
        String code = emailService.sendSimpleMessage(email);
        return code;
    }

    @PostMapping("/email-test")
    public String mailTest(@RequestParam(value = "email", required = false) String email) throws Exception{
        Post post = postRepository.findById(1L).orElseThrow(()-> new RuntimeException("해당하는 게시글이 없습니다"));
        emailService.sendEmail2(email,post);
        return email+"로의 뉴스레터 전송이 완료되었습니다.";
    }


}





