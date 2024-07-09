package com.ticle.server.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    private Long postId;

    private String quizTitle;
    private Long quizNo;

    @Transient
    @Column(name = "multipleChoice")
    private Map<String, String> multipleChoice;


    @Transient
    @Column(name = "answer")
    private String answer;// A, B, C, D 중 하나


}
