package com.ticle.server.user.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
//@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Long userId;
//    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public Long getUserId() {
        return userId;
    }

//    public String getEmail(){return email;}

    @Override
    public String getUsername() {
        return String.valueOf(userId); // userId를 문자열로 반환
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
