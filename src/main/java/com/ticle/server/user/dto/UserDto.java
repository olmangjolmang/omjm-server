package com.ticle.server.user.dto;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String nickName;
    private String email;
    private Category category;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .category(user.getCategory())
                .build();
    }

}
