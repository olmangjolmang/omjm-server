package com.ticle.server.home.dto.request;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.user.domain.User;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
    @NotNull(message = "요일을 선택해주세요.")
    Day subsDay,
    @NotNull(message = "정보 수신 동의 여부를 선택해주세요.")
    Boolean agreeInfo,
    Boolean agreeMarketing
) {
    public Subscription toSubscription(User user) {
        return Subscription
                .builder()
                .subsDay(subsDay)
                .agreeInfo(agreeInfo)
                .agreeMarketing(agreeMarketing)
                .user(user)
                .build();
    }
}
