package com.ticle.server.user.domain;

import com.ticle.server.memo.domain.Memo;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.type.Category;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity
@Getter
@Builder
@NoArgsConstructor()
@AllArgsConstructor
public class User {
    // 애플리케이션의 핵심 비즈니스 로직을 담고 있는 개체
    // 주로 데이터베이스와 직접적으로 연관되어 있음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "agree_terms")
    private boolean agreeTerms;

    @OneToMany(mappedBy = "user")
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "user")
    private List<Memo> memos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrapped> scrappeds;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
