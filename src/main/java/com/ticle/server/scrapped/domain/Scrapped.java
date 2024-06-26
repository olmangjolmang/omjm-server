package com.ticle.server.scrapped.domain;

import com.ticle.server.mypage.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Scrapped")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Scrapped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

// 주석 풀고 병합
//     @ManyToOne
//     @JoinColumn(name = "post_id")
//     private Post post;

}
