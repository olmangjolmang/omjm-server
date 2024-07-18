package com.ticle.server.user.service;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.JoinRequest;
import com.ticle.server.user.dto.JwtToken;
import com.ticle.server.user.dto.LoginRequest;
import com.ticle.server.user.dto.UserDto;
import com.ticle.server.user.jwt.JwtTokenProvider;
import com.ticle.server.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtToken signIn(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        log.info("hello" + authentication.getDetails());
        log.info("hello22" + authentication.getName());


        return jwtToken;
    }

    @Transactional
    public UserDto signUp(JoinRequest joinRequest) {
        if (userRepository.existsByEmail(joinRequest.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return UserDto.toDto(userRepository.save(joinRequest.toEntity(encodedPassword, roles)));
    }


    public User getLoginUserByEmail(String email) {
        if (email == null)
            return null;

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty())
            return null;

        return optionalUser.get();
    }

}
