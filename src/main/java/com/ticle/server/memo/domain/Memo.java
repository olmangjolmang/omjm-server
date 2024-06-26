package com.ticle.server.memo.domain;

import com.ticle.server.mypage.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
    private Long memo_id;

    @Column(name="content")
    private String content;

    @Column(name="memo_date")
    private Timestamp memo_date;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;

    // post 머지 후에 주석 풀어야 함.
//    @ManyToOne
//    @JoinColumn(name="post_id")
//    private Post post_id;

}
