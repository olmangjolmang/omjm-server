package com.ticle.server.mypage.domain;

import com.ticle.server.mypage.domain.type.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "password", length = 40)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Builder
    public User(String email, String nickname, String password, Category category) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.category = category;
    }
}
