package com.ticle.server.opinion.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record OpinionResponseList(
        List<OpinionResponse> opinionResponseList
) {

    public static OpinionResponseList from(List<OpinionResponse> opinionList) {
        return new OpinionResponseList(opinionList);
    }
}