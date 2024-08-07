package com.ticle.server.user.jwt;


import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

//import com.ticle.server.user.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
//        log.info("email------> "+ email);

        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다"));
    }

    private CustomUserDetails createUserDetails(com.ticle.server.user.domain.User user) {
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        return new CustomUserDetails(user.getId(), user.getPassword() ,authorities);
    }
}


