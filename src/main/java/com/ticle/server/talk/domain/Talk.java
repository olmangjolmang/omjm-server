package com.ticle.server.talk.domain;

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

    @Builder
    public Talk(String question, Long view) {
        this.question = question;
        this.view = view;
    }
}
