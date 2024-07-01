package com.ticle.server.user.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getCurrentUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName()==null){
            throw new RuntimeException("No auth informantion");
        }
        return authentication.getName();
    }
}
