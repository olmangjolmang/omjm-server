package com.ticle.server.memo.domain;

import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name="Memo")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memo_id")
    private Long memoId;

    @Column(name="content")
    private String content;

    @CreatedDate
    @Column(name="memo_date",nullable = false)
    private LocalDateTime memoDate;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

}
