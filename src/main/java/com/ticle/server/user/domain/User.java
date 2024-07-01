package com.ticle.server.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 애플리케이션의 핵심 비즈니스 로직을 담고 있는 개체
    // 주로 데이터베이스와 직접적으로 연관되어 있음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "category")
    private String category;

    @Column(name = "agree_terms")
    private boolean agree_terms;

}
