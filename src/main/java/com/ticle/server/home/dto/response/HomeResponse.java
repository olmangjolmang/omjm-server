package com.ticle.server.home.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HomeResponse(
        String topic,
        List<PostSetsResponse> responseList
) {
    public static HomeResponse of(String topic, List<PostSetsResponse> responseList) {
        return HomeResponse.builder()
                .topic(topic)
                .responseList(responseList)
                .build();
    }
}
