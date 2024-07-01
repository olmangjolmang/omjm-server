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

@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content", length = 500)
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "talk_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Talk talk;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();
    private Long heartCount;

    @Builder
    public Comment(String content, User user, Talk talk, List<Heart> hearts, Long heartCount) {
        this.content = content;
        this.user = user;
        this.talk = talk;
        this.hearts = hearts;
        this.heartCount = heartCount;
    }

    public void heartChange(Long heartCount) {
        this.heartCount = heartCount;
    }
}