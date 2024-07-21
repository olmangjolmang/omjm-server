package com.ticle.server.opinion.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentResponseList(
    String question,
    String userNickname,
    List<CommentResponse> responseList
) {
    public static CommentResponseList of(String question, String userNickname, List<CommentResponse> responseList) {
        return CommentResponseList.builder()
                .question(question)
                .userNickname(userNickname)
                .responseList(responseList)
                .build();
    }
}