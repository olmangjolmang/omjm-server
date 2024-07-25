package com.ticle.server.user.dto.response;

import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import lombok.Builder;

import java.util.List;

@Builder
public record UserInfoResponse(
        String nickname,
        String email,
        List<Category> category
) {
    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .nickname(user.getNickName())
                .email(user.getEmail())
                .category(user.getCategory())
                .build();
    }
}
