package com.ticle.server.talk.domain;

import com.ticle.server.global.domain.BaseTimeEntity;
import com.ticle.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "talk")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Talk extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talk_id")
    private Long talkId;

    @Column(name = "question", length = 200)
    private String question;

    @Column(name = "view_count")
    private Long viewCount;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "talk", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "comment_count")
    private Long commentCount;

    @Builder
    public Talk(String question, Long viewCount, User user, List<Comment> comments, Long commentCount) {
        this.question = question;
        this.viewCount = viewCount;
        this.user = user;
        this.comments = comments;
        this.commentCount = commentCount;
    }

    public void addViewCount() {
        this.viewCount++;
    }
}
