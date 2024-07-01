package com.ticle.server.user.controller;

import com.ticle.server.user.dto.UserDto;
import com.ticle.server.user.service.EmailService;
import com.ticle.server.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

//    @PostMapping("/email-send")
//    public String mailSend()
    @PostMapping("/emailSend")
    public String mailConfirm(@RequestParam(value = "email", required = false) String email) throws Exception{
        String code = emailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }
}





