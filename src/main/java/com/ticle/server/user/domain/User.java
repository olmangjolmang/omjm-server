package com.ticle.server.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "agree_terms")
    private boolean agree_terms;

}
