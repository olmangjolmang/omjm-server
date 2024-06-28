package com.ticle.server.talk.domain;

import com.ticle.server.mypage.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "heart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long heartId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Builder
    public Heart(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
