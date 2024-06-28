package com.ticle.server.talk.domain;

import com.ticle.server.mypage.domain.User;
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

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "talk_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Talk talk;

    @Builder
    public Comment(String content, Long heart, User user, Talk talk) {
        this.content = content;
        this.heart = heart;
        this.user = user;
        this.talk = talk;
    }
}