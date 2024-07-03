package com.ticle.server.user.controller;

import com.ticle.server.user.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email", description = "이메일 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "이메일 인증", description = "이메일 인증코드")
    @PostMapping("/emailSend")
    public String mailConfirm(@RequestParam(value = "email", required = false) String email) throws Exception{
        String code = emailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }
}





