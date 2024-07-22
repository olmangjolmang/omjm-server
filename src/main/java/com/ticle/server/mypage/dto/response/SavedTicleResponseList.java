package com.ticle.server.mypage.dto.response;

import com.ticle.server.global.dto.PageInfo;
import com.ticle.server.opinion.dto.response.CommentResponse;
import com.ticle.server.opinion.dto.response.OpinionResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record SavedTicleResponseList(
        PageInfo pageInfo,
        List<SavedTicleResponse> savedTicleResponseList
) {

    public static SavedTicleResponseList of(PageInfo pageInfo, List<SavedTicleResponse> savedTicleResponseList) {
        return new SavedTicleResponseList(pageInfo, savedTicleResponseList);
    }
}