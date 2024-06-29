package com.ticle.server.user.service;

import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final UserRepository userRepository;
    private String authNum;

    private void checkDuplicatedEmail(String email){
        userRepository.findByEmail
    }




}
