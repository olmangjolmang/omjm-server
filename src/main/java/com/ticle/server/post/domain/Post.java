package com.ticle.server.post.domain;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.user.domain.type.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Table(name = "Post")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

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

    @Column(name = "created_date")
    Date createdDate;

    @Embedded
    @Column
    private S3Info image;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "scrap_count")
    private Integer scrapCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrapped> scrappeds;


    public void increaseScrapCount() {
        this.scrapCount++; //스크랩 수 증가
    }

    public void decreaseScrapCount() {
        this.scrapCount--; // 스크랩 수 감소
    }
}
