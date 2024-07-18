package com.ticle.server.opinion.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.opinion.domain.Comment;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.opinion.repository.CommentRepository;
import com.ticle.server.opinion.repository.OpinionRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@LocalDummyDataInit
public class CommentInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final OpinionRepository opinionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (commentRepository.count() > 0) {
            log.info("[Comment] 더미 데이터 존재");
        } else {
            User dummyUser = userRepository.findById(2L).orElseThrow();
            User dummyUser2 = userRepository.findById(3L).orElseThrow();
            User dummyUser3 = userRepository.findById(4L).orElseThrow();
            User dummyUser4 = userRepository.findById(5L).orElseThrow();
            User dummyUser5 = userRepository.findById(6L).orElseThrow();

            Opinion opinion1 = opinionRepository.findById(1L).orElseThrow();
            Opinion opinion2 = opinionRepository.findById(2L).orElseThrow();


            List<Comment> commentList = new ArrayList<>();

            Comment comment1 = Comment.builder()
                    .opinion(opinion1)
                    .content("좋은 리더에게는 리더십이 필요해")
                    .heartCount(5L)
                    .user(dummyUser)
                    .build();

            Comment comment2 = Comment.builder()
                    .opinion(opinion1)
                    .content("리더는 소통 능력이 중요하다고 생각해")
                    .heartCount(8L)
                    .user(dummyUser2)
                    .build();

            Comment comment3 = Comment.builder()
                    .opinion(opinion1)
                    .content("책임감과 결단력이 있어야 해")
                    .heartCount(10L)
                    .user(dummyUser3)
                    .build();

            Comment comment4 = Comment.builder()
                    .opinion(opinion1)
                    .content("팀원들의 의견을 존중하는 리더가 필요해")
                    .heartCount(7L)
                    .user(dummyUser4)
                    .build();

            Comment comment5 = Comment.builder()
                    .opinion(opinion1)
                    .content("문제를 해결하는 능력도 중요하지")
                    .heartCount(6L)
                    .user(dummyUser5)
                    .build();

            Comment comment6 = Comment.builder()
                    .opinion(opinion2)
                    .content("모두가 중요한 역할을 하지만, 프로젝트 매니저가 핵심이라고 생각해")
                    .heartCount(5L)
                    .user(dummyUser4)
                    .build();

            Comment comment7 = Comment.builder()
                    .opinion(opinion2)
                    .content("팀의 기술 리더가 가장 중요한 구성원이라고 생각해")
                    .heartCount(8L)
                    .user(dummyUser)
                    .build();

            Comment comment8 = Comment.builder()
                    .opinion(opinion2)
                    .content("커뮤니케이션을 담당하는 사람이 중요하다고 봐")
                    .heartCount(10L)
                    .user(dummyUser5)
                    .build();

            Comment comment9 = Comment.builder()
                    .opinion(opinion2)
                    .content("모든 팀원이 각자의 역할을 충실히 하는 것이 가장 중요해")
                    .heartCount(7L)
                    .user(dummyUser2)
                    .build();

            Comment comment10 = Comment.builder()
                    .opinion(opinion2)
                    .content("팀워크를 이끌어가는 리더가 가장 중요한 구성원이지")
                    .heartCount(6L)
                    .user(dummyUser3)
                    .build();

            commentList.add(comment1);
            commentList.add(comment2);
            commentList.add(comment3);
            commentList.add(comment4);
            commentList.add(comment5);
            commentList.add(comment6);
            commentList.add(comment7);
            commentList.add(comment8);
            commentList.add(comment9);
            commentList.add(comment10);

            commentRepository.saveAll(commentList);
        }
    }
}
