package com.ticle.server.talk.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TalkResponseList(
        List<TalkResponse> talkResponseList
) {

    public static TalkResponseList from(List<TalkResponse> talkList) {
        return new TalkResponseList(talkList);
    }
}