package com.ticle.server.user.dto;

import com.ticle.server.user.domain.User;
import com.ticle.server.mypage.domain.type.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    // DTO는 주로 데이터 전송을 위한 객체
    // 클라이언트와 서버 간 또는 애플리케이션 계층 간에 데이터를 전송하는 데 사용됨

    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 비어있습니다")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "닉네임이 비어있습니다")
    private String nickname;

    @NotBlank
    private String category;

    @NotBlank
    private boolean agree_terms;

    public User toEntity(String encodedPassword){
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .nickname(this.nickname)
                .category(this.category)
                .agree_terms(this.agree_terms)
                .build();
    }


}
