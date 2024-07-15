package com.ticle.server.post.dto;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.domain.Quiz;
import com.ticle.server.user.domain.type.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {

    private Long quizNo;
    private String postTitle;
    private String quizTitle;
    private Map<String, String> multipleChoice;
    private String answer; //A,B,C,D 중 하나

    public static QuizResponse from(Quiz quiz) {
        return new QuizResponse(
                quiz.getQuizNo(),
                quiz.getPostTitle(),
                quiz.getQuizTitle(),
                quiz.getMultipleChoice(),
                quiz.getAnswer());
    }

    @Override
    public String toString() {
        return "{" +
                "quizNo=" + quizNo +
                ", quizTitle='" + quizTitle + '\'' +
                ", multipleChoice=" + multipleChoice +
                ", answer='" + answer + '\'' +
                '}';
    }
}
