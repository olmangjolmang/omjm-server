package com.ticle.server.talk.domain;

import com.ticle.server.mypage.domain.User;
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
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talk_id")
    private Long talkId;

    @Column(name = "question", length = 200)
    private String question;

    @Column(name = "view")
    private Long view;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "talk", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Talk(String question, Long view, User user, List<Comment> comments) {
        this.question = question;
        this.view = view;
        this.user = user;
        this.comments = comments;
    }
}
