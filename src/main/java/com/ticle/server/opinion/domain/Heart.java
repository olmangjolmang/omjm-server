package com.ticle.server.opinion.domain;

import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.talk.exception.CommentNotFoundException;
import com.ticle.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

<<<<<<< HEAD:src/main/java/com/ticle/server/talk/domain/Talk.java
import java.util.List;

@Table(name = "talk")
=======
@Table(name = "heart")
>>>>>>> a6ec0e64c5f2b3645cabf7461c6f49d16db708a2:src/main/java/com/ticle/server/opinion/domain/Heart.java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long heartId;

<<<<<<< HEAD:src/main/java/com/ticle/server/talk/domain/Talk.java
    @Column(name = "question", length = 200)
    private String question;

    @Column(name = "view")
    private Long view;

//    @Column(name = "comment_count")
//    private Long commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
=======
>>>>>>> a6ec0e64c5f2b3645cabf7461c6f49d16db708a2:src/main/java/com/ticle/server/opinion/domain/Heart.java
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

<<<<<<< HEAD:src/main/java/com/ticle/server/talk/domain/Talk.java
    @OneToMany(mappedBy = "talk",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
=======
    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
>>>>>>> a6ec0e64c5f2b3645cabf7461c6f49d16db708a2:src/main/java/com/ticle/server/opinion/domain/Heart.java

    @Builder
    public Heart(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
