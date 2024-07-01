package com.ticle.server.user.service;

import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
}
