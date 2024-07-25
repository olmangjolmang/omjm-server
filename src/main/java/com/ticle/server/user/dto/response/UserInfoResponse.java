package com.ticle.server.user.dto.response;

import com.ticle.server.user.domain.User;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        String nickname,
        String email
) {
    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .nickname(user.getNickName())
                .email(user.getEmail())
                .build();
    }
}
