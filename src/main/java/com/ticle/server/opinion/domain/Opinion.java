package com.ticle.server.opinion.domain;

import com.ticle.server.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "opinion")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Opinion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opinion_id")
    private Long opinionId;

    @Column(name = "question", length = 200)
    private String question;

    @Column(name = "view_count")
    private Long viewCount;

    @OneToMany(mappedBy = "opinion", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "comment_count")
    private Long commentCount;

    @Builder
    public Opinion(String question, Long viewCount,List<Comment> comments, Long commentCount) {
        this.question = question;
        this.viewCount = viewCount;
        this.comments = comments;
        this.commentCount = commentCount;
    }

    public void addViewCount() {
        this.viewCount++;
    }

    public void addCommentCount() {
        this.commentCount++;
    }


    public void subCommentCount() {
        this.commentCount--;
    }
}
