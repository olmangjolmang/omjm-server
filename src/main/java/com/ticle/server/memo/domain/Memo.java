package com.ticle.server.memo.domain;

import com.ticle.server.global.domain.BaseTimeEntity;
import com.ticle.server.post.domain.Post;
import com.ticle.server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Memo")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long memoId;

    @Column(name = "target_text")
    private String targetText;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void updateNote(String content){
        this.content = content;
    }
}
