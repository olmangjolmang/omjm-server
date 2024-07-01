package com.ticle.server.user.dto;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    // DTO는 주로 데이터 전송을 위한 객체
    // 클라이언트와 서버 간 또는 애플리케이션 계층 간에 데이터를 전송하는 데 사용됨

    private String email;
    private String password;
//    private String passwordCheck;
    private String nickName;
    private String category;
    private boolean agreeTerms;
    private List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword,List<String> roles){
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .nickName(this.nickName)
                .category(Category.valueOf(this.category))
                .agreeTerms(this.agreeTerms)
                .roles(this.roles)
                .build();
    }


}
