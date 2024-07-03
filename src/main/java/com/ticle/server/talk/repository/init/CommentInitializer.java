package com.ticle.server.talk.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.talk.domain.Comment;
import com.ticle.server.talk.domain.Talk;
import com.ticle.server.talk.repository.CommentRepository;
import com.ticle.server.talk.repository.TalkRepository;
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
@Order(2)
@LocalDummyDataInit
public class CommentInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TalkRepository talkRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (commentRepository.count() > 0) {
            log.info("[Talk] 더미 데이터 존재");
        } else {
            User DUMMY_USER = userRepository.findById(2L).orElseThrow();
            User DUMMY_USER2 = userRepository.findById(3L).orElseThrow();
            User DUMMY_USER3 = userRepository.findById(4L).orElseThrow();
            User DUMMY_USER4 = userRepository.findById(5L).orElseThrow();
            User DUMMY_USER5 = userRepository.findById(6L).orElseThrow();

            Talk TALK1 = talkRepository.findByTalkId(1L).orElseThrow();
            Talk TALK2 = talkRepository.findByTalkId(2L).orElseThrow();


            List<Comment> commentList = new ArrayList<>();

            Comment COMMENT1 = Comment.builder()
                    .talk(TALK1)
                    .content("좋은 리더에게는 리더십이 필요해")
                    .heartCount(5L)
                    .user(DUMMY_USER)
                    .build();

            Comment COMMENT2 = Comment.builder()
                    .talk(TALK1)
                    .content("리더는 소통 능력이 중요하다고 생각해")
                    .heartCount(8L)
                    .user(DUMMY_USER2)
                    .build();

            Comment COMMENT3 = Comment.builder()
                    .talk(TALK1)
                    .content("책임감과 결단력이 있어야 해")
                    .heartCount(10L)
                    .user(DUMMY_USER3)
                    .build();

            Comment COMMENT4 = Comment.builder()
                    .talk(TALK1)
                    .content("팀원들의 의견을 존중하는 리더가 필요해")
                    .heartCount(7L)
                    .user(DUMMY_USER4)
                    .build();

            Comment COMMENT5 = Comment.builder()
                    .talk(TALK1)
                    .content("문제를 해결하는 능력도 중요하지")
                    .heartCount(6L)
                    .user(DUMMY_USER5)
                    .build();

            // "팀에서 가장 중요한 구성원은 누구인가요?"에 대한 댓글 5개
            Comment COMMENT6 = Comment.builder()
                    .talk(TALK2)
                    .content("모두가 중요한 역할을 하지만, 프로젝트 매니저가 핵심이라고 생각해")
                    .heartCount(5L)
                    .user(DUMMY_USER4)
                    .build();

            Comment COMMENT7 = Comment.builder()
                    .talk(TALK2)
                    .content("팀의 기술 리더가 가장 중요한 구성원이라고 생각해")
                    .heartCount(8L)
                    .user(DUMMY_USER)
                    .build();

            Comment COMMENT8 = Comment.builder()
                    .talk(TALK2)
                    .content("커뮤니케이션을 담당하는 사람이 중요하다고 봐")
                    .heartCount(10L)
                    .user(DUMMY_USER5)
                    .build();

            Comment COMMENT9 = Comment.builder()
                    .talk(TALK2)
                    .content("모든 팀원이 각자의 역할을 충실히 하는 것이 가장 중요해")
                    .heartCount(7L)
                    .user(DUMMY_USER2)
                    .build();

            Comment COMMENT10 = Comment.builder()
                    .talk(TALK2)
                    .content("팀워크를 이끌어가는 리더가 가장 중요한 구성원이지")
                    .heartCount(6L)
                    .user(DUMMY_USER3)
                    .build();

            commentList.add(COMMENT1);
            commentList.add(COMMENT2);
            commentList.add(COMMENT3);
            commentList.add(COMMENT4);
            commentList.add(COMMENT5);
            commentList.add(COMMENT6);
            commentList.add(COMMENT7);
            commentList.add(COMMENT8);
            commentList.add(COMMENT9);
            commentList.add(COMMENT10);

            commentRepository.saveAll(commentList);
        }
    }
}
