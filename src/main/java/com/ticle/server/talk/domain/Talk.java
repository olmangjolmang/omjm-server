package com.ticle.server.talk.domain;

import com.ticle.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "talk")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talk_id")
    private Long talkId;

    @Column(name = "question", length = 200)
    private String question;

    @Column(name = "view")
    private Long view;


    @Column(name = "comment_count")
    private Long commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Talk(String question, Long view, User user) {
        this.question = question;
        this.view = view;
        this.user = user;
    }
}
