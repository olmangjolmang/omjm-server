package com.ticle.server.talk.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "heart")
    private Long heart;

    @Builder
    public Comment(String content, Long heart) {
        this.content = content;
        this.heart = heart;
    }
}