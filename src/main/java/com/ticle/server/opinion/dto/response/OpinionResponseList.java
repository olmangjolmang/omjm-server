package com.ticle.server.opinion.dto.response;

import com.ticle.server.global.dto.PageInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record OpinionResponseList(
        PageInfo pageInfo,
        List<OpinionResponse> opinionResponseList
) {

    public static OpinionResponseList from(PageInfo pageInfo, List<OpinionResponse> opinionList) {
        return new OpinionResponseList(pageInfo, opinionList);
    }
}