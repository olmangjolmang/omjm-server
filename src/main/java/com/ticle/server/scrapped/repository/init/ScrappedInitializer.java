package com.ticle.server.scrapped.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.scrapped.domain.Scrapped;
import com.ticle.server.scrapped.repository.ScrappedRepository;
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
@Order(4)
@LocalDummyDataInit
public class ScrappedInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ScrappedRepository scrappedRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (scrappedRepository.count() > 0) {
            log.info("[Scrapped] 더미 데이터 존재");
        } else {
            User user1 = userRepository.findById(1L).orElseThrow();
            User user2 = userRepository.findById(2L).orElseThrow();
            User user3 = userRepository.findById(3L).orElseThrow();

            Post post1 = postRepository.findById(1L).orElseThrow();
            Post post2 = postRepository.findById(2L).orElseThrow();
            Post post3 = postRepository.findById(3L).orElseThrow();

            List<Scrapped> scrappedList = new ArrayList<>();

            Scrapped scrapped1 = Scrapped.builder()
                    .user(user1)
                    .post(post1)
                    .status("SCRAPPED")
                    .build();

            Scrapped scrapped2 = Scrapped.builder()
                    .user(user1)
                    .post(post2)
                    .status("SCRAPPED")
                    .build();

            Scrapped scrapped3 = Scrapped.builder()
                    .user(user2)
                    .post(post2)
                    .status("SCRAPPED")
                    .build();

            Scrapped scrapped4 = Scrapped.builder()
                    .user(user2)
                    .post(post3)
                    .status("SCRAPPED")
                    .build();

            Scrapped scrapped5 = Scrapped.builder()
                    .user(user3)
                    .post(post1)
                    .status("UNSCRAPPED")
                    .build();

            Scrapped scrapped6 = Scrapped.builder()
                    .user(user1)
                    .post(post3)
                    .status("SCRAPPED")
                    .build();

            scrappedList.add(scrapped1);
            scrappedList.add(scrapped2);
            scrappedList.add(scrapped3);
            scrappedList.add(scrapped4);
            scrappedList.add(scrapped5);
            scrappedList.add(scrapped6);


            scrappedRepository.saveAll(scrappedList);
        }
    }
}
