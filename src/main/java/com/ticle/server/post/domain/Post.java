package com.ticle.server.post.domain;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.mypage.domain.type.Category;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Table(name = "Post")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long post_id;

    @Column(name="content",nullable = false)
    private String content;

    @Column(name="author",nullable = false)
    private String author;

    @Column(name="create_date",nullable = false)
    private Timestamp create_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_category")
    private Category post_category;

    @Column(name="image")
    private String image;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;

}
