package com.ticle.server.user.dto.request;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class JoinRequest {
    // DTO는 주로 데이터 전송을 위한 객체
    // 클라이언트와 서버 간 또는 애플리케이션 계층 간에 데이터를 전송하는 데 사용됨
    @NotBlank(message = "email은 필수 입력 값입니다. ")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
//    private String passwordCheck;
    private String nickName;
    private Category category;
    private boolean agreeTerms;
    private List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword,List<String> roles){
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .nickName(nickName)
                .category(category)
                .agreeTerms(agreeTerms)
                .roles(roles)
                .build();
    }


}
