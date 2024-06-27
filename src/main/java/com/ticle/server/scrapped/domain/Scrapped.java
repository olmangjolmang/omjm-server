package com.ticle.server.scrapped.domain;

import com.ticle.server.mypage.domain.User;
import com.ticle.server.post.domain.Post;
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

     @ManyToOne
     @JoinColumn(name = "post_id")
     private Post post;

}
