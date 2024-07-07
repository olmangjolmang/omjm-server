package com.ticle.server.home.dto.request;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.user.domain.User;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
    @NotNull(message = "이메일을 입력해주세요.")
    String email,
    @NotNull(message = "닉네임을 입력해주세요.")
    String nickName,
    @NotNull(message = "요일을 선택해주세요.")
    Day subsDay,
    @NotNull(message = "정보 수신 동의 여부를 선택해주세요.")
    Boolean agreeInfo,
    Boolean agreeMarketing
) {
    public Subscription toSubscription(User user) {
        return Subscription
                .builder()
                .email(email)
                .nickName(nickName)
                .subsDay(subsDay)
                .agreeInfo(agreeInfo)
                .agreeMarketing(agreeMarketing)
                .user(user)
                .build();
    }
}
