package com.ticle.server.user.service;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.dto.JoinRequest;
import com.ticle.server.user.dto.LoginRequest;
import com.ticle.server.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public boolean checkEmailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplicate(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public void signup(JoinRequest req){
        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    public User login(LoginRequest req){
        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());

        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();

        if(!user.getPassword().equals(req.getPassword())){
            return null;
        }
        return user;
    }

    public User getLoginUserByEmail(String email){

    }

}
