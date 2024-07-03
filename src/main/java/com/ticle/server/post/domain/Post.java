package com.ticle.server.post.domain;

import com.ticle.server.global.domain.BaseTimeEntity;
import com.ticle.server.global.domain.S3Info;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Table(name = "Post")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column
    private S3Info image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
