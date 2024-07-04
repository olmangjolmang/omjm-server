package com.ticle.server.scrapped.domain;

import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "post_id",nullable = false)
     private Post post;

}
