package com.ticle.server.post.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.memo.domain.Memo;
import com.ticle.server.post.repository.MemoRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
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
@Order(5)
@LocalDummyDataInit
public class MemoInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
    private final PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (memoRepository.count() > 0) {
            log.info("[Memo] 더미 데이터 존재");
        } else {
            User user1 = userRepository.findById(1L).orElseThrow();
            User user2 = userRepository.findById(2L).orElseThrow();
            Post post1 = postRepository.findById(1L).orElseThrow();
            Post post2 = postRepository.findById(2L).orElseThrow();
            Post post3 = postRepository.findById(3L).orElseThrow();

            List<Memo> memoList = new ArrayList<>();

            // Creating 5 memos for user with user_id 1
            for (int i = 1; i <= 5; i++) {
                Memo memo = Memo.builder()
                        .targetText("Target Text " + i)
                        .content("Content for memo " + i)
                        .user(user1)
                        .post(post1)
                        .build();
                memoList.add(memo);
            }

            // Creating additional memos
            Memo memo6 = Memo.builder()
                    .targetText("Target Text 6")
                    .content("Content for memo 6")
                    .user(user2)
                    .post(post2)
                    .build();

            Memo memo7 = Memo.builder()
                    .targetText("Target Text 7")
                    .content("Content for memo 7")
                    .user(user1)
                    .post(post3)
                    .build();

            memoList.add(memo6);
            memoList.add(memo7);

            memoRepository.saveAll(memoList);
        }
    }
}
