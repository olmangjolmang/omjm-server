package com.ticle.server.user.domain;

import com.ticle.server.user.domain.type.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "agree_terms")
    private boolean agreeTerms;

    @Builder
    public User(String email, String nickname, String password, Category category, Boolean agreeTerms) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.category = category;
        this.agreeTerms = agreeTerms;
    }
}
