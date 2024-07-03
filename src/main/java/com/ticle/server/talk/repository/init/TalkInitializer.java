package com.ticle.server.talk.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.talk.domain.Talk;
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
@Order(1)
@LocalDummyDataInit
public class TalkInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final TalkRepository talkRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (talkRepository.count() > 0) {
            log.info("[Talk] 더미 데이터 존재");
        } else {
            User dummyAdmin = userRepository.findById(1L).orElseThrow();

            List<Talk> talkList = new ArrayList<>();

            Talk talk1 = Talk.builder()
                    .question("좋은 리더란 어떤 자질이 필요할까요?")
                    .viewCount(5L)
                    .commentCount(4L)
                    .user(dummyAdmin)
                    .build();

            Talk talk2 = Talk.builder()
                    .question("팀에서 가장 중요한 구성원은 누구인가요?")
                    .viewCount(10L)
                    .commentCount(3L)
                    .user(dummyAdmin)
                    .build();

            Talk talk3 = Talk.builder()
                    .question("롤모델이 있다면 누구이고 이유는 무엇인가요?")
                    .viewCount(14L)
                    .commentCount(5L)
                    .user(dummyAdmin)
                    .build();

            Talk talk4 = Talk.builder()
                    .question("인생에서 가장 필요한 사항은 무엇이라 생각하나요?")
                    .viewCount(20L)
                    .commentCount(7L)
                    .user(dummyAdmin)
                    .build();

            Talk talk5 = Talk.builder()
                    .question("대인관계에서 가장 중요하게 생각하는 것은 무엇인가요?")
                    .viewCount(4L)
                    .commentCount(2L)
                    .user(dummyAdmin)
                    .build();

            Talk talk6 = Talk.builder()
                    .question("가장 기억에 남는 프로젝트는 무엇인가요?")
                    .viewCount(12L)
                    .commentCount(6L)
                    .user(dummyAdmin)
                    .build();

            Talk talk7 = Talk.builder()
                    .question("성공적인 팀워크를 위해 가장 필요한 요소는 무엇인가요?")
                    .viewCount(8L)
                    .commentCount(3L)
                    .user(dummyAdmin)
                    .build();

            Talk talk8 = Talk.builder()
                    .question("기술 발전이 사회에 미치는 영향은 무엇인가요?")
                    .viewCount(15L)
                    .commentCount(4L)
                    .user(dummyAdmin)
                    .build();

            Talk talk9 = Talk.builder()
                    .question("효과적인 문제 해결 방법은 무엇이라 생각하나요?")
                    .viewCount(9L)
                    .commentCount(2L)
                    .user(dummyAdmin)
                    .build();

            Talk talk10 = Talk.builder()
                    .question("업무에서 동기부여를 유지하는 방법은 무엇인가요?")
                    .viewCount(18L)
                    .commentCount(5L)
                    .user(dummyAdmin)
                    .build();

            Talk talk11 = Talk.builder()
                    .question("IT 업계에서 가장 중요한 기술 트렌드는 무엇인가요?")
                    .viewCount(11L)
                    .commentCount(3L)
                    .user(dummyAdmin)
                    .build();

            Talk talk12 = Talk.builder()
                    .question("효과적인 의사소통 방법은 무엇이라고 생각하나요?")
                    .viewCount(7L)
                    .commentCount(4L)
                    .user(dummyAdmin)
                    .build();

            Talk talk13 = Talk.builder()
                    .question("최근에 배운 가장 중요한 교훈은 무엇인가요?")
                    .viewCount(10L)
                    .commentCount(5L)
                    .user(dummyAdmin)
                    .build();

            Talk talk14 = Talk.builder()
                    .question("미래의 커리어 목표는 무엇인가요?")
                    .viewCount(13L)
                    .commentCount(6L)
                    .user(dummyAdmin)
                    .build();

            talkList.add(talk1);
            talkList.add(talk2);
            talkList.add(talk3);
            talkList.add(talk4);
            talkList.add(talk5);
            talkList.add(talk6);
            talkList.add(talk7);
            talkList.add(talk8);
            talkList.add(talk9);
            talkList.add(talk10);
            talkList.add(talk11);
            talkList.add(talk12);
            talkList.add(talk13);
            talkList.add(talk14);

            talkRepository.saveAll(talkList);
        }
    }
}
