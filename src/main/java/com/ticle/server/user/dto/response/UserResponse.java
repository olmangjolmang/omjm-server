package com.ticle.server.user.dto.response;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String nickName;
    private String email;
    private List<Category> category;

    public static UserResponse toDto(User user){
        return UserResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .category(user.getCategory())
                .build();
    }

}
