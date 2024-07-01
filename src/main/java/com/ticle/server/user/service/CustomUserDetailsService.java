package com.ticle.server.user.service;


import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
//import com.ticle.server.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다"));
    }

//    private UserDetails createUserDetails(com.ticle.server.user.domain.User user){
//        return User.builder()
//                .email(user.getEmail())
//                .password(passwordEncoder.encode(user.getPassword()))
//                .roles(List.of(user.getRoles().toArray(new String[0])))
//                .build();
//    }

    private UserDetails createUserDetails(com.ticle.server.user.domain.User user) {
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(user.getEmail(), passwordEncoder.encode(user.getPassword()), authorities);
    }
}


