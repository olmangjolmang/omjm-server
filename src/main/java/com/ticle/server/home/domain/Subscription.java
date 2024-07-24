package com.ticle.server.home.domain;

import com.ticle.server.global.domain.BaseTimeEntity;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscription extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_id")
    private Long subsId;

    @Column(name = "agree_info")
    private Boolean agreeInfo;

    @Column(name = "agree_marketing")
    private Boolean agreeMarketing;

    @Enumerated(EnumType.STRING)
    @Column(name = "subs_day")
    private Day subsDay;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Subscription(Boolean agreeInfo, Boolean agreeMarketing, Day subsDay, User user) {
        this.agreeInfo = agreeInfo;
        this.agreeMarketing = agreeMarketing;
        this.subsDay = subsDay;
        this.user = user;
    }
}
